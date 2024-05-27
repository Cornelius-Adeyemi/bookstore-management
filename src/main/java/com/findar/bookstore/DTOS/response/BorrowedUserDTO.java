package com.findar.bookstore.DTOS.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowedUserDTO {

    private String borrowId;

    private Boolean returned;

    private List<BookUserDTO> books;
}
