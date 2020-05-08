package org.os.maven.ketty;

import java.util.HashMap;
import java.util.Map;

public class Hello {
	
	public static void main(String[] args) {
		System.out.println(new String[] {"123", "345"}.getClass());
		System.out.println(new String[] {"123", "345"}.getClass().isArray());
		System.out.println(new String[] {"123", "345"}.getClass().getName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", null);
		map.put("bbb", null);
		System.out.println(map);
	}

}
