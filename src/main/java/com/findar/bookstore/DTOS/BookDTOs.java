package com.findar.bookstore.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.findar.bookstore.model.entity.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class BookDTOs {



    @JsonIgnoreProperties(ignoreUnknown = true)
    public record UpdateBookDTO(

             @NotEmpty(message = "Title required")
             String title,

             @NotEmpty(message = "Author required")
             String author,
             @NotEmpty(message = "pages required")
            String pages,
             @NotEmpty(message = "chapters required")
             String chapters,
             @NotNull(message = "availability required")
             Boolean available,

             @NotNull(message = "Availability quantity required")
             Integer availableQuantity
    ){ }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AddBookDTO(
            @NotEmpty(message = "Title required")
             String title,
            @NotEmpty(message = "Author required")
            String author,
            @NotEmpty(message = "No of pages is required")
            String pages,
            @NotEmpty(message = "No of chapters required")
            String chapters,
            @NotNull(message = "Quantity is required")
            Boolean available,

            @NotNull(message = "Quantity is required")
            Integer availableQuantity
    ){

    }



    public static Book bookDTOMapper(AddBookDTO addBookDTO){
        Book book = new Book();

        book.setTitle(addBookDTO.title());
        book.setAuthor(addBookDTO.author());
        book.setPages(addBookDTO.pages());
        book.setChapters(addBookDTO.chapters());
        book.setAvailable(true);
        book.setAvailableQuantity(addBookDTO.availableQuantity());
        return book;
    }
}
