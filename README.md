# CodeCrafters

## Team Members

* Bindu Neupane
* Utsav Neupane
* Pranav Raj Purbey
* Gaurav Paneru

---

# Currency Convertor — Java Spring Boot

This is the same Currency Converter project, rebuilt in Java using Spring Boot. The UI (HTML/CSS) is **identical** to the original. The backend is now Java.

## Project Structure

```text
currency-convertor-java/
├── pom.xml                                      ← Maven build file
└── src/
    └── main/
        ├── java/com/currency/converter/
        │   ├── CurrencyConvertorApplication.java ← Main entry point
        │   ├── CurrencyController.java           ← REST API controller
        │   └── ConversionResponse.java           ← Response model (DTO)
        └── resources/
            ├── application.properties            ← Server config
            └── static/                           ← Frontend (same UI)
                ├── index.html
                ├── style.css
                ├── country.js
                └── currency.js
```

## How It Works

```text
Browser (HTML/CSS/JS)
        ↓  fetch("/api/convert?from=USD&to=INR&amount=100")
Java Spring Boot Server (port 8080)
        ↓  calls exchangerate-api.com for live rates
Returns JSON → JS displays the result
```

## How to Run

### Requirements

* Java 17+
* Maven (or use the Maven wrapper)

### Steps

```bash
# 1. Go into the project folder
cd currency-convertor-java

# 2. Run the server
mvn spring-boot:run

# 3. Open your browser
# Go to: http://localhost:8080
```

That's it! The application will start, and you can access it in your browser.

## Java Concepts Used

* **Spring Boot** — Java web framework
* **@RestController** — Marks a class as an API controller
* **@GetMapping** — Maps a method to a GET HTTP request
* **@RequestParam** — Reads query parameters from the URL
* **WebClient** — Makes HTTP calls from Java to external APIs
* **ResponseEntity** — Wraps the response with an HTTP status
* **DTO (Data Transfer Object)** — `ConversionResponse` class that holds the response data
