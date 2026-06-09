# banking-api-Springboot

A secure and scalable Banking Backend Application built using Spring Boot, Spring Security, JWT Authentication, Spring Data JPA, Hibernate, and PostgreSQL.

Features

Authentication & Authorization

- User Registration
- User Login
- JWT Token Generation & Validation
- Role-Based Access Control (USER / ADMIN)
- Secure Protected APIs

Banking Operations

- Create Account
- Get Account Details
- Get All Accounts
- Deposit Money
- Withdraw Money
- Transfer Money Between Accounts
- Delete Account

Supported Account Types

- Savings Account
- Current Account
- Fixed Deposit (FD) Account

Business Rules

- Savings Account Withdrawal Limits
- Minimum Balance Validation
- Current Account Overdraft Facility
- Interest Calculation
- Fixed Deposit Maturity Processing
- Secure Transaction Validation

Security Features

- Spring Security Integration
- JWT Authentication Filter
- BCrypt Password Encryption
- Stateless Authentication
- Protected REST Endpoints

- Exception Handling
  
Custom Exception Handling
Global Exception Handler
Meaningful Error Responses
Proper HTTP Status Codes

Database

PostgreSQL
Spring Data JPA
Hibernate ORM
Automatic Schema Updates
Technology Stack
Java 17
Spring Boot 3
Spring Security
JWT (JSON Web Token)
Spring Data JPA
Hibernate
PostgreSQL
Maven
Lombok
Postman
Git & GitHub

API Endpoints

Authentication APIs
Method
Endpoint
POST
/auth/register

POST
/auth/login
Account APIs
Method
Endpoint
POST
/api/accounts
GET
/api/accounts
GET
/api/accounts/{accountNumber}
PUT
/api/accounts/{accountNumber}/deposit
PUT
/api/accounts/{accountNumber}/withdrawl
PUT
/api/accounts/transfer
PUT
/api/accounts/{accountNumber}/interest
PUT
/api/accounts/{accountNumber}/mature
DELETE
/api/accounts/{accountNumber}

Project Highlights

JWT-Based Authentication & Authorization

Role-Based Access Control

Secure Banking Transactions

Transfer, Deposit & Withdrawal Operations

Fixed Deposit Management System

Interest Calculation Engine

Custom Exception Handling

Layered Architecture (Controller → Service → Repository)

DTO Pattern Implementation

PostgreSQL Database Integration

Spring Security with JWT

RESTful API Design

Sample Transfer Request

{
  "fromAccountNumber": "72758792922",
  "toAccountNumber": "90862200914",
  "amount": 1000
}

Getting Started

Clone Repository

git clone https://github.com/codeByGauravv/secure-banking-backend-api.git

Configure PostgreSQL

spring.datasource.url=jdbc:postgresql://localhost:5432/banking_system
spring.datasource.username=postgres
spring.datasource.password=32872633

Run Application

mvn spring-boot:run

Application runs on:

http://localhost:8080

Author

Gourav Gadhe

Java Backend Developer

LinkedIn: https://www.linkedin.com/in/gourav-gadhe-4329832b5
