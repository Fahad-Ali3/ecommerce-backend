# 🛍️ E-Commerce Backend  (Spring Boot)



Welcome to the **E-Commerce Backend API**, a fully functional, secure, and extensible RESTful backend built with **Spring Boot**.  
This project is designed as a robust foundation for modern online stores, supporting user authentication, product management, cart operations, orders, and integrated payments.

---
**🚧 THIS PROJECT IS STILL IN DEVELOPMENT — BUGS & ISSUES MAY EXIST AND WILL BE FIXED CONTINUOUSLY 🚧**
---


## ✨ Features

✅ **Spring Boot & Spring Data JPA** — Rapid development with powerful ORM support using **MySQL**.  
✅ **JWT-Based Security** — Secure authentication & role-based authorization.  
✅ **RESTful APIs** — Clean REST architecture with CRUD operations for all core entities.  
✅ **Payment Integration** — Seamless payment processing using **Stripe**.  
✅ **DTOs & Manual Mapping** — Data Transfer Objects for clean API contracts and clear separation of concerns.  
✅ **Pagination & Sorting** — Efficient handling of large product catalogs.  
✅ **API Documentation** — Integrated **Swagger UI** for easy API exploration and testing.  
✅ **Postman Tested** — Fully tested endpoints using Postman collections.

---

## ⚙️ Tech Stack

- **Backend:** Spring Boot, Spring Security, Spring Data JPA  
- **Database:** MySQL  
- **Authentication:** JWT Token, Role-Based Access Control  
- **Payment Gateway:** Stripe  
- **API Docs:** Swagger/OpenAPI

---

## 🗂️ Architecture

This project follows a clean layered architecture:

- **Entities:** `User`, `Category`, `Product`, `Cart`, `CartItem`, `Order`, `OrderItem`, `Payment`  
- **DTOs:** Data Transfer Objects for each entity to ensure clean API responses and requests.  
- **Repositories:** JPA Repositories for database operations.  
- **Services:** Business logic encapsulated in `Service` and `Impl` layers.  
- **Controllers:** REST Controllers for exposing CRUD operations.

---

## 🔒 Authentication

- **Register & Login:** Secure endpoints for user signup and login.  
- **JWT Token:** Issued at login for secure API access.  
- **Role-Based Access:** Different access levels for admin, seller, or customer.

---

## 💳 Payment Integration

Integrated with **Stripe** to handle secure payments for orders.  
Supports creating and processing payment intents seamlessly.

---

## 📚 API Documentation

Interactive API docs available via **Swagger UI**.  
Explore and test all endpoints directly from your browser.

---

## 🧪 Testing

All endpoints have been tested using **Postman** collections to ensure reliability and correctness.

---

## 🚦 Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/your-repo.git
