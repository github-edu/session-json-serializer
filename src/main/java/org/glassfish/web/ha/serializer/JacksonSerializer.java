package org.glassfish.web.ha.serializer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSerializer {

    private ClassLoader loader;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    }

    protected ClassLoader getClassLoader() {
        return null == loader ? ClassLoader.getSystemClassLoader() : loader;
    }

    public byte[] attributesHashFrom(Map<String, Object> attrs) throws IOException {
        Map<String, Wrapper> attributes = new HashMap<>();
        for (String key : attrs.keySet()) {
            attributes.put(key, wrapper(attrs.get(key)));
        }
        byte[] serialized = objectMapper.writeValueAsBytes(attributes);
        System.out.println(new String(serialized, Charset.defaultCharset()));
        System.out.println("$$");
        return serialized;
    }

    protected Wrapper wrapper(Object object) {
        if (null == object) {
            return new ObjectWrapper();
        }
        if (object.getClass().isArray()) {
            return new ArrayWrapper(object);
        }
        if (object instanceof List) {
            return new ListWrapper(object);
        }
        if (object instanceof Set) {
            return new SetWrapper(object);
        }
        if (object instanceof Map) {
            return new MapWrapper(object);
        }
        return new ObjectWrapper(object);
    }


    public Object deserializeInto(byte[] data) throws IOException, ClassNotFoundException {
        // byte[] -> { attr1: XxxWrapper, attr2: XxxWrapper, ...}
        // XxxWrapper { type, object, ... } -> Real Object
        Map<String, Object> attrs = new HashMap<>();
        JsonNode rootNode = objectMapper.readTree(data);
        Iterator<String> attrNameIterator = rootNode.fieldNames();
        if (attrNameIterator.hasNext()) {
            while (attrNameIterator.hasNext()) {
                Object realObj = null;
                String attrName = attrNameIterator.next();
                /**
                 * ObjectWrapper#{type, primitive, object, className}, ArrayWrapper#{type, primitive, object, elementTypes}, ...
                 */
                JsonNode wrapperNode = rootNode.findValue(attrName);
                if (null == wrapperNode) {
                    continue;
                }
                JsonNode wrapperObjectNode = wrapperNode.findValue("object");
                JsonNode wrapperTypeNode = wrapperNode.findValue("type");
                int type = null == wrapperTypeNode ? Wrapper.TYPE_OBJECT : wrapperTypeNode.asInt(Wrapper.TYPE_OBJECT);
                if (Wrapper.TYPE_OBJECT == type) {
                    realObj = deserializeObjectWrapper(wrapperNode, wrapperObjectNode);
                } else if (Wrapper.TYPE_ARRAY == type) {
                    realObj = deserializeArrayWrapper(wrapperNode, wrapperObjectNode);
                } else if (Wrapper.TYPE_LIST == type) {
                    realObj = deserializeListWrapper(wrapperNode, wrapperObjectNode);
                } else if (Wrapper.TYPE_SET == type) {
                    realObj = deserializeSetWrapper(wrapperNode, wrapperObjectNode);
                } else if (Wrapper.TYPE_MAP == type) {
                    realObj = deserializeMapWrapper(wrapperNode, wrapperObjectNode);
                } else {
                    System.err.println("[WARNING] Illegal Wrapper#TYPE " + type);
                    realObj = null;
                }

                attrs.put(attrName, realObj);
            }
            // 刷新缓存
            //session.getAttributes().clear();
            //session.getAttributes().putAll(attrs);
        } else {
            //session.getAttributes().clear();
        }
        return attrs;
    }

    private Object deserializeWrapper(JsonNode wrapperNode, JsonNode objectNode, int type) {
        JsonNode elementTypesNode = wrapperNode.findValue("elementTypes");
        JsonNode componentTypeNode = wrapperNode.findValue("componentType");

        if (!objectNode.isArray()) {
            return null;
        }
        String componentType = componentTypeNode.asText();
        componentType = null == componentType || componentType.trim().isEmpty() ? String.class.getName() : componentType;
        boolean primitiveArray = false;
        if (Stream.of(ArrayWrapper.javaPrimitiveTypes).anyMatch(componentType :: equalsIgnoreCase)) {
            primitiveArray = true;
        } else {
            if (!elementTypesNode.isArray()) {
                return null;
            }
        }

        // Elements: XxxWrapper -> Real Object
        Iterator<JsonNode> typeElementsIterator = primitiveArray ? null : elementTypesNode.elements();
        Iterator<JsonNode> objectElementsIterator = objectNode.elements();

        List<String> classNames = primitiveArray ? null : new ArrayList<>();
        List<Object> realObjects = new ArrayList<>();

        while (!primitiveArray && typeElementsIterator.hasNext()) {
            classNames.add(typeElementsIterator.next().asText());
        }
        int i = 0;
        while (objectElementsIterator.hasNext()) {
            JsonNode node = objectElementsIterator.next();
            if (null == node) {
                realObjects.add(null);
            } else {
                realObjects.add(deserializeObject(node, primitiveArray ? componentType : classNames.get(i), primitiveArray));
            }
            i++;
        }

        // TODO 暂不实现转成与原来一样的集合类型（可以改造，序列化时存储集合类型，反序列化可以取到类型，然后集合类型转换addAll）
        if (Wrapper.TYPE_LIST == type) {
            return realObjects;
        }
        if (Wrapper.TYPE_ARRAY == type) {
            // return realObjects.toArray();
            // 反序列化时保证与原数组类型一致（否则粗暴实现全转成 Object Array）
            try {
                if (primitiveArray) {
                    /*
                    final String componentType2 = componentType;
                    Class<?> clazz = Stream.of(ArrayWrapper.javaPrimitiveClasses).filter(c -> c.getName().equalsIgnoreCase(componentType2)).findFirst().orElse(null);
                    Object array = Array.newInstance(clazz, realObjects.size());
                    return realObjects.toArray((?[])array);
                     */
                    return ArrayUtils.asPrimitiveArray(realObjects, componentType);
                }
                Class<?> clazz = getClassLoader().loadClass(componentType);
                Object array = Array.newInstance(clazz, realObjects.size());
                return realObjects.toArray((Object[])array);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return realObjects.toArray();
        }
        if (Wrapper.TYPE_SET == type) {
            return new HashSet<Object>(realObjects);
        }

        return null;
    }

    protected Object deserializeObject(JsonNode node, String className, boolean primitive) {
        if (null == className || className.trim().isEmpty() || "null".equalsIgnoreCase(className)) {
            return null;
        }
        if (node.isArray()) {
            return null;
        }
        if (primitive) {
            return asPrimitiveObject(node, className);
        }

        if (null == className || className.trim().isEmpty()) {
            return null;
        }
        if (String.class.getName().equals(className)) {
            return node.asText();
        }
        try {
            Class<?> clazz = getClassLoader().loadClass(className);
            Object realObject = objectMapper.readValue(node.toString(), clazz);
            return realObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object deserializeMapWrapper(JsonNode wrapperNode, JsonNode objectNode) {
        // TODO XXX
        return new HashMap<>();
    }

    private Object deserializeSetWrapper(JsonNode wrapperNode, JsonNode objectNode) {
        return deserializeWrapper(wrapperNode, objectNode, Wrapper.TYPE_SET);
    }

    private Object deserializeListWrapper(JsonNode wrapperNode, JsonNode objectNode) {
        return deserializeWrapper(wrapperNode, objectNode, Wrapper.TYPE_LIST);
    }

    private Object deserializeArrayWrapper(JsonNode wrapperNode, JsonNode objectNode) {
        return deserializeWrapper(wrapperNode, objectNode, Wrapper.TYPE_ARRAY);
    }

    protected Object deserializeObjectWrapper(JsonNode wrapperNode) {
        return deserializeObjectWrapper(wrapperNode, null);
    }

    private Object deserializeObjectWrapper(JsonNode wrapperNode, JsonNode objectNode) {
        objectNode = null == objectNode ? wrapperNode.findValue("object") : objectNode;
        JsonNode primitiveNode = wrapperNode.findValue("primitive");
        JsonNode classNameNode = wrapperNode.findValue("className");
        String className = null == classNameNode ? null : classNameNode.asText();
        boolean isPrimitive = null == primitiveNode ? false : primitiveNode.asBoolean(false);
        if (isPrimitive) {
            return asPrimitiveObject(objectNode, className);
        }
        if (null == className || className.trim().isEmpty()) {
            return null;
        }
        if (String.class.getName().equals(className)) {
            return objectNode.asText();
        }
        try {
            Class<?> clazz = getClassLoader().loadClass(className);
            Object realObject = objectMapper.readValue(objectNode.toString(), clazz);
            return realObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object asPrimitiveObject(JsonNode objectNode, String className) {
        if (null == className || className.trim().isEmpty()) {
            return null;
        }
        // See Class#isPrimitive
        //     * @see     java.lang.Boolean#TYPE
        //     * @see     java.lang.Character#TYPE
        //     * @see     java.lang.Byte#TYPE
        //     * @see     java.lang.Short#TYPE
        //     * @see     java.lang.Integer#TYPE
        //     * @see     java.lang.Long#TYPE
        //     * @see     java.lang.Float#TYPE
        //     * @see     java.lang.Double#TYPE
        //     * @see     java.lang.Void#TYPE
        if (Boolean.class.getName().equalsIgnoreCase(className) || "boolean".equalsIgnoreCase(className) || "bool".equalsIgnoreCase(className)) {
            return objectNode.asBoolean();
        }
        if (Character.class.getName().equalsIgnoreCase(className) || "character".equalsIgnoreCase(className) || "char".equalsIgnoreCase(className)) {
            String text = objectNode.asText();
            return null == text || text.isEmpty() ? null : new Character(text.charAt(0));
        }
        if (Byte.class.getName().equalsIgnoreCase(className) || "byte".equalsIgnoreCase(className)) {
             return new Byte((byte) objectNode.asInt());
        }
        if (Short.class.getName().equalsIgnoreCase(className) || "short".equalsIgnoreCase(className)) {
            return new Short((short) objectNode.asInt());
        }
        if (Integer.class.getName().equalsIgnoreCase(className) || "integer".equalsIgnoreCase(className) || "int".equalsIgnoreCase(className)) {
            return new Integer(objectNode.asInt());
        }
        if (Long.class.getName().equalsIgnoreCase(className) || "long".equalsIgnoreCase(className)) {
            return new Long(objectNode.asLong());
        }
        if (Float.class.getName().equalsIgnoreCase(className) || "float".equalsIgnoreCase(className)) {
            return new Float(objectNode.asDouble());
        }
        if (Double.class.getName().equalsIgnoreCase(className) || "double".equalsIgnoreCase(className)) {
            return new Double(objectNode.asDouble());
        }
        return objectNode.asText();
    }

}
