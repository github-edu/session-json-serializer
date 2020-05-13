package org.glassfish.web.ha.serializer;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author ZH (mailto: lizw@primeton.com)
 */
public abstract class Wrapper {

    public static final int TYPE_OBJECT = 1;
    public static final int TYPE_ARRAY = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_SET = 4;
    public static final int TYPE_MAP = 5;

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
    public static final String[] javaPrimitiveObjTypes = new String[] {
            Boolean.class.getName(),
            Character.class.getName(),
            Byte.class.getName(),
            Short.class.getName(),
            Integer.class.getName(),
            Long.class.getName(),
            Float.class.getName(),
            Double.class.getName(),
            Void.class.getName()
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

    protected Object object;

    public Wrapper() {
    }

    public Wrapper(Object object) {
        this.object = object;
    }

    public abstract int getType();

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
