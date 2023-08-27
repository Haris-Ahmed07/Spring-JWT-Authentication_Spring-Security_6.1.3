# Spring Boot JWT Authentication with Spring Security

Welcome to the Spring Boot JWT Authentication project, powered by Spring Security 6.1.3! This repository serves as a comprehensive guide and practical implementation for establishing secure authentication using JSON Web Tokens (JWT) in a Spring Boot application. By employing JWT-based authentication, this project demonstrates industry best practices for securing your web application's endpoints.

## Features:

Cutting-Edge Spring Boot & Spring Security: Utilize the power of Spring Boot 3.1.3 and Spring Security 6.1.3 for a robust and up-to-date security foundation.

JWT-Based Authentication: Dive deep into configuring and implementing JSON Web Token (JWT) authentication, offering a secure and stateless mechanism for user authentication.

User Authentication and Authorization: Learn how to efficiently manage user authentication and authorization using JWTs, enhancing the overall security posture of your application.

Clean and Maintainable Configuration: Experience well-organized configuration classes that adhere to separation of concerns principles, improving code readability and maintainability.

Sample User Interface: Interact with a user interface that demonstrates the seamless integration of JWT-based authentication, showcasing protected endpoints accessible only to authenticated users.

Educational Resource: This repository serves as an educational resource, guiding you through the process of understanding and implementing JWT authentication in Spring Security.

## Steps to Run the Application:

1. Clone the repository to your local machine.
2. Run the application using your preferred Integrated Development Environment (IDE) or through the command line: `./mvnw spring-boot:run`.
3. The application will start on `http://localhost:8080`.

## Steps to Obtain a JWT Token:

1. Open Postman (or your preferred API testing tool).
2. In the Headers section, add a new key-value pair:
    Key: Content-Type
    Value: application/json
3. Create a POST request and set the URL to `localhost:8080/auth/login`.
4. Select body type raw(JSON)
5. There are two Authenticated Users Configured 
    1) username = "Haris" , password = "123" , role = "ADMIN"
    2)  username = "Ahmed" , password = "123" , role = "USER"

6. In the request body, provide the following JSON:
   ```json
   {
       "email": "haris",
       "password": "123"
   }
7. Send the POST request. You will receive a JWT token in the response. Copy this token.

## Accessing Protected Endpoints:
Create a GET request in Postman.
Set the URL to the desired protected endpoint, e.g.,

    1) localhost:8080/home/getAll.
    2) localhost:8080/home/admin
    3) localhost:8080/home/user 

In the Headers section, add a new key-value pair:
Key: Authorization
Value: Bearer <your-copied-jwt-token>
For example, if your copied token is eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..., your Authorization header value should be Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9....

Send the GET request. You have now successfully accessed a protected endpoint using JWT authentication!

## Contributions:
Feel free to contribute to this project by submitting pull requests that enhance JWT authentication, introduce additional features, or address security considerations.

## Note:
This guide assumes that you have a basic understanding of Spring Boot, Spring Security, and API testing with tools like Postman. Refer to the official Spring Boot and Spring Security documentation for more comprehensive explanations and advanced configurations. Always prioritize security and best practices when implementing authentication in your applications.