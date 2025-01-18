package com.findar.bookstore.service.implementation;


import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.enums.Constant;
import com.findar.bookstore.exception.Errors;
import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.repository.BookRepository;
import com.findar.bookstore.service.interfaces.BookService;
import com.findar.bookstore.util.BookSpecification;
import com.findar.bookstore.util.Mapper;
import com.findar.bookstore.util.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final Mapper mapper;




    private  final BookRepository bookRepository;

    public Object getAllBooks(int pageNum, int pageSize){
        log.info("------------------------- get all  book");
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdTime").descending());

        Page<Book> bookPage = bookRepository.findAllByDeletedFalse(pageable);

        HashMap<String, Object> paginatedResponse = PaginatedResponse.paginatedResponse(
                bookPage.getContent().stream().map(mapper::bookMapperToDTOThatShowsBorrows).collect(Collectors.toList()),
                pageNum, bookPage.getTotalPages(), bookPage.getTotalElements(),bookPage.isFirst(),
                bookPage.isLast()
        );

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(paginatedResponse)
                .build();


    }

    @Override
    public Object getABook(Long id) {

        log.info("------------------------- get book with id: {}", id);
        Book  book = bookRepository.findBookById(id).orElseThrow(
                ()-> new GeneralException(Errors.INVALID_BOOK_ID, id)
        );

        return GeneralResponseDTO.builder()
                .success(true)
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .data(mapper.bookMapperToDTOThatShowsBorrows(book))
                .build();


    }


    public Object searchBook(String keyword, int pageNo, int pageSize){
        log.info("------------------------- search book using the keyword : {}", keyword);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdTime").descending());

        Specification<Book> specification =  new BookSpecification(keyword);

        Page<Book> bookPage = bookRepository.findAll(specification,pageable);

        HashMap<String, Object> paginatedResponse = PaginatedResponse.paginatedResponse(
                bookPage.getContent().stream().map(mapper::bookMapperToDTOThatShowsBorrows).collect(Collectors.toList()),
                pageNo, bookPage.getTotalPages(), bookPage.getTotalElements(),bookPage.isFirst(),
                bookPage.isLast()
        );

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(paginatedResponse)
                .build();

    }


}
