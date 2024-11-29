package com.furkan.spring_security_jwt.dto;

public record AuthRequest (
    String username,
    String password
)
{}
