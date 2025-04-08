# 💸 Account API

This is a RESTful API built with Spring Boot that simulates basic banking operations such as balance inquiries and processing of deposit, withdrawal, and transfer events.
It was developed as part of a technical challenge and does not use a database — data is stored in memory to keep the application simple.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.4.4
- MapStruct
- Lombok
- Jakarta Validation & Persistence

## 📦 Dependencies

Main dependencies are listed in `pom.xml`:
- `spring-boot-starter-web`
- `lombok`
- `mapstruct` & `mapstruct-processor`
- `jakarta.validation-api`
- `jakarta.persistence-api`

## 📌 Endpoints

### GET `/balance?account_id={id}`  
Returns the balance for a given account.

**Success Response (200):**
```json
100.0
```

**Error (404):**
```json
{
  "message": "Account not found",
  "code": "0"
}
```

---

### POST `/event`  
Processes financial events between accounts.  
Event types: `deposit`, `withdraw`, `transfer`.

**Request example:**
```json
{
  "type": "deposit",
  "destination": "100",
  "amount": 10.0
}
```

**Success Response example:**
```json
{
  "destination": {
    "id": "100",
    "balance": 10.0
  }
}
```

## ⚙️ How to Run

```bash
# Clone the repository
git clone https://github.com/evelynsfranca/account-api
cd account-api

# Compile and run the project
./mvnw spring-boot:run
```

## 🧪 Tests

The project includes support for testing with `spring-boot-starter-test`.  
Run the tests with:

```bash
./mvnw test
```

## 📁 Project Structure

```
com.account.api
├── domain
│   ├── model              # Application models (Account, Event)
│   ├── enumeration        # Enums (EventType)
│   └── service            # Business logic
├── dto                    # Data Transfer Objects
├── mapper                 # Converters using MapStruct
├── repository             # In-memory storage simulation
├── controller             # REST Controllers (entry points)
├── application            # Application services (use cases, coordination logic)
├── exception              # Custom exceptions and error handling
└── context                # Context initializers and configurations
```

## ✨ Notes

- The application does not use a database by design.
- Exceptions are handled using `BusinessException` and `ResponseStatusException`.
- All data is reset when the application restarts.

## 📫 Contact

If you have any questions or suggestions, feel free to reach out!