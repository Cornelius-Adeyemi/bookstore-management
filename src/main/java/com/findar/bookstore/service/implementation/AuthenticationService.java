package com.findar.bookstore.service.implementation;


import com.findar.bookstore.DTOS.request.LoginDTO;
import com.findar.bookstore.DTOS.request.UserDTO;
import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.config.jwt.JwtService;
import com.findar.bookstore.config.security.CustomUserDetailService;
import com.findar.bookstore.config.security.CustomerUserDetails;
import com.findar.bookstore.enums.Constant;
import com.findar.bookstore.enums.Errors;
import com.findar.bookstore.enums.Role;
import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

    private final CustomUserDetailService userDetailsService;

  private final JwtService jwtService;

    public Object login(LoginDTO loginDTO){
       log.info("-----------------> {} Login ",loginDTO.getEmail());
        CustomerUserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        if(!passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){// check if password is valid
            throw new GeneralException(Errors.INVALID_PASSWORD,null);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                loginDTO.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken); // // this checks all necessary field to see if the user is valid and not disable
        }catch (Exception e){
         throw new GeneralException(e.getMessage(), HttpStatus.UNAUTHORIZED,null );
        }


        String  token = jwtService.generateToken(userDetails);
        HashMap<String, String> tokenObject = new HashMap<>();

        tokenObject.put("token", token);
        return GeneralResponseDTO.builder()
                .message(Constant.LOGIN_SUCCESSFUL.getMessage())
                .success(true)
                .data(tokenObject)
                .build();



    }


    public Object signup(UserDTO userDTO){
        log.info("-----------------> {} Sign up ",userDTO.getEmail());
      HashMap<String, String> error = new HashMap<>();

      Optional<Users>  user = userRepository.findByEmail(userDTO.getEmail());

      if(user.isPresent()) error.put("email", Errors.USER_EMAIL_ALREADY_EXIST.getErrorMessage()); // check if email is not taken

      if(userDTO.getUserName() != null ) {
       Optional<Users> user2 = userRepository.findByUserName(userDTO.getUserName()); // check if username is not taken
       if(user2.isPresent()) error.put("email", Errors.USERNAME_ALREADY_EXIST.getErrorMessage());
     }

      if(!error.isEmpty()){

        throw new GeneralException(Errors.ERROR_SIGNING_UP, error);
      }


      Users newUser = userDTOMapper(userDTO);

       Users returnUser =   userRepository.save(newUser);

       return GeneralResponseDTO.builder()
               .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
               .success(true)
               .data(UserMapperToDTO(returnUser))
               .build();



    }

    private Users userDTOMapper(UserDTO userDTO){
      Users users = new Users();
      users.setEmail(userDTO.getEmail());
      users.setUserName(userDTO.getUserName() == null? userDTO.getEmail(): userDTO.getUserName());
      users.setActive(true);
      users.setRole(Role.CUSTOMER);
      users.setFirstName(userDTO.getFirstName());
      users.setLastName(userDTO.getLastName());
      users.setPassword(passwordEncoder.encode(userDTO.getPassword()));

      return users;

    }

    private UserDTO UserMapperToDTO(Users users){

        UserDTO userDTO = UserDTO.builder()
                .userName(users.getUsername())
                .email(users.getEmail())
                .lastName(users.getLastName())
                .firstName(users.getFirstName())
                .role(users.getRole())
                .build();

       return userDTO;
    }
}
