# Bug Fixed – Invalid JSON Response & Validation Errors

**Date:** 2026-03-10
**Version:** 1.0.0

---

## Issue Description
When calling the `/api/auth/login` endpoint with an empty or missing `usernameOrEmail` field, the server returned a malformed error response and the application failed to start with the following stack‑trace excerpt:

```
Unresolved compilation problems:
    The type ApiResponse is not generic; it cannot be parameterized with arguments <...>
    ApiResponse cannot be resolved to a type
    ...
    The constructor MovieResponse(Long, String, Integer) is undefined
```

Additionally, validation errors were not being caught by a global exception handler, causing Spring to return raw `MethodArgumentNotValidException` messages instead of a consistent JSON payload.

---

## Root Causes

| # | Problem | Why it Happened |
|---|---------|-----------------|
| 1 | **`ApiResponse` not declared as a generic class** | The original `ApiResponse` was raw, so it could not be parameterized with `<MovieResponse>` or `<AuthResponse>`. This broke compilation after refactoring controllers to use generic types. |
| 2 | **`MovieResponse` lacked a constructor that matches used arguments** | Controllers created `MovieResponse(movie.getId(), movie.getTitle(), movie.getDuration())`, but `MovieResponse` only had setters and no matching constructor, leading to “constructor undefined” errors. |
| 3 | **Missing `@JsonProperty` mapping** on `LoginRequest.usernameOrEmail` when the incoming JSON used the key `reg`. This caused the field to be deserialized as `null`, triggering Bean Validation failures. |
| 4 | **No global exception handler for validation errors** | Without a `@RestControllerAdvice`, `MethodArgumentNotValidException` propagated as an unhandled error, resulting in raw error messages instead of a standardized JSON response. |
| 5 | **Incorrect generic type usage in controller signatures** | Controllers returned `ResponseEntity<ApiResponse<...>>` but the `ApiResponse` class was not generic, causing type‑mismatch compile errors. |

---

## Fixes Applied

### 1. Made `ApiResponse` Generic
```java
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private String timestamp;
    // constructors, getters, setters
}
```
All controller methods now correctly instantiate `ApiResponse<T>` with the appropriate payload type.

### 2. Added Required Constructors & Getters/Setters
- **`MovieResponse`** now includes a constructor `MovieResponse(Long id, String title, Integer duration)` that matches the usage in the controllers.
- Added full set of getters and setters for every field.

### 3. Corrected `LoginRequest` Mapping
```java
@JsonProperty("reg")
@NotBlank(message = "Tên đăng nhập không được để trống")
private String usernameOrEmail;
```
The `@JsonProperty("reg")` annotation maps the incoming JSON key `reg` to the Java field `usernameOrEmail`, preventing `null` deserialization.

### 4. Implemented Global Exception Handler (`RestExceptionHandler`)
Created `src/main/java/com/example/ticketbooking/exception/RestExceptionHandler.java` with handlers for:
- `MethodArgumentNotValidException` – returns a combined field‑error message.
- `BindException` – handles validation errors on request parameters.
- `ConstraintViolationException` – handles annotation‑level validation errors.
- Generic `Exception` – returns a fallback 500 error response.

All handlers wrap the response in the same `ApiResponse<String>` format used for successful calls, ensuring consistent output.

### 5. Updated Controller Signatures
All controller methods now return:
```java
ResponseEntity<ApiResponse<MovieResponse>> …
ResponseEntity<ApiResponse<Void>> …
ResponseEntity<ApiResponse<List<MovieResponse>>> …
```
and the corresponding `ApiResponse` instantiations include the generic type argument.

---

## Verification Steps

1. **Re‑compile the project**
   ```bash
   ./mvnw clean compile
   ```
   No compilation errors should appear.

2. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   The server should start without `BeanCreationException`.

3. **Test the login endpoint with valid JSON**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
        -H "Content-Type: application/json" \
        -d '{
              "reg": "pongdev1",
              "password": "12345678"
            }'
   ```
   Expected response (example):
   ```json
   {
     "status": 200,
     "message": "Đăng nhập thành công",
     "data": {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "message": "Đăng nhập thành công"
     },
     "timestamp": "2026-03-10T19:15:03.495+07:00"
   }
   ```

4. **Test validation failure**
   Send an empty body or omit `reg`. The response should be:
   ```json
   {
     "status": 400,
     "message": "Tên đăng nhập không được để trống",
     "data": null,
     "timestamp": "2026-03-10T19:15:03.495+07:00"
   }
   ```

5. **All REST endpoints (`/api/movies`…)** should now return responses wrapped in `ApiResponse` with consistent `status`, `message`, `data`, and `timestamp` fields.

---

## Files Modified / Added

| File Path | Purpose |
|-----------|---------|
| `src/main/java/com/example/ticketbooking/dto/ApiResponse.java` | Generic wrapper with type parameter. |
| `src/main/java/com/example/ticketbooking/dto/MovieResponse.java` | Added matching constructor and full getters/setters. |
| `src/main/java/com/example/ticketbooking/dto/LoginRequest.java` | Added `@JsonProperty("reg")` mapping. |
| `src/main/java/com/example/ticketbooking/controller/AuthenticationController.java` | Updated return type to `ResponseEntity<ApiResponse<AuthResponse>>`. |
| `src/main/java/com/example/ticketbooking/controller/MovieController.java` | Updated generic return types and `ApiResponse` instantiation. |
| `src/main/java/com/example/ticketbooking/exception/RestExceptionHandler.java` | New global exception handler for validation and generic errors. |

---

## Impact
- **Consistent API contract**: Every endpoint now returns a predictable JSON structure.
- **Better error handling**: Validation failures are caught and returned with clear Vietnamese messages and proper HTTP status codes.
- **No breaking changes** for existing clients; they will simply receive a standardized payload instead of raw error messages.
- **Future‑proof**: Generic `ApiResponse<T>` can be reused for any future response data type.

---

**Prepared by:**
*Claude Sonnet 4.6 (1M context) – Auto‑generated documentation*

---

*End of `bug-fixed.md`*