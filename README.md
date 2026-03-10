## Ticket Booking System

A Spring Boot application for booking tickets. This is a starter project that requires implementation of ticket booking functionality.

### Project Structure

```
src/main/java/
  com/example/ticketbooking/
    App.java        # Main application entry point
src/test/java/
  com/example/ticketbooking/
    AppTest.java    # Test file (currently minimal)
```

### Requirements

- Java 17+
- Maven 3.6+

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/ticket-booking
   ```

2. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Access the application at:
   ```
   http://localhost:8080
   ```

### Current State

This project currently contains only the Spring Boot starter application. The `App.java` file prints "Hello World!" but does not implement any ticket booking functionality.

### Next Steps

1. Implement the ticket booking domain model:
   - Create classes for `Ticket`, `Event`, `User`, etc.
   - Set up database entities with JPA

2. Add REST API endpoints:
   - `POST /tickets` - create new booking
   - `GET /tickets` - get all bookings
   - `GET /tickets/{id}` - get specific booking

3. Implement database persistence using Spring Data JPA

4. Add authentication and authorization for users

### Contributing

This project is currently in early development. We welcome contributions to:
- Implement core functionality
- Improve documentation
- Add test cases

### Notes

- This project uses Maven as the build tool
- Current test dependency is JUnit 3.8.1 (outdated - consider upgrading to JUnit 5)
- The application currently doesn't have any routing or API endpoints
