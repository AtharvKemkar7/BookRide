# RideShare Microservices Platform

Production-style ride booking system with six Spring Boot 3 services, a Spring Cloud Gateway, and an Angular 18 passenger/driver console.

## Services

| Service | Port | Database | Responsibility |
| --- | --- | --- | --- |
| api-gateway | 8060 | - | Route `/api/auth/**`, `/api/passengers/**`, `/api/drivers/**`, `/api/trips/**`, `/api/notifications/**` |
| auth-service | 8081 | ride_auth_db | Registration, login, JWT issuance |
| passenger-service | 8082 | ride_passenger_db | Passenger profiles |
| driver-service | 8083 | ride_driver_db | Fleet, availability, location |
| trip-service | 8084 | ride_trip_db | Booking, fare quotes, status machine, Feign alerts |
| notification-service | 8085 | ride_notification_db | Mock email/SMS/push via `/api/notifications/send` |
| frontend | 4200 | - | Angular 18 console with JWT interceptor and live toasts |

Local development uses H2 in-memory databases that emulate MariaDB. Switch to MariaDB with `--spring.profiles.active=mariadb`.

## Demo accounts

- Passenger: `ava.chen@rideshare.io` / `Passenger1!`
- Driver: `marcus.hale@rideshare.io` / `Driver1!`
- Admin: `jordan.reid@rideshare.io` / `Admin1!`

## Run locally

```bash
# Build every backend module
mvn -q -DskipTests package

# Start each service in its own terminal
mvn -pl api-gateway spring-boot:run
mvn -pl auth-service spring-boot:run
mvn -pl passenger-service spring-boot:run
mvn -pl driver-service spring-boot:run
mvn -pl trip-service spring-boot:run
mvn -pl notification-service spring-boot:run

# Frontend (proxies /api to gateway 8060)
cd frontend
npm start
```

When a trip moves to `REQUESTED`, `IN_PROGRESS` or `COMPLETED`, trip-service asynchronously calls notification-service so the booking flow is never blocked.
