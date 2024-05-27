package com.findar.bookstore.service.interfaces;

public interface BookService {

    Object getAllBooks(int pageNum, int pageSize);

    Object getABook(Long id);

    Object searchBook(String keyword, int pageNo, int pageSize);
}
