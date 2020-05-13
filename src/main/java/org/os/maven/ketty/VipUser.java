package org.os.maven.ketty;

public class VipUser extends User {

	private String vipcode;

	public VipUser() {
		super();
	}

	public VipUser(String id, String name, int age, Dept dept) {
		super(id, name, age, dept);
	}

	public VipUser(String id, String name, int age) {
		super(id, name, age);
	}
	
	public VipUser(String id, String name, int age, Dept dept, String vipcode) {
		super(id, name, age, dept);
		this.vipcode = vipcode;
	}
	
	public VipUser(String id, String name, int age, String vipcode) {
		super(id, name, age);
		this.vipcode = vipcode;
	}

	public String getVipcode() {
		return vipcode;
	}

	public void setVipcode(String vipcode) {
		this.vipcode = vipcode;
	}

	@Override
	public String toString() {
		return "VipUser [vipcode=" + vipcode + ", toString()=" + super.toString() + "]";
	}
	
}
