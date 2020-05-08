package org.os.maven.ketty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class User {
	
	private String id;
	
	private String name;
	
	private int age;
	
	private Dept dept;
	
	private Foo foo;
	
	private Bar bar;

	public User() {
		super();
	}

	public User(String id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public User(String id, String name, int age, Dept dept) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.dept = dept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Foo getFoo() {
		return foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public Bar getBar() {
		return bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", dept=" + dept + ", foo=" + foo + ", bar=" + bar
				+ "]";
	}

	public User initFooBar(int id, String name) {
		foo = new Foo(id + 1000000, "F-" + name);
		bar = new Bar(id + 2000000, "B-" + name);
		return this;
	}
	
	/**
	 * 内部类和静态内部类需要天剑JSON序列化反序列化注解
	 */
	
	@JsonSerialize
	@JsonDeserialize
	public static class Foo {
		@JsonProperty
		private int id;
		@JsonProperty
		private String name;
		public Foo() {
			super();
		}
		public Foo(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Foo [id=" + id + ", name=" + name + "]";
		}
	}
	
	@JsonSerialize
	@JsonDeserialize
	public class Bar {
		@JsonProperty
		private int id;
		@JsonProperty
		private String name;
		public Bar() {
			super();
		}
		public Bar(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Bar [id=" + id + ", name=" + name + "]";
		}
	}

}
