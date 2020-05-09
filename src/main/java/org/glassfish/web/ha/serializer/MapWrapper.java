package org.glassfish.web.ha.serializer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 暂时只支持KEY为String类型的MAP
 *
 * @author ZH (mailto: lizw@primeton.com)
 */
public class MapWrapper extends Wrapper {

    /**
     * Values type
     */
    private Map<String, String> elementTypes = new HashMap<>();
    private Map<String, Boolean> primitives = new HashMap<>();

    public MapWrapper() {
    }

    public MapWrapper(Object object) {
        super(object);
        init();
    }

    protected void init() {
        elementTypes.clear();
        primitives.clear();
        if (null == object) {
            return;
        }
        if (!(object instanceof Map)) {
            throw new RuntimeException("Non-Map Object not allowed.");
        }
        Map<String, ?> map = (Map<String, ?>)object;
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            if (null != value && (value.getClass().isArray() || value instanceof Collection)) {
                System.err.println("[WARNING] Map element not allowed to use Array or Collection. Cause: Deserialization does not recognize the type of its element.");
                elementTypes.put(key.toString(), null);
                primitives.put(key.toString(), Boolean.TRUE);
            } else {
                elementTypes.put(key.toString(), null == value ? null : value.getClass().getName());
                primitives.put(key.toString(), value.getClass().isPrimitive());
            }
        }
    }

    @Override
    public int getType() {
        return TYPE_MAP;
    }

}
