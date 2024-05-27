package com.findar.bookstore.DTOS.response;


import com.findar.bookstore.enums.Role;
import com.findar.bookstore.model.entity.Borrowed;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {



    private String email;


    private String userName;




    private String firstName;

    private String lastName;


    private Role role;

    private Boolean active;

    private List<BorrowedUserDTO> borrows;

}
