package com.findar.bookstore.model.audith.config;

import com.findar.bookstore.config.security.SecurityDetailsHolder;
import com.findar.bookstore.util.GetLoginUser;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityDetailsHolder loginUser = GetLoginUser.getLoginUser();

        if( loginUser != null &&  loginUser.isAuthenticated() ){

            return Optional.of(loginUser.getEmail());
        }

        return Optional.empty();
    }
}
