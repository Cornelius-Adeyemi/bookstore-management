package com.findar.bookstore.service.implementation;

import com.findar.bookstore.DTOS.request.AddBookDTO;
import com.findar.bookstore.DTOS.request.UpdateBookDTO;
import com.findar.bookstore.DTOS.response.BookUserDTO;
import com.findar.bookstore.DTOS.response.BorrowedUserDTO;
import com.findar.bookstore.DTOS.response.GeneralResponseDTO;
import com.findar.bookstore.DTOS.response.UserResponseDTO;
import com.findar.bookstore.enums.Constant;
import com.findar.bookstore.enums.Errors;
import com.findar.bookstore.enums.Role;
import com.findar.bookstore.exception.GeneralException;
import com.findar.bookstore.model.entity.Book;
import com.findar.bookstore.model.entity.Borrowed;
import com.findar.bookstore.model.entity.Users;
import com.findar.bookstore.repository.BookRepository;
import com.findar.bookstore.repository.BorrowRepository;
import com.findar.bookstore.repository.UserRepository;
import com.findar.bookstore.service.interfaces.AdminService;
import com.findar.bookstore.util.PaginatedResponse;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl  implements AdminService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final BorrowRepository borrowRepository;

    @Value("${csv.sample:}")
    private String filePath;


    @Override
    public Object addBook(AddBookDTO addBookDTO) {

        log.info("------------------------- add book");

      if(addBookDTO.getAvailableQuantity() <= 0) throw new GeneralException(Errors.INVALID_BOOK_QUANTITY, addBookDTO.getTitle());

        Book book  = bookDTOMapper(addBookDTO);

        Book savedBook = bookRepository.save(book);


        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(savedBook)
                .build();
    }

    @Override
    public Object addBookViaFile(MultipartFile file) {
        log.info("------------------------- add via csv file");
        try(CSVReader reader = createCsvReader(file)){
            String[] headers = readCsvHeaders(reader);// get Csv header
            validateCsvHeaders(headers); // check if header is valid
           List<AddBookDTO> addBookDTOList = readCsvData(reader, headers); // read csv body
           return uploadBooks(addBookDTOList);

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

    }

    private Object uploadBooks(List<AddBookDTO> requestDtoList) {

        for (AddBookDTO requestDto : requestDtoList) {
            addBook(requestDto);
        }
           return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(null)
                .build();
    }

    private void validateCsvHeaders(String[] headers) {
        List<String> requiredHeaders = Arrays.asList("TITLE" ,"AUTHOR","PAGES","CHAPTERS","AVAILABLE QUANTITY");
        for (String header : headers) {
            if (!requiredHeaders.contains(header.trim().toUpperCase())) {
                throw new GeneralException(Errors.INVALID_CSV_HEADER, null);

            }
        }
    }

    private List<AddBookDTO> readCsvData(CSVReader reader, String[] headers) throws IOException, CsvValidationException {
        List<AddBookDTO> requestDtoList = new ArrayList<>();
        int title = findHeaderIndex(headers, "TITLE");
        int author = findHeaderIndex(headers, "AUTHOR");
        int pages = findHeaderIndex(headers, "PAGES");
        int chapters = findHeaderIndex(headers, "CHAPTERS");
        int availableQuantity = findHeaderIndex(headers, "AVAILABLE QUANTITY");



        String[] rowData;
        while ((rowData = reader.readNext()) != null) {
            if (rowData.length >= headers.length) {
                AddBookDTO requestDto = new AddBookDTO();
                requestDto.setChapters(rowData[chapters]);
                requestDto.setTitle(rowData[title]);
                requestDto.setAuthor(rowData[author]);
                requestDto.setPages(rowData[pages]);
                requestDto.setAvailableQuantity( Integer.parseInt(rowData[availableQuantity]));

                requestDtoList.add(requestDto);
            } else {
                throw new GeneralException(Errors.INVALID_CSV_HEADER, null);
            }
        }
        return requestDtoList;
    }

    private int findHeaderIndex(String[] headers, String headerName) {
        for (int i = 0; i < headers.length; i++) {
            if (headerName.trim().equalsIgnoreCase(headers[i].trim())) {
                return i;
            }
        }
        throw new GeneralException(Errors.INVALID_CSV_HEADER, null);

    }




    private CSVReader createCsvReader(MultipartFile file) throws IOException {
        return new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())));
    }

    private String[] readCsvHeaders(CSVReader reader) throws IOException, CsvValidationException {
        return reader.readNext();
    }

    @Override
    public Object updateBook(Long id, UpdateBookDTO updateBookDTO) {

        log.info("------------------------- update book");
        Book  book = bookRepository.findBookById(id).orElseThrow(
                ()-> new GeneralException(Errors.INVALID_BOOK_ID, id)
        );


        updateBookDTOMapper(book, updateBookDTO);

        Book updatedBook =  bookRepository.save(book);

        return  GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(updatedBook)
                .build();


    }

    /**
     * @Input : it can be either email or username
     */
    @Override
    public Object disableUser(String email) {
        log.info("------------------------- disable user");
        Users users = getUser(email);

        users.setActive(false);

        userRepository.save(users);

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(null)
                .build();
    }

    @Override
    public Object getAllUser( int pageNum, int pageSize ) {
        log.info("------------------------- get all user");

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdTime").descending());

        Page<Users> usersPage = userRepository.findAll(pageable);

        HashMap<String, Object> paginatedResponse = PaginatedResponse.paginatedResponse(
          usersPage.getContent().stream().map(this::userMapperToDto).collect(Collectors.toList()),
          pageNum, usersPage.getTotalPages(), usersPage.getTotalElements(),usersPage.isFirst(),
          usersPage.isLast()
        );

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(paginatedResponse)
                .build();
    }

    /**
     *
     * @param email: it can be either email or username
     * @return
     */

    public Object getAUser(String email){
        log.info("------------------------- get a user {}", email);
        Users users = getUser(email);

        UserResponseDTO userResponseDTO = this.userMapperToDto(users);

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(userResponseDTO)
                .build();
    }
    /**
     *
     * @param email: it can be either email or username
     * @return
     */

    public Object getBooksBorrowByCustomer(String email){

        log.info("------------------------- get book borrowed by {}",email);
        Users users = getUser(email);

        List<Borrowed> borrowedList = borrowRepository.findByReturnedFalseAndUser_email(users.getEmail());

         List<BorrowedUserDTO> borrowedUserDTOList = borrowedList.stream().map(this::borrowMapperToDTO).toList();


        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(borrowedUserDTOList)
                .build();

    }

    public Object deleteBook(Long id){
        log.info("------------------------- delete book {}", id);
        Book  book = bookRepository.findBookById(id).orElseThrow(
                ()-> new GeneralException(Errors.INVALID_BOOK_ID, id)
        );

        book.setDeleted(true);

       Book updateBook = bookRepository.save(book);

        return GeneralResponseDTO.builder()
                .message(Constant.REQUEST_SUCCESSFULLY_TREATED.getMessage())
                .success(true)
                .data(bookMapperToDTO(updateBook))
                .build();
    }



    @Override
    public void downloadCSVFileSample(HttpServletResponse response) {

        log.info("------------------------- download csv upload sample ");
        try {
            String fileName = "bulkBookUploadTemplate.csv";
            response.setContentType("text/csv");

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey, headerValue);

            OutputStream outputStream = response.getOutputStream();


             try(InputStream inputStream = new FileInputStream(filePath)){
                 byte[] buffer = new byte[1024];
                 int length;
                 while ((length = inputStream.read(buffer)) != -1) {
                     outputStream.write(buffer, 0, length);
                 }
             }catch(Exception e){
                 throw  new GeneralException(Errors.ERROR_DOWNLOADING_CSV_SAMPLE, null);

             }

            response.flushBuffer();
            outputStream.close();
        } catch (IOException ex) {
            throw  new GeneralException(Errors.ERROR_DOWNLOADING_CSV_SAMPLE, null);
        }



    }




  private Book  bookDTOMapper(AddBookDTO addBookDTO){
        Book book = new Book();

        book.setTitle(addBookDTO.getTitle());
        book.setAuthor(addBookDTO.getAuthor());
        book.setPages(addBookDTO.getPages());
        book.setChapters(addBookDTO.getChapters());
        book.setAvailable(true);
        book.setAvailableQuantity(addBookDTO.getAvailableQuantity());
        return book;
  }

  private void updateBookDTOMapper(Book book, UpdateBookDTO updateBookDTO){

        book.setTitle(updateBookDTO.getTitle() != null && !updateBookDTO.getTitle().isEmpty()?
                updateBookDTO.getTitle() : book.getTitle());

        book.setAuthor(updateBookDTO.getAuthor() != null && !updateBookDTO.getAuthor().isEmpty()?
                updateBookDTO.getAuthor() : book.getAuthor());

        book.setPages(updateBookDTO.getPages() != null && !updateBookDTO.getPages().isEmpty()?
                updateBookDTO.getPages() : book.getPages());

        book.setChapters(updateBookDTO.getChapters() != null && !updateBookDTO.getChapters().isEmpty()?
                updateBookDTO.getChapters() : book.getChapters());

        Integer quantity = book.getAvailableQuantity() + (updateBookDTO.getAvailableQuantity() != null ? updateBookDTO.getAvailableQuantity() : 0);

        book.setAvailableQuantity(quantity);

        book.setAvailable(quantity >= 0 ? true : false);

    }

    private Users getUser(String email){
        Users users = null;


        Optional<Users> usersbyEmail = userRepository.findByEmail(email);

        if(!usersbyEmail.isPresent()){
            Optional<Users> usersByUsername = userRepository.findByUserName(email);

            if(!usersByUsername.isPresent()){
                throw new GeneralException(Errors.INVALID_USERNAME_OR_EMAIL, email);
            }

            users = usersByUsername.get();

        }

        users = usersbyEmail.get();

        return  users;


    }


    private UserResponseDTO userMapperToDto(Users users){

       UserResponseDTO userResponseDTO = UserResponseDTO.builder()
              .email(users.getEmail())
              .userName(users.getUsername())
              .firstName(users.getFirstName())
              .lastName(users.getLastName())
              .active(users.getActive())
              .role(Role.valueOf( users.getRole()) )
              .borrows(users.getBorrows().stream().map(this::borrowMapperToDTO).collect(Collectors.toList()))
              .build();

       return userResponseDTO;

    }


    private BorrowedUserDTO borrowMapperToDTO(Borrowed borrowed){

        BorrowedUserDTO borrowedUserDTO  = BorrowedUserDTO.builder()
                .borrowId(borrowed.getBorrowId())
                .returned(borrowed.getReturned())
                .books(borrowed.getBooks().stream().map(this::bookMapperToDTO).collect(Collectors.toList()))
                .build();


        return borrowedUserDTO;
    }

    private BookUserDTO bookMapperToDTO(Book book){

        BookUserDTO  bookUserDTO = BookUserDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .pages(book.getPages())
                .chapters(book.getChapters())
                .deleted(book.getDeleted())
                .build();

        return bookUserDTO;

    }

}
