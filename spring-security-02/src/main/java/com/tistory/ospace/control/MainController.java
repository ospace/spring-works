package com.tistory.ospace.control;

import com.tistory.ospace.repository.SecurityUser;
import com.tistory.ospace.repository.UserRepository;
import com.tistory.ospace.repository.User;
import com.tistory.ospace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.Arrays;
@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(User user) {
        userService.register(user);

        return "redirect:/";
    }

    @GetMapping("/dropout")
    @PreAuthorize("hasRole('USER') && !hasRole('ADMIN')")
    public String dropout() {
        String id =  SecurityContextHolder.getContext().getAuthentication().getName();

        userService.dropout(id);
        ServletRequestAttributes requestAttr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (null != requestAttr) {
            requestAttr.getRequest().getSession().invalidate();
        }

        return "redirect:/";
    }
    @ResponseBody
    @DeleteMapping("/api/user/{id}")
    @PreAuthorize("hasRole('ADMIN') && #id != authentication.name")
    public void deleteUser(@PathVariable String id) {
        userService.dropout(id);
    }
}
