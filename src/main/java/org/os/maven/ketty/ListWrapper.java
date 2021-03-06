package org.os.maven.ketty;

import java.util.Collection;
import java.util.List;

/**
 * @author ZH (mailto: lizw@primeton.com)
 */
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
            } else {
                getElementTypes().add(null == obj ? null : obj.getClass().getName());
            }
        }
    }

    @Override
    public int getType() {
        return TYPE_LIST;
    }

}
