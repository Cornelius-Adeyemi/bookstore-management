package com.findar.bookstore.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Errors {


    BOOK_NOT_AVAILABLE("Book is currently unavailable", HttpStatus.BAD_REQUEST),

    USER_EMAIL_ALREADY_EXIST("Email already exist", HttpStatus.BAD_REQUEST),

    USERNAME_ALREADY_EXIST("Username already taken", HttpStatus.BAD_REQUEST),

    ERROR_SIGNING_UP("error signing up", HttpStatus.BAD_REQUEST),

    INVALID_PASSWORD("Invalid password", HttpStatus.BAD_REQUEST),

    INVALID_BOOK_QUANTITY("Book quantity should be grater than equal to 1", HttpStatus.BAD_REQUEST),

    INVALID_BOOK_ID("No book with that id", HttpStatus.BAD_REQUEST),

    INVALID_USERNAME_OR_EMAIL("Invalid email or username", HttpStatus.BAD_REQUEST),

    ERROR_DOWNLOADING_CSV_SAMPLE("error downloading  csv smaple", HttpStatus.INTERNAL_SERVER_ERROR),

    UNRETURNED_BOOKS("You can't borrow more books until you've returned the previous one", HttpStatus.BAD_REQUEST),

    INVALID_BORROWID("The borrow Id you provided is invalid", HttpStatus.BAD_REQUEST),

    UNAUTHORISED_ACCESS_TO_BORROWED_BOOK("This book was borrowed by someone else", HttpStatus.UNAUTHORIZED),

    INVALID_JWT_TOKEN("please provide a valid JwT token", HttpStatus.UNAUTHORIZED),

    INVALID_CSV_HEADER("Invalid csv header provided", HttpStatus.BAD_REQUEST),

    INVALID_ENDPOINT("Endpoint not found", HttpStatus.BAD_REQUEST);






    private  final HttpStatus httpStatus;
    private final String errorMessage;

    Errors(String message, HttpStatus httpStatus){
        this.httpStatus = httpStatus;
        this.errorMessage = message;
    }

}
