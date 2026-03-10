package com.example.ticketbooking.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryAuthenticationService implements UserDetailsService {

    private final Map<String, String> users = new HashMap<>();

    public InMemoryAuthenticationService() {
        // Add some test users
        users.put("admin", "password123");
        users.put("user", "password456");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = users.get(username);
        if (password == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return User.builder()
                .username(username)
                .password(password)
                .roles(username.equals("admin") ? "ADMIN" : "USER")
                .build();
    }

    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}