# Helidon + React Template

Minimal `Helidon SE + Kotlin + React + Typescript + Gradle + Yarn + Vite` example.

## Prerequisites

- Installed `Gradle`
- Installed `Yarn`
- JDK11+ for `helidon`

## Build and run

```bash
gradle build --parallel
java -jar backend/build/libs/backend.jar
```

### Develop the frontend

- `cd frontend`
- `yarn dev` - starts vite in watch and hot reload mode

### Develop the backend

- `idea .`

## Test example todo app is running

- Open http://localhost:8080 in browser

### Helidon SE examples

```
curl -X GET http://localhost:8080/greet
{"message":"Hello World!"}
. . .

curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .
```
