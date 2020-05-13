package org.glassfish.web.ha.serializer;

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

    /**
     * Map具体类型，Exp. HashMap （预留扩展）
     */
    private String componentType;

    public MapWrapper() {
    }

    public MapWrapper(Object object) {
        super(object);
        init();
    }

    protected void init() {
        this.componentType = null == object ? null : object.getClass().getName();
        elementTypes.clear();
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
            } else {
                elementTypes.put(key.toString(), null == value ? null : value.getClass().getName());
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
        return TYPE_MAP;
    }

    public Map<String, String> getElementTypes() {
        return elementTypes;
    }

    public String getComponentType() {
        return componentType;
    }

}
