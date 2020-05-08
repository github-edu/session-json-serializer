package org.os.maven.ketty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonTest {
	
	final static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		
		{
			Set<String> set = new HashSet<>();
			set.add("Java");
			set.add("Python");
			set.add("Go");
			System.out.println(mapper.writeValueAsString(set));
			List<String> list = new ArrayList<>();
			list.add("Java");
			list.add("Python");
			list.add("Go");
			System.out.println(mapper.writeValueAsString(list));
			System.out.println("$$");
		}
		
		
		User user = new User("123456", "Ketty", 12, new Dept("001", "HR"));
		System.out.println(user);System.out.println();
		
		if (mapper.canSerialize(User.class)) {
			String json = mapper.writeValueAsString(user);
			System.out.println(json);
			
			Object obj = mapper.readValue(json, User.class);
			System.out.println(obj);
		}
		
		final String content = mapper.writeValueAsString(new Object[] { 1, Long.MAX_VALUE, Byte.MAX_VALUE, Short.MAX_VALUE, 'c', "3", 4.3f, 4.6d, new Date(),
				new User("123456", "Ketty", 12, new Dept("001", "HR")).initFooBar(12345, "FooBar") });
		System.out.println();
		System.out.println(content);
		System.out.println();
		
		final List<?> list = mapper.readValue(content, List.class);
		for (Object obj : list) {
			System.out.println(obj + ", " + obj.getClass());
		}
		
		
		System.out.println("$$$");
		
		final String content2 = mapper.writeValueAsString(transfer(new Object[] { 1, Long.MAX_VALUE, Byte.MAX_VALUE, Short.MAX_VALUE, 'c', "3", 4.3f, 4.6d, new Date(),
				new User("123456", "Ketty", 12, new Dept("001", "HR")).initFooBar(12345, "FooBar") }));
		System.out.println();
		System.out.println(content2);
		System.out.println();
		
		final List<?> list2 = mapper.readValue(content2, List.class);
		for (Object obj : list2) {
			System.out.println(obj + ", " + obj.getClass());
		}
		System.out.println("\n$$$\n");
		{
			JsonNode root = mapper.readTree(content2);
			if (root.isArray()) {
				Iterator<JsonNode> elements = root.elements();
				while (elements.hasNext()) {
					JsonNode node = elements.next();
					if (!node.isArray()) {
						ObjectNode objNode = (ObjectNode)node;
						String clazz = objNode.findValue("clazz").asText();
						JsonNode realObj = objNode.findValue("object");
						System.out.print(clazz + " $$$ ");
						if (Integer.class.getName().equals(clazz)) {
							System.out.println(realObj.asInt());
						} else if (Long.class.getName().equals(clazz)) {
							System.out.println(realObj.asLong());
						} else if (Byte.class.getName().equals(clazz)) {
							System.out.println(realObj.asInt());
						} else if (Short.class.getName().equals(clazz)) {
							System.out.println(realObj.asInt());
						} else if (Double.class.getName().equals(clazz)) {
							System.out.println(realObj.asDouble());
						} else if (Float.class.getName().equals(clazz)) {
							System.out.println(realObj.asDouble());
						} else if (Boolean.class.getName().equals(clazz)) {
							System.out.println(realObj.asBoolean());
						} else if (Character.class.getName().equals(clazz)) {
							System.out.println(realObj.asText());
						} else if (String.class.getName().equals(clazz)) {
							System.out.println(realObj.asText());
						} else if (Date.class.getName().equals(clazz)) {
							long time = realObj.asLong(-1);
							if (time > 0) {
								System.out.println(new Date(time));
							} else {
								System.out.println(realObj.asText());
							}
						} else {
							Object element = mapper.readValue(realObj.toString(), getClazz(clazz));
					        System.out.println(element);
						}
					} else {
						// TODO
						System.err.println("Illegal json field, ignore.");
					}
			      }
			}
		}
		
	}
	
	final static Map<String, Class<?>> classes = new ConcurrentHashMap<String, Class<?>>();
	final static Class<?>[] BASIC_CLASSES = new Class[] { Integer.class, Long.class, Double.class, Float.class, Boolean.class, String.class, Date.class };
	
	static Class<?> getClazz(String clazz) throws Exception {
		if (null == clazz || clazz.trim().isEmpty()) {
			return null;
		}
		/*
		if (clazz.startsWith("java.lang.")) {
			Class<?> ooo = Stream.of(BASIC_CLASSES).filter(e -> e.getName().equals(clazz)).findFirst().orElse(null);
			if (null != ooo) {
				return ooo;
			}
		}
		
		if (classes.containsKey(clazz)) {
			return classes.get(clazz);
		}
		Class<?> ooo = Class.forName(clazz);
		classes.put(clazz, ooo);
		*/
		Class<?> ooo = ClassLoader.getSystemClassLoader().loadClass(clazz);
		
		return ooo;
	}
	
	static List<SerializeObject> transfer(Object[] data) {
		if (null == data || 0 == data.length) {
			return new ArrayList<SerializeObject>();
		}
		return Stream.of(data).map(e -> new SerializeObject(e)).collect(Collectors.toList());
	}

}
