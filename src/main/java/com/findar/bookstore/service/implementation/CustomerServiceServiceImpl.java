package com.findar.bookstore.service.implementation;


import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.config.security.SecurityDetailsHolder;
import com.findar.bookstore.enums.Constant;
import com.findar.bookstore.exception.Errors;
import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Borrowed;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.BookRepository;
import com.findar.bookstore.repository.BorrowRepository;
import com.findar.bookstore.repository.UserRepository;
import com.findar.bookstore.service.interfaces.CustomerService;
import com.findar.bookstore.util.GetLoginUser;
import com.findar.bookstore.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceServiceImpl implements CustomerService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final BorrowRepository borrowRepository;

    private final Mapper mapper;



    @Override
    @Transactional
    public Object borrowBook(Set<Long> listOfBookId) {


        SecurityDetailsHolder securityDetailsHolder = GetLoginUser.getLoginUser();
        Users users = userRepository.findByEmail(securityDetailsHolder.getEmail()).get();
        log.info("------------------------- user {} want to borrow books with the following ids {}", users.getFirstName().concat( " "+ users.getLastName()), listOfBookId);
        AtomicReference<String> unreturnedId = new AtomicReference<>();

        if( users.getBorrows().stream().anyMatch((bo) -> {
            boolean b = !bo.getReturned();
            unreturnedId.set(bo.getBorrowId());
            return b; })){ //check if user has any unreturned books

            throw new GeneralException(Errors.UNRETURNED_BOOKS, unreturnedId.get());
        }

        Set<Book> books = new HashSet<>();

        for(long id : listOfBookId ){// this fetches book by Id
            Book book = bookRepository.findBookByIdAndDeletedFalse(id).orElseThrow(
                    ()-> new GeneralException(Errors.INVALID_BOOK_ID, id)
            );
            books.add(book);
        }


        Borrowed borrow = new Borrowed();
        borrow.setUser(users);
        borrow.setBooks(books);
        borrow.setBorrowId(generateBorrowedId());
        borrow.setReturned(false);

        for (Book book : books) {
            book.getBorrows().add(borrow); // there is an implementation in the method that
            // reduces the number of books for everything the method is. It checks if the book is avaialble

        }

        users.getBorrows().add(borrow);

        borrowRepository.save(borrow);

        userRepository.save(users);

        bookRepository.saveAll(books);



        return  GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(mapper.borrowMapperToDTO(borrow))
                .build();
    }

    @Override
    @Transactional
    public Object returnBooks(String borrowedId) {

        SecurityDetailsHolder securityDetailsHolder = GetLoginUser.getLoginUser();

        log.info("------------------------- user {} want to return books with borrowId {}", securityDetailsHolder.getEmail(), borrowedId);
        Borrowed borrowed = borrowRepository.findByBorrowId(borrowedId).orElseThrow(
                () -> new GeneralException(Errors.INVALID_BORROWID, borrowedId)
        );

        if (!borrowed.getUser().getUsername().equalsIgnoreCase(securityDetailsHolder.getUsername())) { // this check if it is the login user that borrowed the books

            throw new GeneralException(Errors.UNAUTHORISED_ACCESS_TO_BORROWED_BOOK, borrowedId);
        }

       Set<Book> borrowedBook =  borrowed.getBooks();

        for(Book book : borrowedBook){ // this logic increase the quantity of that book by one in the store and change the availability
            int quantity = book.getAvailableQuantity() + 1;
            book.setAvailable(true);
            book.setAvailableQuantity(quantity);

        }


        borrowed.setReturned(true);

        bookRepository.saveAll(borrowedBook);
       Borrowed returnBorrow = borrowRepository.save(borrowed);

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(mapper.borrowMapperToDTO(returnBorrow))
                .build();


    }




    private String generateBorrowedId(){

         return  "B-".concat(RandomStringUtils.randomNumeric(7));
    }
}
