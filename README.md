# Museec API

**Museec API** is a backend application developed as a university project. It allows users to find and connect musicians to bands, providing endpoints to manage musicians, bands, and matching logic.

## About The Project

Museec was created as part of a final university project. The API is built with Spring Boot and provides a robust backend to manage musicians, bands, and their associations. It includes different build profiles for development, quality, and production environments.

## Built With

[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)  
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)  
[![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)  
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

## Getting Started

### Folder Structure

```markdown
museec-api/
├── 📁 src/
│   ├── 📁 main/
│   │   └── 📁 java/
│   │       └── 📁 fr/univartois/butinfo/s5a01/musicmatcher/
│   │           ├── 📁 auth        # Authentication and security logic
│   │           ├── 📁 controller  # REST controllers for API endpoints
│   │           ├── 📁 document    # MongoDB Entities
│   │           ├── 📁 dto         # Data Transfer Objects for requests/responses
│   │           ├── 📁 mapper      # MapStruct mappers for DTO <-> entity
│   │           ├── 📁 repository  # Spring Data JPA repositories
│   │           ├── 📁 request     # Request validation and objects
│   │           ├── 📁 service     # Business logic and service layer
│   │           └── 📁 utils       # Utility classes and helper functions
├── 📁 test/                     # Unit and integration tests
├── 📄 build.gradle.kts          # Gradle build configuration
├── 📄 settings.gradle.kts       # Gradle settings
├── 📄 Dockerfile                # Docker configuration
├── 📄 Dockerfile-prod           # Production Docker configuration
└── 📄 README.md                 # Project documentation


### Prerequisites

Ensure you have the following installed:

```sh
Java 17+
Gradle 7+
Docker (optional for containerized builds)
```

### Installation & Build

1. Clone the repository:

```sh
git clone https://github.com/JulesBobeuf/museec-api.git
cd museec-api
```

2. Build the JAR with different environments:

```sh
gradle clean bootJar -Penv=dev    # Development
gradle clean bootJar -Penv=qual   # Quality
gradle clean bootJar -Penv=prod   # Production
```

3. (Optional) Run the application with Docker:

```sh
docker build -t museec-api .
docker run -p 8080:8080 museec-api
```

### Tests

Run tests using Gradle:

```sh
gradle test -i
```

> ⚠️ Do not run tests directly with JUnit 5; standalone JUnit will not find MapStruct mapper implementations.

### SonarQube Reports

Generate SonarQube reports:

```sh
gradle sonar -D "sonar.login=<token>"
```

## Usage

Once the application is running (e.g., `http://localhost:8080`), you can interact with the API using REST clients such as Postman or Curl to manage musicians, bands, and perform matching.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

Jules Bobeuf  
[LinkedIn](https://www.linkedin.com/in/bobeuf-jules/)  
bobeuf.jules@gmail.com

