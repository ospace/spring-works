package com.tistory.ospace.service;

import com.tistory.ospace.control.MainController;
import com.tistory.ospace.repository.SecurityUser;
import com.tistory.ospace.repository.User;
import com.tistory.ospace.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public  void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.get(username);
        if (null == user) throw new UsernameNotFoundException(username);
        return new SecurityUser(user);
    }

    public void register(User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        userRepo.add(user);
    }
    public void dropout(String id) {
        userRepo.remove(id);
    }
}
