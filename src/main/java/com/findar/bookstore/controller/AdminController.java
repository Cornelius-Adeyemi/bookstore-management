package com.findar.bookstore.controller;


import com.findar.bookstore.DTOS.request.AddBookDTO;
import com.findar.bookstore.DTOS.request.UpdateBookDTO;
import com.findar.bookstore.service.interfaces.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Please note in a robust project, this control would be modularised,
 */


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/process")
public class AdminController {




  private final AdminService adminService;


  @PostMapping("/add-book")
  @Operation(summary = "This is an endpoint to add a book to store")
  public Object addBook(@Valid @RequestBody AddBookDTO addBookDTO){
    return adminService.addBook(addBookDTO);
  }

  @PostMapping("/add-book-via-csv-file")
  @Operation(summary = "This is an endpoint to add multiple books to store via a csv file. The sample of the CSV header can be gotten from the endpoint below")
   public Object addBulkBook(@RequestParam(value = "file")MultipartFile file){

    return adminService.addBookViaFile(file);
   }

  @PostMapping("/update-book/{id}")
  @Operation(summary = "This is an endpoint to update a book details")
  public Object updateBook(@PathVariable(name = "id") Long id,@Valid @RequestBody UpdateBookDTO updateBookDTO){
    return adminService.updateBook(id, updateBookDTO);
  }

  @PatchMapping("/disable-user")
  @Operation(summary = "This is an endpoint to disable a user.you can either provide user email or username in the request param ")
  public Object disableUser(@RequestParam(name = "email") String email){
    return adminService.disableUser(email);
  }


  @GetMapping("/get-all-user")
  @Operation(summary = "This is an endpoint to get all users")
  public Object getAllUser(@RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                           @RequestParam(name= "pageSize", defaultValue = "20", required = false) int pageSize){
    return adminService.getAllUser(pageNo,pageSize);
  }

  @GetMapping("/get-a-user")
  @Operation(summary = "This is an endpoint to get a user")
  public Object getAUser(@RequestParam(name = "email") String  email){
    return adminService.getAUser(email);
  }


  @GetMapping("/get-books-borrowed-by-customer")
  @Operation(summary = "This is an endpoint to get a merchant borrowed books. you can either provide user email or username in the request param")
  public Object getBorrowedByCustomer(@RequestParam(name = "email") String  email){
    return adminService.getBooksBorrowByCustomer(email);
  }



  @GetMapping("/delete-book/{id}")
  @Operation(summary = "This is an endpoint to delete a book. This doesn't completely delete the book from the db")
  public Object delete(@PathVariable(name = "id") Long id){
    return adminService.deleteBook(id);
  }


@GetMapping("/download-csv-sample")
@Operation(summary = "This is an endpoint to get CSV export sample. This endpoint is not secured to make it easy to download the file through the browser")
  public void downloadCSVSampleUpload(HttpServletResponse response){

    adminService.downloadCSVFileSample(response);
  }


}
