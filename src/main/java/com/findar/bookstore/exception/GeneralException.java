package com.findar.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GeneralException extends RuntimeException{

    private HttpStatus httpStatus;

    private String status;

    private Object data;


    public GeneralException(String message, HttpStatus httpStatus, Object data){
        super(message);
        this.httpStatus = httpStatus;
        this.status = httpStatus.toString();
        this.data = data;

    }

    public GeneralException(Errors errors, Object data){
        super(errors.getErrorMessage());
        this.httpStatus = errors.getHttpStatus();
        this.status = errors.getHttpStatus().toString();
        this.data = data;

    }

}
