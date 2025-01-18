package com.findar.bookstore.config.security;


import com.findar.bookstore.config.jwt.JwtService;
import com.findar.bookstore.exception.Errors;
import com.findar.bookstore.exception.GeneralException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService userDetailsService;

    private final JwtService jwtService;

    private final List<String> patternsToExclude = Arrays.asList(SecurityConfiguration.WHITE_LIST);


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return patternsToExclude.stream().anyMatch(pattern -> new AntPathMatcher().match(pattern,request.getServletPath()));
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,@NotNull  FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");
        String token;
        String userEmail;


         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             throw new GeneralException(Errors.INVALID_JWT_TOKEN, null);
             //   filterChain.doFilter(request, response);
             //   return;
         }


         token = authHeader.substring(7);
         log.info("Token ----->{}", token);

         try {
             userEmail = jwtService.extractUsername(token);
         }catch (Exception e){   throw new GeneralException(Errors.INVALID_JWT_TOKEN, null);}

         if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

             CustomerUserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

             log.info("User details ----->{}", userDetails);

             if (jwtService.isTokenValid(token, userDetails)) {

                 SecurityDetailsHolder tokenAuthentication = new SecurityDetailsHolder(userDetails.getUsername(), userDetails.getEmail(), userDetails.getRole(),
                         userDetails.getAuthorities());
                 tokenAuthentication.setAuthenticated(true);
                 SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
             }

         }
         filterChain.doFilter(request, response);




    }




}
