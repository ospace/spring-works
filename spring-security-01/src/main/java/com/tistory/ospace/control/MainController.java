package com.tistory.ospace.control;

import com.tistory.ospace.repository.UserRepository;
import com.tistory.ospace.repository.User;
import com.tistory.ospace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(User user) {
        logger.info("register: {}", user.getId());
        userService.register(user);

        return "redirect:/";
    }
}
