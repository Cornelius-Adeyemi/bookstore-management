package com.findar.bookstore.repository;

import com.findar.bookstore.model.entity.Borrowed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrowed, Long> {


    Optional<Borrowed> findByBorrowId(String borrowId);

    List<Borrowed> findByReturnedTrueAndUser_email(String email);

    List<Borrowed> findByReturnedFalseAndUser_email(String email);
}
