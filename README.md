# Music Platform Backend - Java Spring Boot 🎵🎧

<img src="https://i.pinimg.com/564x/87/99/59/879959c9e18729568156805fbb6c912b.jpg" alt="Music Platform Logo" width="200" />


Welcome to the Music Platform Backend project! This application is a Java Spring Boot backend for a simple music platform. It provides essential functionalities for managing music tracks and user authentication.

**This project was implemented as part of the SHIFT Intensive organized by [CFT Team](https://team.cft.ru/).**


## Technologies Used

- Java Spring Boot: Web framework for building Java applications.
- Cassandra: NoSQL database for handling data.
- Spring Security: Authentication and access control for Spring applications.
- Spring Actuator: Monitor and manage application health and metrics.
- JUnit 5: Testing framework for Java applications.
- Docker: Containerization platform for easy deployment.
- OpenAPI: Specification for documenting and interacting with RESTful APIs.

## Getting Started

To get started with the Music Platform Backend project, follow these steps:
### Option 1: Installation using Gradle
1. Clone the repository: `git clone https://github.com/T9404/Music-Platform.git`
2. Install the required dependencies by running the following Gradle command: `./gradlew build`
3. Set up Cassandra database and update the configuration accordingly.
4. Build the project using your favorite IDE or use Gradle: `./gradlew build`
5. Run the application: `java -jar template-1.0.0.jar`
### Option 2: Installation using Docker
1. Clone the repository: `git clone https://github.com/T9404/Music-Platform.git`
2. Set up the Cassandra database and update the configuration accordingly.
3. Build the Docker image: `docker build -t music-platform-backend . `
4. Run the application using Docker: `docker run -p 8080:8080 music-platform-backend`

## How to Use

Once the application is up and running, you can access the API documentation provided by OpenAPI to understand the available endpoints and how to interact with them. *Ensure you have the necessary authentication credentials to access restricted features*.
