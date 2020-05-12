package org.os.maven.ketty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ZH (mailto: lizw@primeton.com)
 */
public class ArrayWrapper extends Wrapper {

    /**
     * Java基本类型类
     */
    public static final String[] javaPrimitiveTypes = new String[] {
            "boolean",
            "char",
            "byte",
            "short",
            "int",
            "long",
            "float",
            "double",
            "void"
    };
    /**
     * Java基本类型类
     */
    public static final Class<?>[] javaPrimitiveClasses = new Class[] {
            boolean.class,
            char.class,
            byte.class,
            short.class,
            int.class,
            long.class,
            float.class,
            double.class,
            void.class
    };

    /**
     * Java基本类型数组类
     */
    public static final Class<?>[] javaPrimitiveArrayClasses = new Class[] {
            boolean[].class,
            char[].class,
            byte[].class,
            short[].class,
            int[].class,
            long[].class,
            float[].class,
            double[].class
    };

    /**
     * 每个元素的类型（对于基本类型数组，此参数无用）
     */
    private List<String> elementTypes = new ArrayList<>();

    /**
     * 数组声明的类型： <br>
     * 基本类型：int, long, float, double, boolean, ... <br>
     * 对象类型：java.lang.Object, java.lang.Long, com.java.test.User, com.java.test.User$Foo, ... <br>
     */
    private String componentType;

    public ArrayWrapper() {
    }

    public ArrayWrapper(Object object) {
        super(object);
        init();
    }

    protected void init() {
        elementTypes.clear();
        if (null == object) {
            return;
        }
        if (!object.getClass().isArray()) {
            throw new RuntimeException("Non-Array Object not allowed.");
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
        // Primitive Type Array int[] long[] boolean[] ...
        componentType = object.getClass().getComponentType().getName();
        if (Stream.of(javaPrimitiveArrayClasses).anyMatch(object.getClass() :: isAssignableFrom)) {
            // 基本类型数组，每个元素都是相同的类型，无需存储各个元素的类型
        } else {
            Object[] array = (Object[])object;
            for (Object obj : array) {
                if (obj.getClass().isArray() || obj instanceof Collection) {
                    System.err.println("[WARNING] Array element not allowed to use Array or Collection. Cause: Deserialization does not recognize the type of its element.");
                    elementTypes.add(null);
                } else {
                    elementTypes.add(null == obj ? null : obj.getClass().getName());
                }
            }
        }
    }

    @Override
    public void setObject(Object object) {
        super.setObject(object);
        init();
    }

    @Override
    public int getType() {
        return TYPE_ARRAY;
    }

    /**
     * If Primitive Array return empty.
     *
     * @return
     */
    public List<String> getElementTypes() {
        return elementTypes;
    }

    public String getComponentType() {
        return componentType;
    }

}
