package com.example.demo3.repository;

//import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo3.entity.User;

public interface UserRepositoryJPA extends CrudRepository<User, String>{
//public interface UserRepositoryJPA extends JpaRepository<User, String>{	
//	List<User> findById(String name);
}
