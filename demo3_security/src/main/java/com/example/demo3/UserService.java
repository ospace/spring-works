package com.example.demo3;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;

import com.example.demo3.entity.User;
import com.example.demo3.entity.UserRole;
import com.example.demo3.repository.UserRepositorySqlite;

//@Service
public class UserService {
//	public class UserService implements UserDetailsService {	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
//	@Autowired
//	private UserRepository userRepo;
	
	@Autowired
	private UserRepositorySqlite userRepo;
	
//	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userRepo.get(id);
		if(null == user) throw new UsernameNotFoundException(id);
		List<UserRole> roles = userRepo.getUserRole(id);
		
		logger.info("loadUserByUsername : id[{}] roles[{}]", id, toString(roles));
		return new SecurityUser(user);
	}

	private String toString(List<UserRole> roles) {
		if (null == roles) return "-";
		List<String> strRoles = new ArrayList<>();
		for(UserRole it : roles) strRoles.add(it.toString());
		return String.join(",", strRoles);
	}
}
