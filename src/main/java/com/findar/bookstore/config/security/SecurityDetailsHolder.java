package com.findar.bookstore.config.security;


import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class SecurityDetailsHolder extends AbstractAuthenticationToken {

    private String username;

    private String email;

    private String role;

    public SecurityDetailsHolder(String username, String email, String role,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        this.username = username;

        this.email = email;
        this.role = role;
    }

    @Override
    public Object getCredentials() {
        return role;
    }

    @Override
    public Object getDetails(){
       return username;
    }


    @Override
    public Object getPrincipal() {
        return email;
    }
}
