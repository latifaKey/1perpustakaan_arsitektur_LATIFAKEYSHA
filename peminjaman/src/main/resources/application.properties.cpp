
server.port=8084
spring.application.name=peminjaman
spring.datasource.url=jdbc:h2:mem:peminjamandb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Custom RabbitMQ Properties
app.rabbitmq-peminjaman.queue=peminjaman.queue
app.rabbitmq-peminjaman.exchange=peminjaman.exchange
app.rabbitmq-peminjaman.routing-key=peminjaman.routing.key
