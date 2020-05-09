package org.glassfish.web.ha.serializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        if (object.getClass().isAssignableFrom(boolean[].class)) {
            boolean[] array = (boolean[])object;
            for (boolean element : array) {
                elementTypes.add("boolean");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(char[].class)) {
            char[] array = (char[])object;
            for (char element : array) {
                elementTypes.add("char");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(byte[].class)) {
            byte[] array = (byte[])object;
            for (byte element : array) {
                elementTypes.add("byte");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(short[].class)) {
            short[] array = (short[])object;
            for (short element : array) {
                elementTypes.add("short");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(int[].class)) {
            int[] array = (int[])object;
            for (int element : array) {
                elementTypes.add("int");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(long[].class)) {
            long[] array = (long[])object;
            for (long element : array) {
                elementTypes.add("long");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(float[].class)) {
            float[] array = (float[])object;
            for (float element : array) {
                elementTypes.add("float");
                primitives.add(true);
            }
        } else if (object.getClass().isAssignableFrom(double[].class)) {
            double[] array = (double[])object;
            for (double element : array) {
                elementTypes.add("double");
                primitives.add(true);
            }
        } else {
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
