package com.findar.bookstore.config.security;

import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Primary
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomerUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return userRepository.findByEmail(username).orElseGet(
                  ()-> userRepository.findByUserName(username).orElseThrow(
                          ()-> new GeneralException(username + " not found with username or email", HttpStatus.NOT_FOUND, null)
                  )
          );


    }
}
