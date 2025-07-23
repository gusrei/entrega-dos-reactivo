# capacitacion-reactivo-modulo-2

This project is part of Delivery 2 of the Reactive Programming Training and serves as an example of a Reactive application 
using Java, Spring Boot, and Gradle.

## Prerequisites

Make sure you have the following programs installed on your system:

- **Java 21**
- **Gradle 7.6 or higher (optional, since the Gradle wrapper is included)**
- **Git**
- **PostgreSQL**

## Project Setup

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/tu-usuario/capacitacion-reactivo-modulo-2.git
   cd capacitacion-reactivo-modulo-2
   
2. Run the docker-compose.yml:
    
   ```bash
    docker-compose up

3. Build the project using Gradle:
   
    ```bash
   ./gradlew build

4. Create a launcher with the following environment variables:

    ```bash
   MONGO_DB_NAME=learning_reactive_mongo;
   MONGO_HOST=127.0.0.1:27017;
   MONGO_PASS=mongodb;
   MONGO_USER=mongodb;
   POSTGRES_DB_NAME=learning_reactive_r2dbc;
   POSTGRES_HOST=localhost;
   POSTGRES_PASSWORD=postgresql;
   POSTGRES_PORT=5432;
   POSTGRES_USER=postgresql;
   REDIS_HOST=localhost;
   REDIS_PORT=6379


5. Once the project is running, you can test it with a curl request:

    ```bash
   curl --location 'http://localhost:8084/learning/reactive/get-evaluation' \
   --header 'email: testEmail@tenpo.cl' \
   --header 'Content-Type: application/json' \
   --data '{"number1": 1,"number2": 2}'