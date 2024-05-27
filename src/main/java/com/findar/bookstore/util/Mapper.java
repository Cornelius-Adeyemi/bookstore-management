package com.findar.bookstore.util;

import com.findar.bookstore.DTOS.response.BookUserDTO;
import com.findar.bookstore.DTOS.response.BorrowedUserDTO;
import com.findar.bookstore.DTOS.response.UserResponseDTO;
import com.findar.bookstore.enums.Role;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Borrowed;
import com.findar.bookstore.model.entity.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {


    public UserResponseDTO userMapperToDto(Users users){

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .email(users.getEmail())
                .userName(users.getUsername())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .active(users.getActive())
                .role(Role.valueOf( users.getRole()) )
                .borrows(users.getBorrows().stream().map(this::borrowMapperToDTO).collect(Collectors.toList()))
                .build();

        return userResponseDTO;

    }


    public BorrowedUserDTO borrowMapperToDTO(Borrowed borrowed){

        BorrowedUserDTO borrowedUserDTO  = BorrowedUserDTO.builder()
                .borrowId(borrowed.getBorrowId())
                .returned(borrowed.getReturned())
                .books(borrowed.getBooks().stream().map(this::bookMapperToDTO).collect(Collectors.toList()))
                .build();


        return borrowedUserDTO;
    }

    public  BookUserDTO bookMapperToDTO(Book book){

        BookUserDTO  bookUserDTO = BookUserDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .pages(book.getPages())
                .chapters(book.getChapters())
                .build();

        return bookUserDTO;

    }

    public  BookUserDTO bookMapperToDTOThatShowsBorrows(Book book){

        BookUserDTO  bookUserDTO = BookUserDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .pages(book.getPages())
                .borrows(book.getBorrowsAdminUse().stream().map(this::borrowMapperWithoutBooks).toList())
                .chapters(book.getChapters())
                .build();

        return bookUserDTO;

    }

    public BorrowedUserDTO borrowMapperWithoutBooks(Borrowed borrowed){
        BorrowedUserDTO borrowedUserDTO = BorrowedUserDTO.builder()
                .returned(borrowed.getReturned())
                .borrowId(borrowed.getBorrowId())
                .build();

        return borrowedUserDTO;
    }

}
