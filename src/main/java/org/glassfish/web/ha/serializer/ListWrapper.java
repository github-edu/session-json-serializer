package org.glassfish.web.ha.serializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListWrapper extends ArrayWrapper {

    public ListWrapper() {
    }

    public ListWrapper(Object object) {
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
        if (! (object instanceof List)) {
            throw new RuntimeException("Non-List Object not allowed.");
        }
        List<?> array = (List)object;
        for (Object obj : array) {
            if (obj.getClass().isArray() || obj instanceof Collection) {
                System.err.println("[WARNING] List element not allowed to use Array or Collection. Cause: Deserialization does not recognize the type of its element.");
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
        return TYPE_LIST;
    }

}
