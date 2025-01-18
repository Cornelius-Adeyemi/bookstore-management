package com.findar.bookstore.controller;


import com.findar.bookstore.DTOS.request.LoginDTO;
import com.findar.bookstore.DTOS.request.UserDTO;
import com.findar.bookstore.service.implementation.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/process")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    @Operation(summary = "This is an endpoint to login. You cab either login using your email or username")
    @ResponseStatus(HttpStatus.OK)
    public Object login(@Valid @RequestBody LoginDTO loginDTO){

        return  authenticationService.login(loginDTO);
    }


    @GetMapping("/verify-otp")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object verifyOtpCode(@RequestParam("user") String user, @RequestParam("otp") String otp){
        return authenticationService.validateOtP(user, otp);
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is an endpoint to sign up")
    public Object signup(@Valid @RequestBody UserDTO userDTO){

        return  authenticationService.signup(userDTO);
    }
}
