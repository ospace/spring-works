package com.example.demo3.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

//https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
//https://spring.io/guides/gs/accessing-data-jpa/

//https://antoniogoncalves.org/2009/11/01/mapping-and-querying-a-list-of-primitive-types-with-jpa-2-0/
//http://www.java2s.com/Tutorial/Java/0355__JPA/OneToManyListCollection.htm

/*
 * 일반 사용자 정보로 사용자 식별 및 패스워드 관리
 */
//@Entity(name = "users")
@Entity//(name = "users")
public class User {
//	@GeneratedValue(strategy = GenerationType.SEQUENCE) //다른 테이블과 시쿼스 공유됨
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private String id;
	private String pwd;
	private String name;
	
	@ElementCollection
	//@CollectionTable(name="user_role", joinColumns={@JoinColumn(name="id")})
//	@CollectionTable(joinColumns={@JoinColumn(name="id")})
	private List<UserRole> roles;
	// 자동으로 현재 엔티티명와 속성명을 합친 USER_ROLES 테이블을 검색
	// 테이블을 변경하고 싶다면 @CollectionTable(name="foo")를 사용
	// User 테이블에는 사용자 정보가 Roles 테이블은 역할 정보가 저장됨
	// Roles 테이블에 USER_ID 외래키가 포함됨
	
//	@ManyToOne
//	private Group group;
	
	//@ManyToOne
	//private Department department;
	
//	@ElementCollection
//	private List<String> hobbies;
	
	public static User of(String id, String pwd, List<UserRole> roles) {
		User ret = new User();
		ret.setId(id);
		ret.setPwd(pwd);
		ret.setRoles(roles);
		return ret;
	}
	
	public static User of(String id, String pwd, Group group) {
		User ret = new User();
		ret.setId(id);
		ret.setPwd(pwd);
		//ret.setGroup(group);
		return ret;
	}
	
	public static User of(String id, String pwd) {
		User ret = new User();
		ret.setId(id);
		ret.setPwd(pwd);
		return ret;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRole> getRoles() {
		return roles;
	}
	
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
//	public Group getGroup() {
//		return group;
//	}
//
//	public void setGroup(Group group) {
//		this.group = group;
//	}

//	public List<String> getHobbies() {
//		return hobbies;
//	}
//
//	public void setHobbies(List<String> hobbies) {
//		this.hobbies = hobbies;
//	}

	public String toString() {
		return "id["+id+"] pwd["+pwd+"] roles["+roles+"]";
	}
}


