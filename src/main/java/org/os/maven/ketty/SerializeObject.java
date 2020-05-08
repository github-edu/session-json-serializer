package org.os.maven.ketty;

public class SerializeObject {
	
	private String clazz;
	
	private Object object;

	public SerializeObject() {
		super();
	}

	public SerializeObject(Object object) {
		super();
		this.clazz = null == object ? null : object.getClass().getName();
		this.object = object;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "SerializeObject [clazz=" + clazz + ", object=" + object + "]";
	}
	

}
