
# Awesome Pizza - Spring Boot Project

This project is a pizza ordering portal called **Awesome Pizza**, built with **Spring Boot**.

## Prerequisites

- **Java 17** (or later versions)
- **Maven** (version 3.6+)

## Build the Project

To build the project, use **Maven** to clean and install the dependencies. Run the following command in the project root directory:

```bash
mvn clean install
```

This command will:

- Compile the source code
- Run the unit tests
- Generate the `.jar` file for the application in the `target/` directory

## Run the Project

After building the project, you can run the application using the generated `.jar` file:

```bash
java -jar target/awesomepizza-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the application directly with Maven:

```bash
mvn spring-boot:run
```

This will start the application on the default port (`8080`).

## Swagger API Documentation

You can access the Swagger API documentation by visiting the following URL after starting the application:

```
http://localhost:8080/swagger-ui.html
```
