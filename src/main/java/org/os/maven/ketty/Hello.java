package org.os.maven.ketty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {
	
	public static void main(String[] args) throws Exception {
		System.out.println(new String[] {"123", "345"}.getClass());
		System.out.println(new String[] {"123", "345"}.getClass().isArray());
		System.out.println(new String[] {"123", "345"}.getClass().getName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", null);
		map.put("bbb", null);
		System.out.println(map);
		
		System.out.println(Boolean.class.isPrimitive());
		System.out.println(Long.class.isPrimitive());
		System.out.println(Integer.class.isPrimitive());
		System.out.println(int.class.isPrimitive());
		System.out.println(boolean.class.isPrimitive());
		System.out.println(long.class.isPrimitive());
		
		
		System.out.println(new int[] { 1, 2, 3  }.getClass().isPrimitive());
		System.out.println();
		
		Object array = new int[] { 1, 2, 3 };
		
		System.out.println(array.getClass().isAssignableFrom(int[].class));
		
		System.out.println(array.getClass().getCanonicalName());
		System.out.println(array.getClass().getName());
		System.out.println(array.getClass().getSimpleName());
		System.out.println(array.getClass().getTypeName());
		System.out.println(array.getClass().getComponentType().getName());
		
		System.out.println();
		
		System.out.println(new User[] {}.getClass().getCanonicalName());
		System.out.println(new User[] {}.getClass().getName());
		System.out.println(new User[] {}.getClass().getSimpleName());
		System.out.println(new User[] {}.getClass().getTypeName());
		System.out.println(new User[] {}.getClass().getComponentType().getName());
		
		System.out.println();
		
		System.out.println(new User.Bar[] {}.getClass().getCanonicalName());
		System.out.println(new User.Bar[] {}.getClass().getName());
		System.out.println(new User.Bar[] {}.getClass().getSimpleName());
		System.out.println(new User.Bar[] {}.getClass().getTypeName());
		System.out.println(new User.Bar[] {}.getClass().getComponentType().getName());
		
		
		System.out.println(((Object[])new String[] { "1", "2" })[0]);
		
		{
			Class<?> clazz = String.class;
			
			Object[] aaa = (Object[]) Array.newInstance(clazz, 3);
			
			System.out.println(aaa);
			System.out.println(aaa.getClass().getComponentType());
			System.out.println(aaa.length);
		}
		
		{
			System.out.println("$#$");
			Class<?> clazz = int.class;
			
			int[] aaa = (int[]) Array.newInstance(clazz, 3);
			
			System.out.println(aaa);
			System.out.println(aaa.getClass().getComponentType());
			System.out.println(aaa.length);
		}
		
		{
			byte[] b = new byte[1];
			b[0] = 127;
			System.out.println(b[0]);
			
			System.out.println(Byte.parseByte("128"));
		}
		
	}

}
