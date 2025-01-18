package com.findar.bookstore.config.security;

import org.springframework.security.core.userdetails.UserDetails;


public interface CustomerUserDetails extends UserDetails {


    public String getEmail();

    public String getRole();

    Boolean getTwoFa();
}
