package com.key.queryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    // Exchanges
    @Bean
    public TopicExchange anggotaExchange() {
        return new TopicExchange("anggota.exchange");
    }
    
    @Bean
    public TopicExchange bukuExchange() {
        return new TopicExchange("buku.exchange");
    }
    
    @Bean
    public TopicExchange peminjamanExchange() {
        return new TopicExchange("peminjaman.exchange");
    }
    
    // Queues for Anggota
    @Bean
    public Queue anggotaCreatedQueue() {
        return QueueBuilder.durable("anggota.created.queue").build();
    }
    
    @Bean
    public Queue anggotaUpdatedQueue() {
        return QueueBuilder.durable("anggota.updated.queue").build();
    }
    
    @Bean
    public Queue anggotaDeletedQueue() {
        return QueueBuilder.durable("anggota.deleted.queue").build();
    }
    
    // Queues for Buku
    @Bean
    public Queue bukuCreatedQueue() {
        return QueueBuilder.durable("buku.created.queue").build();
    }
    
    @Bean
    public Queue bukuUpdatedQueue() {
        return QueueBuilder.durable("buku.updated.queue").build();
    }
    
    // Queues for Peminjaman
    @Bean
    public Queue peminjamanCreatedQueue() {
        return QueueBuilder.durable("peminjaman.created.queue").build();
    }
    
    @Bean
    public Queue peminjamanReturnedQueue() {
        return QueueBuilder.durable("peminjaman.returned.queue").build();
    }
    
    // Bindings for Anggota
    @Bean
    public Binding anggotaCreatedBinding() {
        return BindingBuilder.bind(anggotaCreatedQueue())
                           .to(anggotaExchange())
                           .with("anggota.created");
    }
    
    @Bean
    public Binding anggotaUpdatedBinding() {
        return BindingBuilder.bind(anggotaUpdatedQueue())
                           .to(anggotaExchange())
                           .with("anggota.updated");
    }
    
    @Bean
    public Binding anggotaDeletedBinding() {
        return BindingBuilder.bind(anggotaDeletedQueue())
                           .to(anggotaExchange())
                           .with("anggota.deleted");
    }
    
    // Bindings for Buku
    @Bean
    public Binding bukuCreatedBinding() {
        return BindingBuilder.bind(bukuCreatedQueue())
                           .to(bukuExchange())
                           .with("buku.created");
    }
    
    @Bean
    public Binding bukuUpdatedBinding() {
        return BindingBuilder.bind(bukuUpdatedQueue())
                           .to(bukuExchange())
                           .with("buku.updated");
    }
    
    // Bindings for Peminjaman
    @Bean
    public Binding peminjamanCreatedBinding() {
        return BindingBuilder.bind(peminjamanCreatedQueue())
                           .to(peminjamanExchange())
                           .with("peminjaman.created");
    }
    
    @Bean
    public Binding peminjamanReturnedBinding() {
        return BindingBuilder.bind(peminjamanReturnedQueue())
                           .to(peminjamanExchange())
                           .with("peminjaman.returned");
    }
    
    // Message Converter
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    // RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
    
    // Listener Container Factory
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}