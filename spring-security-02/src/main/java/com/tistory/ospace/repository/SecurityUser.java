package com.tistory.ospace.repository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityUser extends  org.springframework.security.core.userdetails.User {
    private static final String ROLE_PREFIX = "ROLE_";
    public SecurityUser(User user) {
        super(user.getId(), user.getPwd(), List.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole())));
    }
}
