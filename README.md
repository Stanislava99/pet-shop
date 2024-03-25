# Pet Shop Project

Welcome to the Pet Shop Project! This project is designed to manage pets and users in a pet shop environment. It allows users to buy pets and keeps track of purchase history.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup](#setup)
- [Usage](#usage)
  - [Endpoints](#endpoints)
- [Tests](#tests)
- [Contributing](#contributing)
- [License](#license)

## Features
- Allows creation and management of users and pets.
- Supports buying pets and tracks purchase history.
- Provides functionality to list users and pets.
- Logs purchase history with details such as successful and unsuccessful purchases.

## Technologies Used
- Java
- Spring Boot
- Hibernate
- Lombok
- H2 Database (for testing)
- JUnit (for testing)
- Mockito (for testing)

## Setup
1. Clone the repository to your local machine.
2. Ensure you have Java and Maven installed.
3. Open the project in your preferred IDE.
4. Build the project using Maven: `mvn clean install`.
5. Run the application: `mvn spring-boot:run`.
6. The application should now be running locally.

## Usage
- Once the application is running, you can access the endpoints to manage users and pets.
- Use the provided endpoints to create, retrieve, update, and delete users and pets.
- You can also buy pets through the appropriate endpoint.
- View the purchase history to see details of successful and unsuccessful purchases.

### Endpoints

#### Users
- **GET /users**: Retrieve all users.
- **POST /users/create**: Create a list of random  users.

#### Pets
- **GET /pets**: Retrieve all pets.
- **POST /pets/create**: Create a list of random pets.

#### Purchases
- **POST /buy**: Buy pets for users.
- **GET /buy/history**: Print history log of purchases.

## Tests
- Unit tests are provided to ensure the functionality of the service classes.
- Run the tests using Maven: `mvn test`.
- Mockito is used for mocking dependencies in the tests.
