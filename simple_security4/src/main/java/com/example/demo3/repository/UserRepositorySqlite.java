package com.example.demo3.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo3.entity.User;
import com.example.demo3.entity.UserRole;

//http://asfirstalways.tistory.com/299
//https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/appendix-schema.html
/* 화면표시(CLI mode): https://www.sqlite.org/cli.html
 * .mode column
 * .header on
 */
@Component
public class UserRepositorySqlite {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void init() {
		jdbcTemplate.update("CREATE TABLE user (id TEXT PRIMARY KEY, pwd TEXT NOT NULL)");
		jdbcTemplate.update("CREATE TABLE user_role (id TEXT, name TEXT NOT NULL)");
		jdbcTemplate.update("CREATE TABLE groups (id INTEGER PRIMARY KEY, group_name TEXT NOT NULL)");
		jdbcTemplate.update("CREATE TABLE group_authorities (group_id INTEGER, authority TEXT)");
		jdbcTemplate.update("CREATE TABLE group_members (id INTEGER PRIMARY KEY, username TEXT, group_id INTEGER)");
	}
	
	public void add(User user) {
		//users.put(user.getId(), user);
		jdbcTemplate.update("INSERT INTO user(id, pwd) VALUES(?,?)", user.getId(), user.getPwd());
//		for(UserRole it : user.getRoles()) {
//			jdbcTemplate.update("INSERT INTO user_role (id, name) VALUES(?,?)", user.getId(), it.getName());
//		}
	}
	
	public User get(String id) {
		return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<User>(User.class));
	}
	
	public List<UserRole> getUserRole(String id) {
		return (List<UserRole>) jdbcTemplate.query("SELECT * FROM user_role WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<UserRole>(UserRole.class));
	}
}
