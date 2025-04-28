# Serenity Mental Health Therapy Center System

Serenity is a comprehensive demonstration system designed to simulate the operations of a mental health therapy center. Built with Java, JavaFX, and Hibernate, it showcases robust ORM functionality alongside secure authentication, elaborate role management, scheduling, payment processing, and detailed reporting. This system is intended for both learning and demonstration purposes, illuminating the integration of modern development practices—including JavaFX GUI development, Hibernate ORM for database interactions, BCrypt for secure password storage, and custom exception handling—to build a secure, scalable, and maintainable application.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [License](#license)

---

## Overview

Serenity is built as a full-stack demonstration application to simulate the core operations of a mental health therapy center. It illustrates how Hibernate is used to manage object–relational mapping for entities including Therapists, Therapy Programs, Patients, Therapy Sessions, Users (Admin & Receptionist), and Payments. The system enforces role-based access, securely stores credentials using BCrypt, and uses HQL/JPQL to perform advanced queries—making it an ideal resource for developers looking to understand enterprise-grade Java application architecture.

---

## Features

1. **User Role Management**
    - **Admin:** Manages therapists and therapy programs.
    - **Receptionist:** Handles patient registration, therapy session scheduling, and payment processing.
    - Secure authentication and role-based authorization ensure that only permitted roles can perform specific actions.

2. **Therapist Management (Admin Only)**
    - Add, update, delete, and view therapist details.
    - Assign therapists to designated therapy programs.
    - Track therapist schedules and availability.

3. **Therapy Program Management (Admin Only)**
    - Create, modify, and remove therapy programs.
    - Define program details including name, duration, cost, and description.
    - Link therapy programs to appropriate therapists for proper scheduling.

4. **Patient Management**
    - Perform CRUD operations on patient profiles.
    - Maintain a record of medical history and therapy sessions.
    - Use search and filtering mechanisms to locate patients based on session history.

5. **Therapy Session Scheduling**
    - Book, reschedule, and cancel therapy appointments.
    - Dynamically assign therapists based on availability.
    - Ensure conflict-free scheduling through intelligent therapist-assignment logic.

6. **Payment & Invoice Management**
    - Process payments for therapy sessions.
    - Generate and print invoices.
    - Track payment statuses (pending/completed transactions).

7. **Reporting & Analytics**
    - **Admins:** View comprehensive reports on therapist performance and therapy session statistics.
    - **Receptionists:** Generate financial reports and track payments.
    - Both roles have read-only access to patient therapy histories.

8. **Secure Data Management**
    - Password encryption is implemented using BCrypt.
    - Enforced role-based access control secures the system.
    - Hibernate manages robust database relationships (e.g., One-to-Many: Therapists ↔ Therapy Sessions, Patients ↔ Appointments).

---

## Technology Stack

- **Programming Language:** Java
- **UI Framework:** JavaFX
- **ORM Framework:** Hibernate
- **Security & Encryption:** BCrypt
- **Build Tool:** Maven
- **Database:** MySQL
- **IDE:** IntelliJ IDEA, Eclipse, or any Java-supported IDE

---

## Prerequisites

Before building and running the system, ensure you have:

- **Java JDK 11 or higher:** Ensure JAVA_HOME is set.
- **JavaFX:** Included in your build if using JDK 11 or later (or added as dependencies).
- **Maven or Gradle:** For dependency management and building the project.
- **Database Setup:** Installed and configured MySQL.

---

## License

© 2025 All rights reserved. This project is proprietary software developed by
Chamith Bhanuka.
