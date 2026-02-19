# FoodShare – Food Donation Management System

FoodShare is a role-based food donation management system built using Java, JavaFX, MySQL, and JDBC.  
The system connects food donors with volunteers and manages the complete donation lifecycle using database-driven workflows.

---

##  Problem Statement

Food wastage is a common issue in college mess facilities and events.  
FoodShare provides a structured system where donors can list surplus food and volunteers can express interest and coordinate pickup efficiently.

---

##  Tech Stack

- **Backend:** Java (OOP principles)
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **UI:** JavaFX
- **Tools:** IntelliJ IDEA, MySQL Workbench

---

##  System Architecture

The system follows a modular design:

- User Authentication Module
- Role-Based Dashboard (Donor / Volunteer)
- Donation Management Module
- Volunteer Interest Mapping
- Database Integration Layer (JDBC-based DAO logic)

Separation of concerns is maintained between:
- UI Layer
- Business Logic Layer
- Database Layer

---

##  Key Features

✔ Secure user registration and login  
✔ Role-based dashboards  
✔ Add and manage food donations  
✔ Volunteer interest tracking  
✔ Donation lifecycle status management  
✔ Database-driven workflows using SQL queries  

---

##  Database Design

Main Tables:

- `users`
- `donations`
- `volunteers`

Key relationships:
- One donor can create multiple donations
- One volunteer can express interest in multiple donations
- Donation status updates dynamically

---

##  Backend Concepts Implemented

- Object-Oriented Programming (Encapsulation, Modularity)
- JDBC connection handling
- SQL query optimization
- Exception handling
- Input validation
- Role-based access control logic

---

##  How to Run

1. Clone the repository  
2. Create MySQL database  
3. Run provided SQL schema  
4. Update DB credentials in the project  
5. Run Main.java  

---

##  Future Improvements

- Convert to Spring Boot REST API
- Implement JWT authentication
- Add Docker containerization
- Deploy on cloud (AWS/Azure)

---

##  Author

Gauri Shinde  
Computer Engineering Student  
Backend Development Enthusiast
