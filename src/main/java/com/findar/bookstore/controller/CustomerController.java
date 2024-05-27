package com.findar.bookstore.controller;


import com.findar.bookstore.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer/process")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("borrow")
    @Operation(summary = "This is an endpoint to borrow books. " +
            "A user can only have one unreturned borrow at a time and a single borrow can contain multiple books." +
            "for anytime a book is borrowed, the quantity reduces")
    public Object borrowBook(@RequestBody Set<Long> listOfBookId){

       return customerService.borrowBook(listOfBookId);
    }

    @PatchMapping("return/{borrowId}")
    @Operation(summary = "This is an endpoint to returned borrowed books. it increases the quantity of returned books ")
    public Object returnBook(@PathVariable(name = "borrowId") String borrowId){

        return customerService.returnBooks(borrowId);
    }

}
