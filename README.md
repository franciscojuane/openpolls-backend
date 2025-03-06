# OpenPolls Backend
## Description
The OpenPolls Backend is a RESTful API built with Spring Boot, Spring Security, and Spring Data JPA, powering the OpenPolls polling system. It handles all the core logic, data management, and security for the platform.

## Key Features
* Poll Management: Create, update, and delete polls via API endpoints, with support for complex poll configurations.

* Response Validation: Prevent duplicate submissions by limiting responses per IP or email address.

* Data Analytics: Retrieve raw poll data or aggregated results for visualization in the frontend.

* Authentication and Authorization: Secure API endpoints using Spring Security, with role-based access control (e.g., admins, editors, and viewers).

* Database Integration: Utilizes H2, an in-memory database, for efficient data storage and retrieval during development. Lombok is also used to reduce boilerplate code and improve readability.

## Technologies Used
* Spring Boot: Framework for building stand-alone, production-grade Spring-based applications.

* Spring Security: Provides authentication and authorization features.

* Spring Data JPA: Simplifies database access and operations.

* H2 Database: In-memory database used for development purposes.

* Lombok: Library to reduce boilerplate code in Java.

## Project Setup
### Running the Application
Once the application is running, the backend server will start on port 5000. You can access the API at:

http://localhost:5000

## Purpose
This project demonstrates my expertise in backend development, including API design, security, and database management. It serves as the backbone for the OpenPolls Frontend, ensuring reliable and secure data handling for the entire polling system.
