package com.example.demo3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo3.annotation.TimeLog;
import com.example.demo3.entity.Department;
import com.example.demo3.entity.User;
import com.example.demo3.repository.DepartmentRepositoryJPA;
import com.example.demo3.repository.DeptPagingRepositoryJPA;
import com.example.demo3.repository.UserMapper;
import com.example.demo3.repository.UserRepositoryJPA;

//https://www.baeldung.com/spring-security-acl

@RestController
@RequestMapping("/api")
public class Demo3RestController {
	private static Logger logger = LoggerFactory.getLogger(Demo3RestController.class);
	
//	private Demo3Configuration2 config2 = Demo3Configuration2.instance();
	
	@Autowired
	private Demo3Configuration config;
	
	//@PersistenceContext
	//@Autowired
	//private EntityManager entityManager;
	
	@Autowired
	private UserRepositoryJPA userRepo;
	
	@Autowired
	private DepartmentRepositoryJPA deptRepo;
	
	@Autowired
	private DeptPagingRepositoryJPA deptPageRepo;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired(required=false)
	private FooComponent foo;
	
	private BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder(); 
	
	//@Transactional
	@PostConstruct
	private void init() {
//		userRepo.save(User.of("z", pwdEncoder.encode("z"), Arrays.asList(UserRole.of("BASIC"))));
//		userRepo.save(User.of("z", pwdEncoder.encode("z"), null));
//		userRepo.save(User.of("y", pwdEncoder.encode("y"), Arrays.asList(UserRole.of("ADMIN"))));
//		
//		logger.info("inited");
		logger.info("FooComponent : {}", (null==foo?"disable":"enable"));
		logger.info(">> name : {}, {}", config.getName(), config.getName2());
		
		List<User> users = new ArrayList<>();
		for(int i=0; i<3; ++i) {
			users.add(User.of("u"+i, "pwd"+i));
		}
		
		userRepo.saveAll(users);
		
		List<Department> depts = new ArrayList<>();
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			depts.add(Department.of("dept"+depts.size(), it.next(), it.hasNext()?it.next():null));
		}
		
		deptRepo.saveAll(depts);
		//entityManager.persist(Department.of("deptA", Arrays.asList(u1,u3)));
		
		deptRepo.delete(depts.get(0));
		
		Demo3Configuration2 config2 = Demo3Configuration2.instance();
		logger.info("Demo3Configuration2 config : {}", config2.getPropertiesFile());
	}
	
	@TimeLog
	@RequestMapping("/ping")
	public String ping(@RequestParam(name="msg", required=false)String msg) {
		return (null == msg || msg.isEmpty()) ? "pong" : msg;
	}
	
	@RequestMapping("/user/{id}")
	@Retryable(value = { Exception.class }, maxAttempts = 2)
	public User getUser(@PathVariable("id")String id) {
		return userRepo.findById(id).get();
	}
	
	@RequestMapping("/addUser")
	public void addUser(@RequestParam(name="id")String id, @RequestParam(name="pwd")String pwd) {
		User user = new User();
		user.setId(id);
		user.setPwd(pwd);
		userRepo.save(user);
	}
	
	/*
	 * mybatis api
	 */
	@GetMapping("/mybatis/user/{id}")
	public User mybatisSelectUser(@PathVariable("id")String id) {
		return userMapper.select(id);
	}
	
	@GetMapping("/mybatis/user")
	public List<User> mybatisSelectUserAll() {
		return userMapper.selectAll();
	}
	
	@PutMapping(value="/mybatis/user", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void mybatisInsertUser(@RequestBody User user) {
		logger.info("user[{}]", user);
		userMapper.insert(user);
	}
	
	@PostMapping(value="/mybatis/user", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void mybatisUpdateUser(@RequestBody User user) {
		logger.info("user[{}]", user);
		userMapper.update(user);
	}
	
	@DeleteMapping("/mybatis/user/{id}")
	public void mybatisDeleteUser(@PathVariable("id")String id) {
		userMapper.delete(id);
	}
	
	/*
	 * jpa api
	 */
	@GetMapping("/jpa/user/{id}")
	public User jpaSelectUser(@PathVariable("id")String id) {
		return userRepo.findById(id).get();
	}
	
	@GetMapping("/jpa/user")
	public Iterable<User> jpaSelectUserAll() {
		return userRepo.findAll();
	}
	
	@PutMapping(value="/jpa/user", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void jpaInsertUser(@RequestBody User user) {
		logger.info("user[{}]", user);
		userRepo.save(user);
	}
	
	@PostMapping(value="/jpa/user", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void jpaUpdateUser(@RequestBody User user) {
		logger.info("user[{}]", user);
		userRepo.save(user);
	}
	
	@DeleteMapping("/jpa/user/{id}")
	public void jpaDeleteUser(@PathVariable("id")String id) {
		userRepo.deleteById(id);
	}
	
	@GetMapping("/jpa/dept/{id}")
	public Department jpaSelectDept(@PathVariable("id")Integer id) {
		return deptRepo.findById(id).get();
	}
	
	@GetMapping("/jpa/dept")
	public Iterable<Department> jpaSelectDeptAll() {
		Pageable pageable = PageRequest.of(100, 10, Sort.by("name").descending());
		
		return deptPageRepo.findAll(pageable);
		//return deptRepo.findAll();
	}
	
	@PutMapping(value="/jpa/dept", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void jpaInsertDept(@RequestBody Department dept) {
		logger.info("department[{}]", dept);
		deptRepo.save(dept);
	}
	
	@PostMapping(value="/jpa/dept", consumes={ MediaType.APPLICATION_JSON_VALUE })
	public void jpaUpdateUser(@RequestBody Department dept) {
		logger.info("department[{}]", dept);
		deptRepo.save(dept);
	}
	
	@DeleteMapping("/jpa/dept/{id}")
	public void jpaDeleteDept(@PathVariable("id")Integer id) {
		deptRepo.deleteById(id);
	}
	
	@TimeLog
	@PostMapping("/register")
	public void register(User user) {
		user.setPwd(pwdEncoder.encode(user.getPwd()));
//		user.setRoles(Arrays.asList(UserRole.of("BASIC")));
		
		logger.info("register : user[{}]", user);
		
		userRepo.save(user);
	}
	
}
