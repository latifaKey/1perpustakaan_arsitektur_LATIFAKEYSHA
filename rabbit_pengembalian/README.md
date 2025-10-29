# Rabbit Pengembalian Microservice

Microservice untuk mengelola pengembalian buku dengan integrasi RabbitMQ.

## Fitur
- CRUD operations untuk pengembalian
- Integrasi dengan RabbitMQ untuk message queuing
- Service discovery dengan Eureka
- Database H2 in-memory
- RESTful API endpoints

## Endpoints
- `GET /api/rabbit-pengembalian` - Get all pengembalian
- `GET /api/rabbit-pengembalian/{id}` - Get pengembalian by ID
- `POST /api/rabbit-pengembalian` - Create new pengembalian
- `DELETE /api/rabbit-pengembalian/{id}` - Delete pengembalian
- `POST /api/rabbit-pengembalian/send-message/{action}` - Send custom message to RabbitMQ

## Konfigurasi
- Port: 8087
- Database: H2 in-memory
- RabbitMQ: localhost:5672
- Eureka: localhost:8761

## Cara Menjalankan
1. Pastikan Eureka Server berjalan di port 8761
2. Pastikan RabbitMQ berjalan di localhost:5672
3. Jalankan aplikasi:
   ```
   ./mvnw.cmd spring-boot:run
   ```

## RabbitMQ Configuration
- Queue: pengembalian.queue
- Exchange: pengembalian.exchange
- Routing Key: pengembalian.routing.key