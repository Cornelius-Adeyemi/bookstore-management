package com.findar.bookstore.service.implementation;

import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.TestUtil;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.BookRepository;
import com.findar.bookstore.repository.UserRepository;
import com.mysql.cj.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

  @Mock
    private  BookRepository bookRepository;
@Mock
    private UserRepository userRepository;

@InjectMocks
   private AdminServiceImpl adminService;


    @Test
    void addBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(TestUtil.getBook());


        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) adminService.addBook(TestUtil.addBookDTO());

        assertNotNull(generalResponseDTO);
        assertEquals(generalResponseDTO.isSuccess(), true);
    }



    @Test
    void updateBook() {

        when(bookRepository.findBookById(any(Long.class))).thenReturn(Optional.of(TestUtil.getBook()));
        when(bookRepository.save(any(Book.class))).thenReturn(TestUtil.getBook());
        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) adminService.updateBook(1l, TestUtil.getUpdateBook());

        assertNotNull(generalResponseDTO);

    }

    @Test
    void disableUser() {

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(TestUtil.getUser()));
      //  when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn( new Users());
        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) adminService.disableUser(TestUtil.getUser().getEmail());

        assertNotNull(generalResponseDTO);

    }



    @Test
    void deleteBook() {
        when(bookRepository.findBookById(any(Long.class))).thenReturn(Optional.of(TestUtil.getBook()));
        when(bookRepository.save(any(Book.class))).thenReturn(TestUtil.getBook());
        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) adminService.deleteBook(1l);

        assertNotNull(generalResponseDTO);
    }


}