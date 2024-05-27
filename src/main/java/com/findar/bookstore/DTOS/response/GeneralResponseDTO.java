package com.findar.bookstore.DTOS.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GeneralResponseDTO {

    private String message;

    private String status;

    private boolean success;

    private Object data;

    private Object errors;
}
