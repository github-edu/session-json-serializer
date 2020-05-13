package org.glassfish.web.ha.serializer;

import java.util.List;

/**
 * @author ZH (mailto: lizw@primeton.com)
 */
public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * 穷举基本类型逐一实现转换
     *
     * @param elements 基本类型对象集合
     * @param componentType boolean, char, byte, short, int, long, float, double
     * @return
     */
    public static Object asPrimitiveArray(List<Object> elements, String componentType) {
        if ("boolean".equalsIgnoreCase(componentType) || "bool".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new boolean[0];
            }
            boolean[] array = new boolean[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = false;
                    continue;
                }
                array[i] = e instanceof Boolean ? (Boolean)e : Boolean.parseBoolean(e.toString());
            }
            return array;
        }

        if ("char".equalsIgnoreCase(componentType) || "character".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new char[0];
            }
            char[] array = new char[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = ' ';
                    continue;
                }
                Character c = e instanceof Character ? (Character)e : null;
                if (null == c) {
                    String s = e.toString();
                    c = s.isEmpty() ? ' ' : Character.valueOf(s.charAt(0));
                }
                array[i] = null == c ? ' ' : c.charValue();
            }
            return array;
        }

        if ("byte".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new byte[0];
            }
            byte[] array = new byte[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0;
                    continue;
                }
                try {
                    array[i] = e instanceof Byte ? (Byte)e : Byte.parseByte(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0;
                }
            }
            return array;
        }

        if ("short".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new short[0];
            }
            short[] array = new short[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0;
                    continue;
                }
                try {
                    array[i] = e instanceof Short ? (Short)e : Short.parseShort(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0;
                }
            }
            return array;
        }

        if ("int".equalsIgnoreCase(componentType) || "integer".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new int[0];
            }
            int[] array = new int[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0;
                    continue;
                }
                try {
                    array[i] = e instanceof Integer ? (Integer)e : Integer.parseInt(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0;
                }
            }
            return array;
        }

        if ("long".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new long[0];
            }
            long[] array = new long[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0L;
                    continue;
                }
                try {
                    array[i] = e instanceof Long ? (Long)e : Long.parseLong(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0L;
                }
            }
            return array;
        }

        if ("float".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new float[0];
            }
            float[] array = new float[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0f;
                    continue;
                }
                try {
                    array[i] = e instanceof Float ? (Float)e : Float.parseFloat(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0f;
                }
            }
            return array;
        }

        if ("double".equalsIgnoreCase(componentType)) {
            if (null == elements) {
                return new double[0];
            }
            double[] array = new double[elements.size()];
            for (int i = 0; i < elements.size(); i++) {
                Object e = elements.get(i);
                if (null == e) {
                    array[i] = 0d;
                    continue;
                }
                try {
                    array[i] = e instanceof Double ? (Double)e : Double.parseDouble(e.toString());
                } catch (NumberFormatException ignore) {
                    array[i] = 0d;
                }
            }
            return array;
        }

        return elements.toArray();
    }

}
