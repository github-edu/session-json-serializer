package org.os.maven.ketty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserWrapper {
	
	private List<User> userList = new ArrayList<User>();
	
	private Set<User> userSet = new HashSet<User>();
	
	private Map<String, User> userMap = new HashMap<String, User>();
	
	private User[] userArray = new User[] {};

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	public Map<String, User> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}

	public User[] getUserArray() {
		return userArray;
	}

	public void setUserArray(User[] userArray) {
		this.userArray = userArray;
	}
	
	

}
