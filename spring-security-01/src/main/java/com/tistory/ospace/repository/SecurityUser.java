package com.tistory.ospace.repository;

import java.util.ArrayList;

public class SecurityUser extends  org.springframework.security.core.userdetails.User {
    public SecurityUser(User user) {
        super(user.getId(), user.getPwd(), new ArrayList<>());
    }
}
