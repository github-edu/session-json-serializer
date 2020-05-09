package org.glassfish.web.ha.serializer;

import java.util.Collection;

/**
 * @author ZH (mailto: lizw@primeton.com)
 */
public class ObjectWrapper extends Wrapper {

    private String className;

    public ObjectWrapper() {
    }

    public ObjectWrapper(Object object) {
        super(object);
        init();
    }

    protected void init() {
        this.className = null == object ? null : object.getClass().getName();
        if (null == object ? false : object.getClass().isArray()) {
            throw new RuntimeException("Array object is not allowed.");
        }
        if (null == object ? false : object instanceof Collection) {
            throw new RuntimeException("Collection object is not allowed.");
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int getType() {
        return TYPE_OBJECT;
    }

    @Override
    public void setObject(Object object) {
        super.setObject(object);
        init();
    }

    /*
    public static ObjectWrapper wrapper(Object obj) {
        return new ObjectWrapper(obj);
    }

    public static List<ObjectWrapper> wrapper(Object... objects) {
        return wrapper(false, objects);
    }

    public static List<ObjectWrapper> wrapper(boolean ignoreNullObj, Object... objects) {
        if (null == objects || 0 == objects.length) {
            return new ArrayList<>();
        }
        if (ignoreNullObj) {
            return Stream.of(objects).filter(o -> null != o).map(ObjectWrapper :: wrapper).collect(Collectors.toList());
        }
        return Stream.of(objects).map(ObjectWrapper :: wrapper).collect(Collectors.toList());
    }

    public static List<ObjectWrapper> wrapper(List<Object> objects) {
        return wrapper(false, objects);
    }

    public static List<ObjectWrapper> wrapper(boolean ignoreNullObj, List<Object> objects) {
        if (null == objects || objects.isEmpty()) {
            return new ArrayList<>();
        }
        if (ignoreNullObj) {
            return objects.stream().filter(o -> null != o).map(ObjectWrapper :: wrapper).collect(Collectors.toList());
        }
        return objects.stream().map(ObjectWrapper :: wrapper).collect(Collectors.toList());
    }
    */
}
