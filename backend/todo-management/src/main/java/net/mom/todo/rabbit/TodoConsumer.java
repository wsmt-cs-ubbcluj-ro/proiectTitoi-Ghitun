package net.mom.todo.rabbit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mom.todo.dto.TodoDto;
import net.mom.todo.service.TodoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TodoConsumer {

    private final TodoService todoService;


    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    @RabbitListener(queues = "${rabbitmq.todo.queue}")
    public void consumeTodo(TodoDto todoDto){
        log.info("Received from RabbitMQ: {}", todoDto);
        try {
            
            todoService.addTodo(todoDto);
            log.info("Todo saved successfully.");
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            throw e; // triggers retry
        }
    }

}
