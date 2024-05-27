package com.findar.bookstore.DTOS.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddBookDTO {


    @NotEmpty(message = "Title required")
    private String title;
    @NotEmpty(message = "Author required")
    private String author;
    @NotEmpty(message = "No of pages is required")
    private String pages;
    @NotEmpty(message = "No of chapters required")
    private String chapters;

    private Boolean available;

    @NotNull(message = "Quantity is required")
    private Integer availableQuantity;


}
