package com.findar.bookstore;

import com.findar.bookstore.DTOS.request.AddBookDTO;
import com.findar.bookstore.DTOS.request.LoginDTO;
import com.findar.bookstore.DTOS.request.UpdateBookDTO;
import com.findar.bookstore.DTOS.request.UserDTO;
import com.findar.bookstore.config.security.CustomerUserDetails;
import com.findar.bookstore.enums.Role;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Users;

public class TestUtil {

    public static UserDTO getUserDto(){

        return UserDTO.builder()
                .email("aadebisi@gmail.com")
                .userName("adebisi")
                .lastName("adebisi")
                .firstName("bola")
                .password("shola")
                .build();
    }

    public static CustomerUserDetails getUserDetailsDto(){
        Users users = new Users();
        users.setPassword("adebisi");
        users.setEmail("aadebisi@gmail.com");
        users.setUserName("adebisi");


        return users;
    }

    public static Users getUser(){
        Users users = new Users();
        users.setPassword("adebisi");
        users.setEmail("aadebisi@gmail.com");
        users.setUserName("adebisi");
        users.setRole(Role.CUSTOMER);


        return users;
    }

    public static AddBookDTO addBookDTO(){
        AddBookDTO addBookDTO = new AddBookDTO();
        addBookDTO.setTitle("yes");
        addBookDTO.setAuthor("yes");
        addBookDTO.setPages("20");
        addBookDTO.setChapters("15");
        addBookDTO.setAvailableQuantity(20);

        return addBookDTO;

    }

    public static UpdateBookDTO getUpdateBook(){
        UpdateBookDTO addBookDTO = new UpdateBookDTO();
        addBookDTO.setTitle("yes");
        addBookDTO.setAuthor("yes");
        addBookDTO.setPages("20");
        addBookDTO.setChapters("15");
        addBookDTO.setAvailableQuantity(20);

        return addBookDTO;
    }

    public static Book getBook(){
      Book book = new Book();
        book.setTitle("yes");
        book.setAuthor("yes");
        book.setPages("20");
        book.setChapters("15");
        book.setAvailableQuantity(20);
        book.setAvailable(true);

        return book;
    }


    public static LoginDTO getLoginDTO(){

        LoginDTO loginDTO = new LoginDTO("aadebisi@gmail.com", "adebisi");
        return  loginDTO;
    }
}
