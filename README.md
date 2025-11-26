# Microservices API for Authentication & User Management
A simple backend microservices-style application built with Java Spring Boot, implementing manual JWT authentication and secure password hashing using BCrypt.
---
## Two Main Componenets: 
- Authentication Service -> Handles User Log In and JWT Token Generation
- User Management Service -> Handles user registration, profile retrieval, and profile updates
The Sytem uses **JWT-based Authenticaion**, passowrd are hashed using BCrypt, and protected routes require a valid token
---

##Features
###Authentication Service
- User Login
- JWT Token Generation
- Token Validation for Secured Endpoints
###User Management Service
- User Registration
- Get the authenticated user’s information
- Update Profile
- Hashing Password with BCrypt
  
---
##Prerquisties 
- Java 17
- Maven 3.X
- MYSQL
---

##Database Configuration
Create a Database :
'''sql
  CREATE DATABASE microservice_db;

Update your application.properties :
'''properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/microservice_db
  spring.datasource.username=postgres
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
'''
## Running the Application

### Command Line
```sh
mvn spring-boot:run
```

### Visual Studio Code
```sh
./mvnw spring-boot:run
```
---
##API Endpoints
###1. Register User
**POST**/users/register
Request Body JSON : 
'''
{
  "name": "Jeisen",
  "email": "jeisen@gmail.com",
  "password": "123456",
  "age": 22
}
'''
Example Response :
'''
{
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "name": "Jeisen",
    "email": "jeisengmail.com"
  }
}
'''

###2. Login User
**POST**/auth/login
Request Body JSON :
'''
{
  "email": "jason@gmail.com",
  "password": "123456"
}
'''
Example Response : 
'''
{
  "token": "<jwt_token>",
  "user": {
    "id": 1,
    "name": "Jeisen",
    "email": "jeisen@gmail.com"
  }
}
'''

###3. Get Authenticated User
**GET**/users/me
**Authorization** : Type = "Bearer Token" Token = "<jwt_token>"
Example Response : 
'''
{
  "id": 1,
  "name": "Jason",
  "email": "jason@gmail.com",
  "age": 22
}
'''

###4. Update Profile 
**PUT**/users/{id}
**Authorization** : Type = "Bearer Token" Token = "<jwt_token>"
Request Body JSON : 
'''
{
  "name": "Jason Updated",
  "email": "jason_updated@gmail.com",
  "age": 23
}
'''
Example Response : 
'''
{
  "message": "Profile updated",
  "user": {
    "id": 1,
    "name": "Jason Updated",
    "email": "jason_updated@gmail.com",
    "age": 23
  }
}
'''
##Testing the API
Use Postman, Thunder Client, or curl:
1. Register or Login -> obtain JWT token
2. Use the token for secured endpoints:
'''
    Authorization: Bearer <your_token_here>
'''
Endponts requiring a token :
- /users/me
- /users/{id} (update profile)

##Project Structure
'''
/src
 ├─ controller
 ├─ entity
 ├─ repository
 ├─ service
 ├─ security
 └─ MainApplication.java
'''

##Notes 
- JWT is generated manually using a utility class.
- Passwords are stored securely using BCrypt
- All protected routes validate the token before processing the request




  
