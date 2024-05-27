package com.findar.bookstore.DTOS.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUserDTO {


    private String title;

    private String author;


    private String pages;


    private String chapters;

    private Boolean deleted;

    private List<BorrowedUserDTO> borrows;

}
