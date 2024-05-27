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
      //  return userRepository.findByEmail(username).orElse( userRepository.findByUserName(username))
      try {
          Optional<Users> userByEmail = userRepository.findByEmail(username);
          if (userByEmail.isPresent()) {
              log.info("User found by email: " + username);
              return userByEmail.get();
          }

          Optional<Users> userByUsername = userRepository.findByUserName(username);
          if (userByUsername.isPresent()) {
              log.info("User found by username: " + username);
              return userByUsername.get();
          }

          log.error("User not found with username or email: " + username);
          throw new UsernameNotFoundException("User " + username + " not found with username or email");

      }catch (UsernameNotFoundException e){

          throw new GeneralException(e.getMessage(), HttpStatus.NOT_FOUND, null);
      }

    }
}
