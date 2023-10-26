package com.example.demo3.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity(name = "groups")
public class Group {
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE) //다른 테이블과 시쿼스 공유됨
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String group_name;
	
	@ElementCollection
	@CollectionTable(name="group_roles", joinColumns={@JoinColumn(name="id")})
	private List<GroupRole> roles;

	public static Group of(String group_name, List<GroupRole> roles) {
		Group ret = new Group();
		ret.setGroup_name(group_name);
		ret.setRoles(roles);
		return ret;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public List<GroupRole> getRoles() {
		return roles;
	}

	public void setRoles(List<GroupRole> roles) {
		this.roles = roles;
	}
	
	public String toString() {
		return "id["+id+"] group_name["+group_name+"] roles" + (null == roles?"[]":roles.toString());
	}
}
