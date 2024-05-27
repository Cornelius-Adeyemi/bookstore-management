package com.findar.bookstore.controller;


import com.findar.bookstore.service.interfaces.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/book/process")
public class BookController {


    private final BookService bookService;

    @GetMapping("/get-all-books")
    @Operation(summary = "This is an endpoint to get all books")
    public Object getAllBook(@RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                             @RequestParam(name= "pageSize", defaultValue = "20", required = false) int pageSize){

        return bookService.getAllBooks(pageNo,pageSize);

    }

    @GetMapping("/get-a-book/{id}")
    @Operation(summary = "This is an endpoint to get a single book")
    public Object getBook(@PathVariable(name = "id") Long id){

        return bookService.getABook(id);

    }

    @GetMapping("/search")
    @Operation(summary = "This is an endpoint to search for a book by title or author")
    public Object getBook(@RequestParam(name = "keyword") String keyword,
                          @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                          @RequestParam(name= "pageSize", defaultValue = "20", required = false) int pageSize
                          ){

        return bookService.searchBook(keyword, pageNo,pageSize);

    }



}
