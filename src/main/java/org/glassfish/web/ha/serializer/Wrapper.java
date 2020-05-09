package org.glassfish.web.ha.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Wrapper {

    public static final int TYPE_OBJECT = 1;
    public static final int TYPE_ARRAY = 2;
    public static final int TYPE_LIST = 3;
    public static final int TYPE_SET = 4;
    public static final int TYPE_MAP = 5;

    protected Object object;
    
    @JsonProperty
    protected boolean primitive;

    public Wrapper() {
    }

    public Wrapper(Object object) {
        this.object = object;
        isPrimitive();
    }

    /**
     * 基本类型（int、byte、short、float、double、boolean）
     *
     * @return
     */
    public boolean isPrimitive() {
        if (null == object) {
        	primitive = true;
        } else {
        	primitive = object.getClass().isPrimitive();
        }
        return primitive;
    }

    public abstract int getType();

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
        isPrimitive();
    }

}
