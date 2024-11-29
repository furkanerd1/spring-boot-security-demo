package com.furkan.spring_security_jwt.dto;

import com.furkan.spring_security_jwt.enums.Role;

import java.util.Set;

public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
)
{}
