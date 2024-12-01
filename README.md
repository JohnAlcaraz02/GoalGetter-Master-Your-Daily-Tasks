https://github.com/user-attachments/assets/ce0b3390-3b86-4e4e-adde-e23afe097144
# 🌟 GoalGetter: Master Your Daily Tasks 🗓️

> **"Organize. Prioritize. Achieve."**

## Table of Contents
1. [Project Overview](#-project-overview)
2. [Application of OOP Principles](#application-of-oop-principles)
3. [Alignment with Sustainable Development Goals (SDGs)](#-alignment-with-sustainable-development-goals)
4. [Technology Used](#-technology-used)
5. [Project Structure](#-project-structure)
6. [Database Schema](#-database-schema)
7. [Contributor](#-contributor)
8. [Course Information](#-course-information)


## 📖 Project Overview
### Introduction
**GoalGetter** is a *Java-based console application* designed to help users **manage and track their daily tasks** efficiently. Whether it's work, personal goals, or study-related tasks, **GoalGetter** allows users to set, prioritize, and track their progress. Built with Object-Oriented Programming (OOP) principles, this application ensures a clean, efficient, and user-friendly experience. The system supports user account creation, task categorization, deadline setting, and completion tracking, making it an ideal tool for productivity enthusiasts.

---

## 🎯 Key Features

### 👤 **User Management**
- **User Registration**:  
  Secure registration to set up personal accounts and keep track of daily tasks.
- **Profile Management**:  
  Users can edit their profiles (name, email) and view their task progress.

### 📅 **Task Management**
- **Create Tasks**:  
  Users can create tasks with descriptions, deadlines, and priorities.
- **Categorize Tasks**:  
  Assign categories (e.g., Work, Personal, Study) to organize tasks effectively.
- **Track Progress**:  
  Mark tasks as completed or view pending tasks.

### 🔔 **Reminders and Notifications**
- **Deadline Reminders**:  
  Set reminders for tasks to ensure important deadlines are met.
- **Notifications**:  
  Receive notifications when a task is due or when deadlines are approaching.

### 🔑 **Admin Management**
- **Manage User Accounts**:  
  Admins can view and manage all user accounts.
- **View Task Statistics**:  
  Admins can generate reports on completed and pending tasks across users.

## Application of OOP Principles
In GoalGetter, the core principles of Object-Oriented Programming (OOP) are employed to ensure a structured, scalable, and maintainable application. Below is an explanation of how each OOP principle is applied in the project:

### **Encapsulation**
Encapsulation is used in GoalGetter to bundle the data and methods that operate on the data within a single unit or class. For example, the Task class encapsulates task-related information such as taskName, description, and dueDate. Access to these attributes is controlled through public getter and setter methods, which ensure that only valid data is assigned and retrieved, protecting the integrity of the data. Similarly, the User class encapsulates user details like username, email, and password_hash, restricting direct access and enforcing controlled modifications through getter and setter methods.

### **Inheritance**
In GoalGetter, inheritance is applied to create a hierarchy of classes where a child class inherits attributes and behaviors from a parent class. For instance, the Admin class extends the User class, which allows admins to reuse user-related properties like username, email, and password_hash while adding specialized functionalities such as managing user accounts and viewing all tasks. This reduces code duplication and enhances code reuse, making it easier to extend the system with new roles or features.

### **Polymorphism**
Polymorphism is used in GoalGetter to allow objects of different classes to be treated as objects of a common superclass. This is particularly useful when handling different types of tasks, such as regular tasks and priority tasks. Methods like markAsCompleted() in the TaskService class can operate polymorphically, accepting any Task object or its subclass (like PriorityTask), and performing the required task-related actions without needing to know the exact type of the object being processed. This promotes flexibility in handling various task types without redundant code.

### **Abstraction**
Abstraction is implemented in GoalGetter to hide the complex details of implementation and present a simplified interface to the user. For instance, the TaskService interface defines high-level methods such as createTask(), updateTask(), and deleteTask(), while the actual implementation of these methods is handled by subclasses like DatabaseTaskService. This allows users to interact with the service without needing to understand the underlying details of database interactions.

## 🌍 Alignment with Sustainable Development Goals
The GoalGetter application aligns with several United Nations Sustainable Development Goals (SDGs):

### SDG 8: Decent Work and Economic Growth
GoalGetter promotes productivity and efficient task management, fostering better time management and work-life balance. By helping individuals and teams organize and prioritize their tasks, the project supports increased efficiency, which contributes to sustainable economic growth and decent work.
### SDG 3: Good Health and Well-being
By providing tools for managing daily tasks effectively, GoalGetter can help reduce stress and improve mental health. A well-organized schedule allows users to manage workloads more healthily, ensuring time for rest, exercise, and personal growth.

### 💻 Technology Used

- **Programming Language**: Java 17
- **Database**: MySQL 8.0
- **Database Connector**: MySQL Connector/J 9.1.0
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA
- **Version Control**: Git

## 📂 Project Structure
```
GoalGetter-Master-Your-Daily-Tasks/

├── database/
│   └── schema.sql                            
├── lib/
│   └── task-library.jar                      
├── src/
│   ├── Main.java                             
│   ├── Task.java                             
│   ├── User.java                             
│   ├── TaskManager.java                      
│   ├── NotificationManager.java              
│   ├── DatabaseConnection.java                               
└── README.md                                 
```

## 🗄️ Database Schema

### Users Table
```
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
### Tasks Table
```
 CREATE TABLE `tasks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `task` varchar(255) NOT NULL,
  `description` text,
  `due_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tasks_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```
### Categories Table
```
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `due_date` date NOT NULL,
  `reminder` int DEFAULT NULL,
  `completed` tinyint(1) DEFAULT '0',
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `categories_ibfk_1` (`user_id`),
  CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

## 👤 Contributor

| Name            | Role              | Contact                     |  
|------------------|-------------------|-----------------------------|  
| <a href = "https://github.com/JohnAlcaraz02">John C. Alcaraz</a> | Developer & Author | [johnalcaraz026@gmail.com](mailto:johnalcaraz026@gmail.com) |  

## 📚 Course Information  

- **Instructor:** Ms. Fatima Marie P. Agdon  
- **Course:** CS 211: Object-Oriented Programming  
- **Semester:** 1st Semester, AY 2023-2024  
- **Institution:** [Batangas State University]  

