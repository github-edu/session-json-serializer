package org.glassfish.web.ha.serializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.os.maven.ketty.Dept;
import org.os.maven.ketty.User;
import org.os.maven.ketty.UserWrapper;
import org.os.maven.ketty.VipUser;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SessionTest {
	
	final static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		
		Map<String, Object> attrs = new HashMap<>();
		
		{
			// 基本类型
			attrs.put("intArray", new int[] { 1, 2, 3 });
			attrs.put("booleanArray", new boolean[] { false, true, false });
			attrs.put("floatArray", new float[] { 1.1f, 2.2f, 3.3f });
		}
		{
			// 基本类型 - 对象
			attrs.put("Boolean", Boolean.TRUE);
			attrs.put("Int", 123);
			attrs.put("Long", 123L);
			attrs.put("Float", 123.456f);
			attrs.put("Fouble", 123.456d);
			attrs.put("Byte", new Byte((byte) 11));
		}
		
		{
			// 集合测试
			Set<String> set = new HashSet<>();
			set.add("Java");
			set.add("Python");
			set.add("Go");
			attrs.put("set", set);
			
			List<String> list = new ArrayList<>();
			list.add("Java");
			list.add("Python");
			list.add("Go");
			attrs.put("list", list);
			
			// 混合集合
			List<Object> list2 = new ArrayList<>();
			list2.add(123);
			list2.add(Long.MAX_VALUE);
			list2.add(123d);
			list2.add(Boolean.TRUE);
			attrs.put("list2", list2);
		}
		
		// Java Bean （含有静态内部类和内部类）
		User user = new User("123456", "Ketty", 12, new Dept("001", "HR"));
		user.initFooBar(110, "FB");
		attrs.put("user", user);
		attrs.put("userArray", new User[] { new User("123456", "Ketty", 12, new Dept("001", "HR")), new User("123457", "Jack", 22, new Dept("008", "HR")) });
		attrs.put("userArray1", new User[] { new User("123456", "Ketty", 12, new Dept("001", "HR")), new User("123457", "Jack", 22, new Dept("008", "HR")) });
		attrs.put("userArray11", new User[] { new VipUser("123456", "Ketty", 12, new Dept("001", "HR"), "0848"), new User("123457", "Jack", 22, new Dept("008", "HR")) });
		// 混合数组
		attrs.put("userArray2", new Object[] { 1, 2L, 3.4f, 4.5d, Boolean.TRUE, Character.MAX_VALUE, new User("123456", "Ketty", 12, new Dept("001", "HR")) });
		attrs.put("userList", Arrays.asList(new User[] { new User("123456", "Ketty", 12, new Dept("001", "HR")), new User("123457", "Jack", 22, new Dept("008", "HR")) }));
		attrs.put("userList1", Arrays.asList(new User[] { new VipUser("123456", "Ketty", 12, new Dept("001", "HR"), "9000"), new User("123457", "Jack", 22, new Dept("008", "HR")) }));
		
		
		{
			UserWrapper wrapper = new UserWrapper();
			wrapper.setUserArray(new User[] { new User("123456", "Ketty", 12, new Dept("001", "HR")), new User("123457", "Jack", 22, new Dept("008", "HR")) });
			wrapper.setUserList(Arrays.asList(wrapper.getUserArray()));
			Set<User> set = new HashSet<User>();
			Map<String, User> map = new HashMap<String, User>();
			{
				Stream.of(new User[] { new User("123456", "Ketty", 12, new Dept("001", "HR")), new User("123457", "Jack", 22, new Dept("008", "HR")) }).forEach(e -> {
					set.add(e);
					map.put(e.getId(), e);
				});
			}
			wrapper.setUserSet(set);
			wrapper.setUserMap(map);
			
			attrs.put("userWrapper", wrapper);
			
			
			UserWrapper[] userWrapperArray = new UserWrapper[] { wrapper };
			attrs.put("userWrapperArray", userWrapperArray);
			attrs.put("userWrapperList", Arrays.asList(userWrapperArray));

		}
		
		JacksonSerializer serializer = new JacksonSerializer();
		byte[] bytes = serializer.serialize(attrs);
		System.out.println(bytes.length);
		System.out.println("Serialized JSON:");
		System.out.println(new String(bytes, "utf-8"));
		System.out.println();
		
		
		
		Object obj = serializer.deserialize(bytes);
		// 打断点至此，查看各个对象反序列化是否准确
		System.out.println("Deserialized Object:");
		System.out.println(obj);
		System.out.println("$$");
		
		Map<String, Object> attrs2 = (Map<String, Object>) obj;
		{
			assert attrs.size() == attrs2.size();
			
			System.out.println(attrs2.get("userArray11"));
			System.out.println(attrs2.get("userList1"));
			System.out.println(attrs2.get("userWrapperArray"));
			System.out.println(attrs2.get("userWrapperList"));
		}
		
		
		bytes = serializer.serialize(attrs2);
		System.out.println(bytes.length);
		System.out.println("Serialized JSON:");
		System.out.println(new String(bytes, "utf-8"));
		System.out.println();
	}
	

}
