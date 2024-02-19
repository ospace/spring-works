package com.example.demo3.repository;

//import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo3.entity.Department;

//DB연동 #10 JPA 활용하기 4 ( Transaction 처리를 위한 구성 )
//http://egloos.zum.com/codecrue/v/2233248
public interface DepartmentRepositoryJPA extends CrudRepository<Department, Integer>{
//public interface UserRepositoryJPA extends JpaRepository<User, String>{	
//	List<User> findById(String name);
}
