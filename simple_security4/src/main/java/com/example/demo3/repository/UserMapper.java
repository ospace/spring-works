package com.example.demo3.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo3.entity.User;

@Mapper
public interface UserMapper {
	public void insert(User user);
	public User select(String id);
	public List<User> selectAll();
	public void update(User user);
	public void delete(String id);
}
