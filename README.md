# Hotel Search API

REST API for hotel availability searches built with Spring Boot.

## Requirements

- Docker and Docker Compose

## How to run

Build and start all services:

```bash
docker-compose up --build -d
```

This will start:
- Oracle database (port 1521)
- Kafka and Zookeeper
- Hotel Search API (port 8080)

To stop:

```bash
docker-compose down
```

## API Documentation

Swagger UI is available at: http://localhost:8080/swagger-ui.html

## Endpoints

### POST /search

Creates a new hotel search and publishes it to Kafka for asynchronous persistence.

Request:
```json
{
  "hotelId": "hotel123",
  "checkIn": "15/06/2026",
  "checkOut": "22/06/2026",
  "ages": [24, 30, 7]
}
```

Response:
```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### GET /count?searchId={searchId}

Returns the count of identical searches. Two searches are considered identical when they have the same hotelId, checkIn, checkOut, and ages in the same order.

Response:
```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000",
  "search": {
    "hotelId": "hotel123",
    "checkIn": "15/06/2026",
    "checkOut": "22/06/2026",
    "ages": [24, 30, 7]
  },
  "count": 5
}
```

## Tech Stack

- Java 21
- Spring Boot 4.0
- Oracle Database
- Apache Kafka
- JaCoCo (80% coverage)

## Architecture

The project follows hexagonal architecture:

```
src/main/java/com/sling/technology/
├── domain/           # Business logic and entities
├── application/      # Use cases and ports
└── infrastructure/   # Adapters (web, persistence, messaging)
```

## Running tests

```bash
./mvnw test
```

Coverage report is generated at `target/site/jacoco/index.html`.
