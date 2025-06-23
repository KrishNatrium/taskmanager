# 🧰 Task Manager API

A Spring Boot REST API for managing tasks (Create, Read, Update, Delete).

## 🧪 API Endpoints

| Method | Endpoint         | Description          |
|--------|------------------|----------------------|
| GET    | /api/tasks       | Get all tasks        |
| POST   | /api/tasks       | Create a new task    |
| PUT    | /api/tasks/{id}  | Update a task        |
| DELETE | /api/tasks/{id}  | Delete a task        |

## 💾 Tech Stack

- Java 21
- Spring Boot 3
- MySQL
- Maven

## ▶️ Run Locally

1. Start MySQL and create the database:
```sql
CREATE DATABASE taskdb;
````

2. Configure credentials in `application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=system
```

3. Run the app:

```bash
./mvnw spring-boot:run
```

## 📬 Sample POST Request

```json
{
  "title": "Try the API",
  "description": "Make sure everything works",
  "completed": false
}
```

## 🧪 Testing Information

### 🔧 Tech Stack Used for Testing

* **JUnit 5** – For writing unit and integration tests
* **Mockito** – For mocking dependencies
* **MockMvc** – For simulating HTTP requests in API tests
* **JaCoCo** – For generating code coverage reports

### 🚀 How to Run Tests

To run all tests and generate the code coverage report:

```bash
./mvnw clean test
```

or on Windows:

```bash
mvnw.cmd clean test
```

> 📂 After running, the coverage report can be found at:

```
target/site/jacoco/index.html
```

### 🖼️ Test Coverage Screenshot

> ![JaCoCo Report](./screenshots/imp.png)

---

