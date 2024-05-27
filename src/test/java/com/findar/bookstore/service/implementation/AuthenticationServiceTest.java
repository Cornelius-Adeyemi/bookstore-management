package com.findar.bookstore.service.implementation;

import com.findar.bookstore.DTOS.request.LoginDTO;
import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.TestUtil;
import com.findar.bookstore.config.jwt.JwtService;
import com.findar.bookstore.config.security.CustomUserDetailService;
import com.findar.bookstore.config.security.CustomerUserDetails;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
  @Mock
    private  PasswordEncoder passwordEncoder;
  @Mock
    private AuthenticationManager authenticationManager;
@Mock
    private  UserRepository userRepository;
@Mock
    private  CustomUserDetailService userDetailsService;
@Mock
    private  JwtService jwtService;
@InjectMocks
private  AuthenticationService authenticationService;

@Test
  void signUp(){

      when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
      when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.empty());
      when(userRepository.save(any(Users.class))).thenReturn( new Users());
     when(passwordEncoder.encode(any(String.class))).thenReturn("yesss");
      GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) authenticationService.signup(TestUtil.getUserDto());

      assertNotNull(generalResponseDTO);
  }


    @Test
    void loginUp(){

     String token= "lola";
        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(TestUtil.getUserDetailsDto());
        when(passwordEncoder.matches(TestUtil.getLoginDTO().getPassword(), TestUtil.getUserDetailsDto().getPassword())).thenReturn(true);
        when(jwtService.generateToken(any(CustomerUserDetails.class))).thenReturn(token);

        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) authenticationService.login(TestUtil.getLoginDTO());

        assertNotNull(generalResponseDTO);

        assertEquals(token,((HashMap<String, Object>)generalResponseDTO.getData()).get("token") );
    }



}