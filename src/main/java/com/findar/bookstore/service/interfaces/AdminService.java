package com.findar.bookstore.service.interfaces;

import com.findar.bookstore.DTOS.request.AddBookDTO;
import com.findar.bookstore.DTOS.request.UpdateBookDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Object addBook(AddBookDTO addBookDTO);
    Object addBookViaFile(MultipartFile file);
    Object updateBook(Long id, UpdateBookDTO updateBookDTO);
    Object disableUser(String email);
    Object getAllUser(int pageNum, int pageSize);
    Object getAUser(String email);
    Object getBooksBorrowByCustomer(String email);
    Object deleteBook(Long id);
    void downloadCSVFileSample(HttpServletResponse httpServletResponse);



}
