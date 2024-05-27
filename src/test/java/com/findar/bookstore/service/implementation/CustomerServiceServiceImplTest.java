package com.findar.bookstore.service.implementation;

import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.TestUtil;
import com.findar.bookstore.config.security.SecurityDetailsHolder;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Borrowed;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.BookRepository;
import com.findar.bookstore.repository.BorrowRepository;
import com.findar.bookstore.repository.UserRepository;
import com.findar.bookstore.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceServiceImplTest {
  @Mock
    private  BookRepository bookRepository;
@Mock
    private  UserRepository userRepository;
@Mock
    private  BorrowRepository borrowRepository;
@Mock
private Mapper mapper;

@InjectMocks
private CustomerServiceServiceImpl customerServiceService;

    @Test
    void borrowBook() {
        Users users = TestUtil.getUser();
        SecurityDetailsHolder loginUser = new SecurityDetailsHolder(users.getUsername(),
                users.getEmail(), users.getRole().toString(), users.getAuthorities()   );

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(loginUser);

        SecurityContextHolder.setContext(securityContext);
        Set<Long>  setOfId = Set.of(1l);
        when(bookRepository.findBookByIdAndDeletedFalse(any(Long.class))).thenReturn(Optional.of(TestUtil.getBook()));

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(TestUtil.getUser()));
        when(bookRepository.saveAll(any(Set.class))).thenReturn(List.of());
        when(userRepository.save(any(Users.class))).thenReturn( new Users());
        when(borrowRepository.save(any(Borrowed.class))).thenReturn( new Borrowed());

        GeneralResponseDTO generalResponseDTO = (GeneralResponseDTO) customerServiceService.borrowBook(setOfId);

        assertNotNull(generalResponseDTO);
    }
}