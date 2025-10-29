package com.key.pengembalian;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${app.rabbit-pengembalian.queue}")
  private String queueName;

  @Value("${app.rabbit-pengembalian.exchange}")
  private String exchange;

  @Value("${app.rabbit-pengembalian.routing-key}")
  private String routingKey;

  @Bean
  public Queue queue() {
    return new Queue(queueName, true, false, false);
  }

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(exchange);
  }

  @Bean
  public Binding binding(Queue queue, DirectExchange exchange){
    return BindingBuilder.bind(queue)
              .to(exchange)
              .with(routingKey);
  }

  @Bean
  public MessageConverter jMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }

}
