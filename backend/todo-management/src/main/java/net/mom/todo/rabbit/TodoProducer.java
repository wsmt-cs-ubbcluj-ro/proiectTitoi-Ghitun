package net.mom.todo.rabbit;

import net.mom.todo.dto.TodoDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TodoProducer {

    @Value("${rabbitmq.todo.exchange}")
    private String exchange;

    @Value("${rabbitmq.todo.routing.key}")
    private String routingKey;

    private final AmqpTemplate amqpTemplate;

    public TodoProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendTodo2(TodoDto todoDto) {
        amqpTemplate.convertAndSend(exchange, routingKey, todoDto);
        System.out.println("Sent to RabbitMQ: " + todoDto);
    }
}
