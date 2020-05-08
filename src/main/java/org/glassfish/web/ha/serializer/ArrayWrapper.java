package org.glassfish.web.ha.serializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayWrapper extends Wrapper {

    /**
     * 每个元素的类型
     */
    private List<String> elementTypes = new ArrayList<>();
    /**
     * 每个元素是否是基本类型
     */
    private List<Boolean> primitives = new ArrayList<>();

    public ArrayWrapper() {
    }

    public ArrayWrapper(Object object) {
        super(object);
        init();
    }

    protected void init() {
        elementTypes.clear();
        primitives.clear();
        if (null == object) {
            return;
        }
        if (!object.getClass().isArray()) {
            throw new RuntimeException("Non-Array Object not allowed.");
        }
        Object[] array = (Object[])object;
        for (Object obj : array) {
            if (obj.getClass().isArray() || obj instanceof Collection) {
                System.err.println("[WARNING] Array element not allowed to use Array or Collection. Cause: Deserialization does not recognize the type of its element.");
                elementTypes.add(null);
                primitives.add(true);
            } else {
                elementTypes.add(null == obj ? null : obj.getClass().getName());
                primitives.add(null == obj ? true : obj.getClass().isPrimitive());
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

    public List<String> getElementTypes() {
        return elementTypes;
    }

    public List<Boolean> getPrimitives() {
        return primitives;
    }
}
