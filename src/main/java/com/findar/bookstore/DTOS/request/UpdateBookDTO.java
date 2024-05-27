package com.findar.bookstore.DTOS.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateBookDTO {



    private String title;

    private String author;

    private String pages;

    private String chapters;

    private Boolean available;


    private Integer availableQuantity;

}
