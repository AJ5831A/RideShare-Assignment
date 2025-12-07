# 🚗 RideShare Backend

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring-boot)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-green?style=for-the-badge&logo=mongodb)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange?style=for-the-badge&logo=json-web-tokens)
![Java](https://img.shields.io/badge/Java-17+-blue?style=for-the-badge&logo=openjdk)

A secure and scalable Ride-Sharing backend built with **Spring Boot**, **MongoDB**, and **JWT Authentication**.

[Features](#-features) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started) • [API Documentation](#-api-documentation) • [Postman Collection](#-postman-collection)

</div>

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Postman Collection](#-postman-collection)
- [Database Schema](#-database-schema)
- [Security](#-security)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### 🔐 Authentication & Security
- ✅ User registration with **BCrypt password encryption**
- ✅ Secure login with **JWT token generation**
- ✅ Custom `UserDetailsService` implementation
- ✅ Role-based access control (`USER`, `DRIVER`)
- ✅ Protected endpoints with JWT validation

### 🚦 Ride Management
- ✅ Users can **request rides** with pickup and drop locations
- ✅ Drivers can view **pending ride requests**
- ✅ Drivers can **accept rides**
- ✅ Complete ride workflow tracking
- ✅ Real-time ride status updates

### 📊 Ride Status Flow
```
REQUESTED → ACCEPTED → ONGOING → COMPLETED
                    ↓
                CANCELLED
```

### 🗄️ Database
- ✅ MongoDB Atlas cloud integration
- ✅ Optimized collections for users and rides
- ✅ Indexed queries for better performance

---

## 🛠 Tech Stack

| Component | Technology |
|-----------|------------|
| **Framework** | Spring Boot 3.x |
| **Language** | Java 17+ |
| **Database** | MongoDB Atlas |
| **Authentication** | JWT (HS256) |
| **Security** | Spring Security |
| **Password Encryption** | BCrypt |
| **Build Tool** | Maven |
| **API Testing** | Postman |

---

## 🏗 Architecture

```
┌─────────────────┐
│   REST API      │
│  (Controllers)  │
└────────┬────────┘
         │
┌────────▼────────┐
│    Service      │
│     Layer       │
└────────┬────────┘
         │
┌────────▼────────┐
│   Repository    │
│     Layer       │
└────────┬────────┘
         │
┌────────▼────────┐
│   MongoDB       │
│    Atlas        │
└─────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB Atlas account
- Postman (for API testing)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/AJ5831A/RideShare-Assignment.git
cd RideShare-Assignment
```

2. **Configure MongoDB**

Create a MongoDB Atlas cluster and update `src/main/resources/application.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@cluster0.mongodb.net/rideshare
```

3. **Configure JWT Secret**

Update the JWT secret in `application.yml`:

```yaml
jwt:
  secret: your256bitlongsecuresecretkeyhere1234567890
  expiration-ms: 86400000  # 24 hours
```

4. **Build the project**
```bash
./mvnw clean install
```

5. **Run the application**
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

---

## ⚙️ Configuration

### application.yml

```yaml
spring:
  application:
    name: rideV1
  data:
    mongodb:
      uri: mongodb+srv://<user>:<password>@cluster0.mongodb.net/rideshare

jwt:
  secret: your256bitlongsecuresecretkeyhere1234567890
  expiration-ms: 86400000

server:
  port: 8080
```

### Environment Variables (Alternative)

```bash
MONGODB_URI=mongodb+srv://<user>:<password>@cluster0.mongodb.net/rideshare
JWT_SECRET=your256bitlongsecuresecretkeyhere1234567890
JWT_EXPIRATION=86400000
```

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### 🔑 Authentication Endpoints

#### 1. Register User
```http
POST /auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securepass123",
  "role": "USER"
}
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "username": "john_doe",
  "role": "USER"
}
```

#### 2. Register Driver
```http
POST /auth/register
Content-Type: application/json

{
  "username": "driver_jane",
  "password": "securepass123",
  "role": "DRIVER"
}
```

#### 3. Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securepass123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 86400000
}
```

---

### 🚕 User Ride Endpoints

#### 1. Request a Ride
```http
POST /rides/request
Authorization: Bearer <token>
Content-Type: application/json

{
  "pickupLocation": "Silk Board",
  "dropLocation": "HSR Layout"
}
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439012",
  "userId": "507f1f77bcf86cd799439011",
  "pickupLocation": "Silk Board",
  "dropLocation": "HSR Layout",
  "status": "REQUESTED",
  "createdAt": "2024-12-07T10:30:00Z"
}
```

#### 2. Get My Rides
```http
GET /user/rides
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": "507f1f77bcf86cd799439012",
    "pickupLocation": "Silk Board",
    "dropLocation": "HSR Layout",
    "status": "COMPLETED",
    "driverId": "507f1f77bcf86cd799439013",
    "fare": 150.00,
    "createdAt": "2024-12-07T10:30:00Z",
    "completedAt": "2024-12-07T11:00:00Z"
  }
]
```

---

### 🚗 Driver Ride Endpoints

#### 1. Get Pending Ride Requests
```http
GET /driver/rides/requests
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": "507f1f77bcf86cd799439012",
    "userId": "507f1f77bcf86cd799439011",
    "pickupLocation": "Silk Board",
    "dropLocation": "HSR Layout",
    "status": "REQUESTED",
    "createdAt": "2024-12-07T10:30:00Z"
  }
]
```

#### 2. Accept Ride
```http
POST /driver/rides/{rideId}/accept
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439012",
  "status": "ACCEPTED",
  "driverId": "507f1f77bcf86cd799439013",
  "acceptedAt": "2024-12-07T10:32:00Z"
}
```

---

### ✅ Complete Ride Endpoint

#### Complete Ride
```http
POST /rides/{rideId}/complete
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": "507f1f77bcf86cd799439012",
  "status": "COMPLETED",
  "completedAt": "2024-12-07T11:00:00Z",
  "fare": 150.00
}
```

---

## 📮 Postman Collection

### Import Collection

1. **Download the collection** or copy the JSON below
2. Open Postman
3. Click **Import** → **Raw text**
4. Paste the collection JSON
5. Click **Import**

### Collection JSON

```json
{
  "info": {
    "name": "RideShare API",
    "_postman_id": "b8a1e8fa-9d5f-4b0b-9121-ride-share",
    "description": "Complete API collection for RideShare Backend (Spring Boot + MongoDB + JWT)",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register User",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/auth/register",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "auth", "register"]
            },
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"user1\",\n  \"password\": \"pass123\",\n  \"role\": \"USER\"\n}"
            }
          }
        },
        {
          "name": "Register Driver",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/auth/register",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "auth", "register"]
            },
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"driver1\",\n  \"password\": \"pass123\",\n  \"role\": \"DRIVER\"\n}"
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/auth/login",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "auth", "login"]
            },
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"user1\",\n  \"password\": \"pass123\"\n}"
            }
          }
        }
      ]
    },
    {
      "name": "User - Rides",
      "item": [
        {
          "name": "Request Ride",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              },
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/rides/request",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "rides", "request"]
            },
            "body": {
              "mode": "raw",
              "raw": "{\n  \"pickupLocation\": \"Silk Board\",\n  \"dropLocation\": \"HSR Layout\"\n}"
            }
          }
        },
        {
          "name": "Get My Rides",
          "request": {
            "method": "GET",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/user/rides",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "user", "rides"]
            }
          }
        }
      ]
    },
    {
      "name": "Driver - Rides",
      "item": [
        {
          "name": "Get Pending Ride Requests",
          "request": {
            "method": "GET",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/driver/rides/requests",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "driver", "rides", "requests"]
            }
          }
        },
        {
          "name": "Accept Ride",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/driver/rides/{{rideId}}/accept",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "driver", "rides", "{{rideId}}", "accept"]
            }
          }
        }
      ]
    },
    {
      "name": "Complete Ride",
      "item": [
        {
          "name": "Complete Ride",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Authorization",
                "value": "Bearer {{token}}"
              }
            ],
            "url": {
              "raw": "http://localhost:8080/api/rides/{{rideId}}/complete",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "rides", "{{rideId}}", "complete"]
            }
          }
        }
      ]
    }
  ],
  "variable": [
    {
      "key": "token",
      "value": ""
    },
    {
      "key": "rideId",
      "value": ""
    }
  ]
}
```

### Using the Collection

1. **Set Variables**
   - After login, copy the JWT token from the response
   - Set it in the collection variable `{{token}}`
   - Set `{{rideId}}` when you receive a ride ID

2. **Test Flow**
   - Register a user and a driver
   - Login as user → save token
   - Request a ride → save rideId
   - Login as driver → save driver token
   - Get pending requests
   - Accept the ride using rideId
   - Complete the ride

---

## 🗃 Database Schema

### User Collection

```javascript
{
  "_id": ObjectId,
  "username": String (unique),
  "password": String (BCrypt encrypted),
  "role": String (enum: USER, DRIVER),
  "createdAt": Date,
  "updatedAt": Date
}
```

### Ride Collection

```javascript
{
  "_id": ObjectId,
  "userId": ObjectId (ref: User),
  "driverId": ObjectId (ref: User, optional),
  "pickupLocation": String,
  "dropLocation": String,
  "status": String (enum: REQUESTED, ACCEPTED, ONGOING, COMPLETED, CANCELLED),
  "fare": Number,
  "createdAt": Date,
  "acceptedAt": Date (optional),
  "completedAt": Date (optional),
  "cancelledAt": Date (optional)
}
```

---

## 🔒 Security

### Authentication Flow

1. User registers with username/password
2. Password is encrypted using BCrypt
3. User logs in with credentials
4. Server validates credentials and generates JWT token
5. Client includes token in Authorization header for protected routes
6. Server validates token and extracts user information

### Password Security

- Passwords are hashed using **BCrypt** with 10 rounds
- Original passwords are never stored
- Secure against rainbow table attacks

### JWT Token

- Algorithm: **HS256**
- Expiration: **24 hours** (configurable)
- Contains: user ID, username, and roles
- Validated on every protected request

---

## 🧪 Testing

### Manual Testing with Postman

1. Import the provided Postman collection
2. Follow the test flow described above
3. Verify responses match expected formats

## 👨‍💻 Author

**Aryan Jakhar**

- GitHub: [@AJ5831A](https://github.com/AJ5831A)
- Repository: [RideShare-Assignment](https://github.com/AJ5831A/RideShare-Assignment)

---

---

<div align="center">

Made with ❤️ using Spring Boot and MongoDB

</div>
