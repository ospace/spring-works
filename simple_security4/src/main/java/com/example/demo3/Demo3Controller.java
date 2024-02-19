package com.example.demo3;

import java.util.Arrays;

//import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;

//import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo3.annotation.TimeLog;
import com.example.demo3.entity.User;
import com.example.demo3.entity.UserRole;
import com.example.demo3.repository.UserRepositoryJPA;

@Controller
@RequestMapping("/")
public class Demo3Controller {
	private static Logger logger = LoggerFactory.getLogger(Demo3Controller.class);
	
//	@Autowired
//	private UserRepository userRepo;
	
//	@Autowired
//	private UserRepositorySqlite userRepo;
	
	@Autowired
	private UserRepositoryJPA userRepo;
	
	private BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder(); 
	
	@ExceptionHandler({Exception.class})
	public void handleException(HttpServletRequest request, Exception ex) {
		logger.error("{}[{}]", ex.getClass().getName(), ex.getMessage(), ex);
	}
	
	@TimeLog
	@PostMapping("/hello")
	@PreAuthorize("hasAuthority('admins')")
	public String foo() {
		logger.info("foo");
		return "redirect:/";
	}
	
	@TimeLog
	@PostMapping("/register")
	public String register(User user) {
		user.setPwd(pwdEncoder.encode(user.getPwd()));
		user.setRoles(Arrays.asList(UserRole.of("BASIC")));
		
		logger.info("register : user[{}]", user);
		
		userRepo.save(user);
		
		return "redirect:/";
	}
	
//	@PostMapping("/login")
//	public String loginFrom(HttpServletRequest req) {
//		String referer = req.getHeader("Referer");
//		req.getSession().setAttribute("prevPage", referer);
//		logger.info("login : Referer[{}]", referer);
//		return "login";
//	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(req, res, auth);
	    }
		return "redirect:/";
	}
}
