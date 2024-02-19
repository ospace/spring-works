package com.example.demo3.entity;

import javax.persistence.Embeddable;

@Embeddable
public class GroupRole {
	private String authority;

	public static GroupRole of(String authority) {
		GroupRole ret = new GroupRole();
		ret.setAuthority(authority);
		return ret;
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public String toString() {
		return "authority["+authority+"]";
	}
}
