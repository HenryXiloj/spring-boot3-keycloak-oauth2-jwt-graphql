# Spring Boot 3 + Keycloak + OAuth2 + JWT + GraphQL

A comprehensive example demonstrating how to secure GraphQL and REST APIs using OAuth2 Resource Server with Spring Security 6.1, Keycloak 22, and Angular 16.

📘 Blog Post: [Securing GraphQL / REST APIs with OAuth2 Resource Server Spring Security 6.1 & Keycloak 22 & Angular 16](https://jarmx.blogspot.com/2023/07/securing-graphql-rest-apis-with-oauth2.html)

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
  - [1. Keycloak Setup](#1-keycloak-setup)
  - [2. Spring Boot GraphQL API](#2-spring-boot-graphql-api)
  - [3. Spring Boot REST API](#3-spring-boot-rest-api)
  - [4. Angular Frontend](#4-angular-frontend)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Key Features](#key-features)
- [Security Configuration](#security-configuration)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)

## 🔍 Overview

This project demonstrates a complete OAuth2 implementation where:
- **Keycloak** serves as the Authorization Server (Identity Provider)
- **Spring Boot GraphQL API** acts as a Resource Server
- **Spring Boot REST API** acts as a Resource Server
- **Angular 16** serves as the Client Application

The implementation showcases how to:
- Secure GraphQL and REST endpoints with JWT tokens
- Handle role-based access control (RBAC)
- Integrate OAuth2 Resource Server with Spring Security 6.1
- Use Keycloak for centralized authentication and authorization

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Angular 16    │    │   Keycloak 22   │    │  Spring Boot    │
│    Client       │◄──►│  Auth Server    │◄──►│ Resource Server │
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                                              │
         └──────────────────────────────────────────────┘
                    Direct API Calls (with JWT)
```

## 🛠️ Technology Stack

### Backend
- **Spring Boot**: 3.1.1
- **Spring Security**: 6.1
- **Java**: 17
- **Maven**: Build tool
- **GraphQL**: API query language
- **OAuth2 Resource Server**: JWT token validation

### Frontend
- **Angular**: 16
- **Angular Material**: UI components
- **TypeScript**: Programming language
- **Node.js**: 18
- **npm**: 9

### Infrastructure
- **Keycloak**: 22.0.0
- **Docker**: For Keycloak deployment

## 📋 Prerequisites

- Java 17+
- Node.js 18+
- npm 9+
- Docker
- Maven 3.6+
- Angular CLI 16

## 📁 Project Structure

```
spring-boot3-keycloak-oauth2-jwt-graphql/
├── graphql-api/                    # Spring Boot GraphQL API
│   ├── src/main/java/
│   │   ├── configuration/          # Security & GraphQL config
│   │   ├── controller/             # GraphQL controllers
│   │   ├── dto/                    # Data Transfer Objects
│   │   └── resolver/               # GraphQL resolvers
│   ├── src/main/resources/
│   │   ├── graphql/               # GraphQL schemas
│   │   └── application.yml        # Configuration
│   └── pom.xml
├── rest-api/                       # Spring Boot REST API
│   ├── src/main/java/
│   │   ├── configuration/          # Security config
│   │   ├── controller/             # REST controllers
│   │   ├── dto/                    # Data Transfer Objects
│   │   └── services/               # Business logic
│   ├── src/main/resources/
│   │   └── application.yml        # Configuration
│   └── pom.xml
├── angular-frontend/               # Angular 16 Frontend
│   ├── src/app/
│   │   ├── components/            # UI components
│   │   ├── services/              # HTTP services
│   │   ├── guards/                # Route guards
│   │   └── interceptors/          # HTTP interceptors
│   ├── src/environments/          # Environment configs
│   └── package.json
└── keycloak/
    └── realm.json                 # Keycloak realm configuration
```

## 🚀 Setup Instructions

### 1. Keycloak Setup

#### Start Keycloak with Docker

```bash
docker run -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:22.0.0 start-dev
```

#### Configure Keycloak

1. **Access Admin Console**: http://localhost:8080
2. **Login**: admin/admin

#### Create Realm
1. Click "Add realm" → Name: `demo` → Create

#### Create Client
1. Go to Clients → Create
2. **Client ID**: `spring-boot-keycloak`
3. **Client Protocol**: `openid-connect`
4. **Access Type**: `confidential`
5. **Valid Redirect URIs**:
   - `http://localhost:8081/*`
   - `http://localhost:8082/*`
   - `http://localhost:4200/*`

#### Create Roles
1. Go to Roles → Add Role
2. Create roles: `ADMIN`, `USER`

#### Create Users
1. Go to Users → Add User
2. Create users:
   - **Username**: `admin`, **Password**: `admin`
   - **Username**: `henry`, **Password**: `henry`
3. Assign roles to users in Role Mappings tab

### 2. Spring Boot GraphQL API

#### Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-graphql</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### Configuration (application.yml)
```yaml
server:
  port: 8081

spring:
  security:
    oauth2:
      resource-server:
        jwt:
          issuer-uri: http://localhost:8080/realms/demo
          jwk-set-uri: http://localhost:8080/realms/demo/protocol/openid-connect/certs
  graphql:
    graphiql:
      enabled: true
```

#### Run GraphQL API
```bash
cd graphql-api
mvn spring-boot:run
```

### 3. Spring Boot REST API

#### Configuration (application.yml)
```yaml
server:
  port: 8082

spring:
  security:
    oauth2:
      resource-server:
        jwt:
          issuer-uri: http://localhost:8080/realms/demo
          jwk-set-uri: http://localhost:8080/realms/demo/protocol/openid-connect/certs
```

#### Run REST API
```bash
cd rest-api
mvn spring-boot:run
```

### 4. Angular Frontend

#### Install Dependencies
```bash
cd angular-frontend
npm install
```

#### Environment Configuration
Update `src/environments/environment.ts`:
```typescript
export const environment = {
  production: false,
  client_ID: "spring-boot-keycloak",
  client_secret: "YOUR_CLIENT_SECRET", // Get from Keycloak
  grant_type: "password",
  keycloakEndpoint: "http://localhost:8080/realms/demo/protocol/openid-connect/token",
  graphql_api: "http://localhost:8081/graphql",
  rest_api: "http://localhost:8082/"
};
```

#### Run Angular App
```bash
ng serve
```

## 🏃‍♂️ Running the Application

1. **Start Keycloak** (port 8080)
2. **Start GraphQL API** (port 8081)
3. **Start REST API** (port 8082)
4. **Start Angular Frontend** (port 4200)

Access the application at: http://localhost:4200

## 🧪 Testing

### Using Postman

#### Get Access Token
```http
POST http://localhost:8080/realms/demo/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic <base64(client_id:client_secret)>

grant_type=password&username=admin&password=admin
```

#### Test GraphQL API
```http
POST http://localhost:8081/graphql
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "query": "query { getName }"
}
```

#### Test REST API
```http
GET http://localhost:8082/api/name
Authorization: Bearer <access_token>
```

### Using Angular Frontend

1. Navigate to http://localhost:4200
2. Login with credentials:
   - **Admin**: admin/admin
   - **User**: henry/henry
3. Test GraphQL and REST endpoints through the UI

## ✨ Key Features

- **JWT Token-based Authentication**: Secure API access using JWT tokens
- **Role-based Access Control**: ADMIN and USER roles with different permissions
- **GraphQL Integration**: Secure GraphQL endpoints with OAuth2
- **REST API Security**: Traditional REST endpoints with OAuth2 protection
- **CORS Configuration**: Proper CORS setup for cross-origin requests
- **Angular Interceptors**: Automatic token attachment and error handling
- **Route Guards**: Protected routes in Angular application

## 🔒 Security Configuration

### GraphQL Endpoints
- `POST /graphql` - Requires ADMIN or USER role
- `getName` query - Requires ADMIN or USER role
- `getJWTByUser` query - Requires ADMIN role only

### REST Endpoints
- `/api/**` - Requires ADMIN or USER role
- `/admin/**` - Requires ADMIN role only

### JWT Token Validation
- Automatic JWT signature validation
- Role extraction from JWT claims
- Token expiration handling

## 📡 API Endpoints

### GraphQL API (Port 8081)
```graphql
type Query {
  getName: String
  getJWTByUser: JWTTokenDto
}
```

### REST API (Port 8082)
```
GET /api/name - Get user name
GET /api/principal - Get JWT token details
```


## 📚 References

- [Spring Security OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [Keycloak Documentation](https://www.keycloak.org/documentation)
- [GraphQL Java](https://www.graphql-java.com/)
- [Angular OAuth2 OIDC](https://angular-oauth2-oidc.manfredsteyer.net/)


---

**Detailed description and steps for running the project found here:**  
[Securing GraphQL / REST APIs with OAuth2 Resource Server Spring Security 6.1 & Keycloak 22 & Angular 16](https://jarmx.blogspot.com/2023/07/securing-graphql-rest-apis-with-oauth2.html)
