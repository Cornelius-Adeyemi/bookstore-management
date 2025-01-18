package com.findar.bookstore.DTOS;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.StandardException;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestMulitipleFileUpload {

     private MyFile[] myFiles;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyFile{

        private String fileName;

        private Object file;

    }

}
