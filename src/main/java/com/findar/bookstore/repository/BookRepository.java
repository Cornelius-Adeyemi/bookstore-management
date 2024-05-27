package com.findar.bookstore.repository;


import com.findar.bookstore.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findBookById(Long id);
    Optional<Book> findBookByIdAndDeletedFalse(Long id);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByDeletedFalse(Pageable pageable);

}
