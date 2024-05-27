package com.findar.bookstore.model.entity;

import com.findar.bookstore.model.audith.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashSet;

import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Borrowed extends AuditEntity {



    @Column(nullable = false, unique = true)
    private String borrowId;

    private Boolean returned;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users_id", nullable = false,referencedColumnName = "id")
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "borrow_books",
            joinColumns = @JoinColumn(name = "borrow_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    private Set<Book> books = new HashSet<>();
}
