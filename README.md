# Book store

This is a Spring Boot application that allows admin to manage book and customers, while customers to borrow and return books. 
It features unit tests, Spring Security, and logging.


**Note - to run this application you can either run the docker compose file using "docker-compose up" command or run normally **

## Features
### Admin
- Admin add books 
- Admin update book
- Admin disable user

### Customer

- customer can borrow book - a user can only borrow one or multiple books at a time
- customer can return book
- user can login either with email or username
### Application 

- Unit tests for service layers and repository layers using Mockito
- Spring Security for authentication and authorization


## Technologies Used

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security
- Mockito
- JUnit 5
- MySQL 
- 
- ## Getting Started

1. Clone the repository:

   ```bash
   

2. Navigate to the project directory:
bash
cd bookstore

3. Build the project:
bash
./mvnw clean install

4. Run the application:
bash
./mvnw spring-boot:run

5. Access the application at http://localhost:8080.

- ## Testing

-To run the unit tests:
bash
./mvnw test

-The tests cover the service layers and repository layers using Mockito.

## Api Docummentaion
The api docummetation was done using SpringFox library for swagger configuration, which reveals every endpoint and their respective request as well as response.

- ## Security
The application uses Spring Security for authentication and authorization. The default username is admin and the default password is password.

- ## Logging
Logging is implemented using Spring Slfj4. The logs are written to the console and can be configured in the application.properties file.

- ## Configuration
The application can be configured using the application.properties file. You can set the database connection details, caching configurations, and other settings.

- ## Contributing
If you find any issues or have suggestions for improvements, feel free to create a new issue or submit a pull request.
