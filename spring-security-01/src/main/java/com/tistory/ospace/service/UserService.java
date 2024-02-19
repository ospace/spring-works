package com.tistory.ospace.service;

import com.tistory.ospace.control.MainController;
import com.tistory.ospace.repository.SecurityUser;
import com.tistory.ospace.repository.User;
import com.tistory.ospace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public  void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername - username[{}]", username);
        User user = userRepo.get(username);
        logger.info("user[{}]", user);
        if (null == user) throw new UsernameNotFoundException(username);
        return new SecurityUser(user);
    }

    public void register(User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        logger.info("register - user[{}]", user);
        userRepo.add(user);
    }
}
