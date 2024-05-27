package com.findar.bookstore.DTOS.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotEmpty
    private String email;


    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;


    @NotEmpty
    private String lastName;

    private LocalDateTime createdAt;

    private String role;

    private LocalDateTime lastModifiedAt;

}
