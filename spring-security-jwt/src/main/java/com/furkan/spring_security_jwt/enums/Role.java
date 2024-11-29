package com.furkan.spring_security_jwt.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MOD("MOD");

    String value;

    Role(String value){
     this.value=value;
    }

    String getValue(){
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
