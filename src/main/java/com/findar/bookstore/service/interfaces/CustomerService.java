package com.findar.bookstore.service.interfaces;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    Object borrowBook(Set<Long> listOfBookId);

    Object returnBooks(String borrowedId);
}
