package org.glassfish.web.ha.serializer;

import java.util.Collection;
import java.util.Set;

public class SetWrapper extends ArrayWrapper {

    public SetWrapper() {
    }

    public SetWrapper(Object object) {
        super(object);
        init();
    }

    @Override
    protected void init() {
        getElementTypes().clear();
        getPrimitives().clear();
        if (null == object) {
            return;
        }
        if (! (object instanceof Set)) {
            throw new RuntimeException("Non-Set Object not allowed.");
        }
        Set<?> array = (Set)object;
        for (Object obj : array) {
            if (obj.getClass().isArray() || obj instanceof Collection) {
                System.err.println("[WARNING] Set element not allowed to use Array or Collection. Cause: Deserialization does not recognize the type of its element.");
                getElementTypes().add(null);
                getPrimitives().add(true);
            } else {
                getElementTypes().add(null == obj ? null : obj.getClass().getName());
                getPrimitives().add(null == obj ? true : obj.getClass().isPrimitive());
            }
        }
    }

    @Override
    public int getType() {
        return TYPE_SET;
    }

}
