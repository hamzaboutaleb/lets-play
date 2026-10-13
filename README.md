# Play — Spring Boot REST API

A secure, scalable REST API for a small e-commerce-like platform. Built with Spring Boot 4.1.1, JWT authentication, role-based access control, and MongoDB.

## Quick Start

```bash
# Prerequisites: Java 25, Maven 3.9+, MongoDB 7+ (replica set)
./mvnw spring-boot:run
```

Server starts at `http://localhost:9090`.

### MongoDB Replica Set (required for transactions)

```bash
docker run -d --name mongo -p 27017:27017 mongo:7 --replSet rs0 --bind_ip_all
docker exec -it mongo mongosh --eval 'rs.initiate({_id:"rs0",members:[{_id:0,host:"localhost:27017"}]})'
```

## Configuration

`src/main/resources/application.yaml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/play?replicaSet=rs0
    web:
      pageable:
        max-page-size: 100
        page-size: 20

jwt:
  secret: "your-32-char-min-secret-key-here"
  access-token-expiration-ms: 900000      # 15 min
  refresh-token-expiration-ms: 604800000  # 7 days
```

## API Endpoints

### Auth
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/v1/auth/register` | Public | Register new user |
| POST | `/api/v1/auth/login` | Public | Login, returns JWT |

### Users (Admin only)
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/v1/users` | ADMIN | List users (paginated) |
| GET | `/api/v1/users/{id}` | ADMIN | Get user by ID |
| PUT | `/api/v1/users/{id}` | ADMIN | Update user |
| DELETE | `/api/v1/users/{id}` | ADMIN | Delete user + their products |

### Products
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/v1/products` | USER | List products (paginated) |
| GET | `/api/v1/products/{id}` | USER | Get product by ID |
| POST | `/api/v1/products` | USER | Create product (sets `userId` from token) |
| PATCH | `/api/v1/products/{id}` | USER/ADMIN | Update (owner or admin) |
| DELETE | `/api/v1/products/{id}` | USER/ADMIN | Delete (owner or admin) |

### Health
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/v1/health/mongo` | Public | MongoDB connectivity check |

## Request/Response Format

**Success:**
```json
{
  "success": true,
  "data": { ... },
  "timestamp": "2026-10-13T10:30:00.123Z"
}
```

**Error:**
```json
{
  "success": false,
  "error": {
    "message": "Error description",
    "details": { "field": "value" }
  },
  "timestamp": "2026-10-13T10:30:00.123Z"
}
```

**Pagination:** `?page=0&size=20&sort=name,asc` (max size: 100)

## Auth Flow

1. Register or login → receive JWT
2. Include in requests: `Authorization: Bearer <token>`
3. Token expires in 15 min (access), 7 days (refresh — not yet implemented)

## Authorization Rules

| Resource | Admin | Owner | Other Users |
|----------|-------|-------|-------------|
| Users (list, get, update, delete) | ✅ | ❌ | ❌ |
| Products (create) | ✅ | ✅ | ✅ |
| Products (read) | ✅ | ✅ | ✅ |
| Products (update, delete) | ✅ | ✅ | ❌ |

- **Admin** — full access to all resources
- **User** — can manage own products only
- **Products** — `userId` set from JWT on create; ownership enforced on update/delete

## Error Handling

All errors return a consistent JSON format:

```json
{
  "success": false,
  "error": {
    "message": "Human-readable error message",
    "details": { "field": "rejected value" }
  },
  "timestamp": "2026-10-13T10:30:00.123Z"
}
```

| Scenario | HTTP Status | Example Message |
|----------|-------------|-----------------|
| Email already registered | 409 | "Email already registered: user@example.com" |
| Username taken | 409 | "Name already taken: john" |
| User not found | 404 | "User not found: 652abc..." |
| Product not found | 404 | "Product not found: 652def..." |
| Access denied (owner check) | 403 | "Not authorized to update this product" |
| Access denied (role check) | 403 | "Access denied: Access is denied" |
| Validation failed | 400 | "email: must be a valid email; password: size must be between 8 and 30" |
| Unexpected error | 500 | "An unexpected error occurred" |

## Build & Test

```bash
./mvnw test           # run tests
./mvnw clean package  # build JAR
```

## Project Structure

```
src/main/java/play/
├── config/          # Security, JWT
├── controller/      # REST endpoints
├── dto/             # Request/Response DTOs (auth, user, product, common)
├── exception/       # Custom exceptions + GlobalExceptionHandler
├── model/           # MongoDB entities (User, Product, Role)
├── repository/      # Spring Data repositories
├── security/        # JWT, filters, UserDetails
└── service/         # Business logic
```

## Tech Stack

- Java 25, Spring Boot 4.1.1
- Spring Security (JWT, BCrypt, Method Security)
- Spring Data MongoDB (replica set for `@Transactional`)
- JJWT 0.12.6, Lombok, Maven