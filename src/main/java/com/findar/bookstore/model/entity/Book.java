package com.findar.bookstore.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findar.bookstore.enums.Errors;
import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.audith.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "books")
public class Book  extends AuditEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String pages;

    @Column(nullable = false)
    private String chapters;

    private Boolean available;

    private Integer availableQuantity;

    private Boolean deleted = false;

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties(value = {"user", "books"})
    private Set<Borrowed> borrows = new HashSet<>();



    public Set<Borrowed> getBorrows(){

        if(availableQuantity >= 1 && available){
            int updatedNumber =this.availableQuantity -1;
            this.availableQuantity = updatedNumber;
            this.available = updatedNumber >= 1;

            return borrows;

        }
        throw new GeneralException(Errors.BOOK_NOT_AVAILABLE, this.title );
    }


    public Set<Borrowed> getBorrowsAdminUse(){



            return borrows;

    }


}
