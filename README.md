# 🚆 Online Reservation System

A desktop-based Online Reservation System developed using **Java, Java Swing, JDBC, Maven, and MySQL**.

This application allows users to register, log in, book train tickets, view their reservations, and cancel existing reservations through a graphical user interface.

## 📌 Project Overview

The Online Reservation System is designed to simplify train ticket reservation operations through a user-friendly desktop application.

It uses Java Swing for the graphical user interface and JDBC to communicate with a MySQL database.

The project follows a layered architecture using DAO, Service, Model, GUI, and Database packages to organize the application.

## ✨ Features

- User Registration
- User Login and Authentication
- Dashboard Navigation
- Train Selection
- Train Ticket Booking
- Automatic PNR Generation
- Booking Confirmation
- View All Reservations
- Cancel Train Reservations
- Input Validation
- MySQL Database Integration

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| Java Swing | Graphical User Interface |
| JDBC | Database connectivity |
| MySQL | Database management |
| Maven | Project and dependency management |
| MySQL Connector/J | JDBC driver |
| VSCODE | Development environment |
| Git and GitHub | Version control and project hosting |

## 📂 Project Structure

```text
Online-Reservation-System/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── reservation/
│                   ├── database/
│                   ├── dao/
│                   ├── gui/
│                   ├── model/
│                   ├── service/
│                   └── Main.java
│
├── pom.xml
├── .gitignore
└── README.md
```

## ⚙️ Prerequisites

Make sure the following software is installed:

- Java Development Kit (JDK 25)
- Apache Maven
- MySQL Server
- MySQL Workbench (optional)
- IntelliJ IDEA or another IDE supports Java

## 🗄️ Database Configuration

The application uses a MySQL database named:

```sql
online_reservation
```

Create the database using:

```sql
CREATE DATABASE online_reservation;
```

Create the required database tables using the SQL schema for this project.

Configure your database credentials using environment variables.

### Windows PowerShell

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/online_reservation"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
```

Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password.

## 🚀 How to Run the Project

### Method 1: Using VSCODE

1. Clone or download this repository.
2. Open the project in VSCODE.
3. Allow Maven to download the required dependencies.
4. Start your MySQL server.
5. Configure the database environment variables.
6. Run `Main.java`.
7. The application login screen will appear.

### Method 2: Using Maven

Open a terminal in the project directory.

Build the project:

```bash
mvn clean package
```

Run the generated executable JAR:

```bash
java -jar target/online-reservation-system-1.0-SNAPSHOT.jar
```

Make sure the database is running and the required environment variables are configured in the terminal session.

## 🏗️ Application Architecture

The project is organized into the following packages:

| Package | Responsibility |
|---|---|
| database | Database connection management |
| dao | Database operations |
| model | Application data models |
| service | Business logic |
| gui | Graphical user interface |

The application uses a layered architecture to separate user interface operations, business logic, and database operations.

## 🧪 Testing

The following application operations have been tested:

- User registration
- User login
- Ticket booking
- Booking confirmation
- Viewing reservations
- Ticket cancellation
- Database connectivity
- Maven packaging
- Executable JAR execution

## 🔒 Security Note

Database credentials are read through environment variables instead of being directly stored in the source code.

Users should configure their own local database credentials before running the application.

## 👨‍💻 Developer

Developed as a Java desktop application project using Java Swing, JDBC, Maven, and MySQL.

## 📄 License

This project is available for educational and learning purposes.
