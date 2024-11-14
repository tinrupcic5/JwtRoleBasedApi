# Role-Based Access Control (RBAC) System with JWT in Spring Boot :coffee:

## Overview

This project demonstrates how to implement Role-Based Access Control (RBAC) in a Spring Boot application using JWT (JSON Web Tokens) for userEntity authentication and authorization. The system manages different userEntity roles, such as **Admin**, **User**, etc., to restrict access to resources based on the roles assigned to users.

Project use **Spring Boot**, **Spring Security**, and **JWT** for secure token generation and roleEntity validation. This setup ensures that only authorized users with the appropriate roles can access specific endpoints.


- Building a Role-Based Access Control System with JWT in Spring Boot
  ([dev.to](https://dev.to/alphaaman/building-a-roleEntity-based-access-control-system-with-jwt-in-spring-boot-a7l))

- Spring security ([Configuration Migrations](https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html))


## Features

- **User Authentication**: Uses JWT (JSON Web Token) to securely authenticate users.
- **Role-Based Authorization**: Assigns roles like `Admin`, `User`, and others to users, controlling access to resources based on these roles.
- **Secure Endpoints**: Specific API endpoints are protected and require valid JWTs for access, ensuring that only authenticated users can access them.
- **Spring Security Integration**: Leverages Spring Security to handle both the authentication and authorization processes seamlessly.


## Technologies

- **Spring Boot 3.3.5**
- **Spring Security** for authentication and authorization
- **JWT** for creating and validating tokens
- **Maven** for dependency management
- **Spring Data JPA** (optional, for database integration)

## Spotless Code Formatting :doughnut:

Spotless is configured to automatically format code according to defined style rules. This includes organizing imports, enforcing indentation, and following naming conventions, among other rules. By running Spotless, you can automatically format your code, ensuring it follows project's standards.

This project uses the Spotless Maven Plugin (version 2.43.0) with Google Java Format.
- Maven Spotless Plugin for Java ([Baeldung](https://www.baeldung.com/java-maven-spotless-plugin))
### How to Run Spotless
- **Check Formatting**: before committing to ensure your code meets the project's formatting standards.
  ```bash
  mvn spotless:check
  ```

- **Apply Formatting**: to fix issues automatically
  ```bash
  mvn spotless:apply
  ```
