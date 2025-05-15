package net.mom.todo.rabbit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mom.todo.dto.TodoDto;
import net.mom.todo.service.TodoService;
import net.mom.todo.service.impl.EmailServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TodoConsumer {

    private final TodoService todoService;
    private final EmailServiceImpl emailService;

    @RabbitListener(queues = "${rabbitmq.todo.queue}", containerFactory = "rabbitListenerContainerFactory")
    public void consumeTodo(TodoDto todoDto){
        log.info("Received from RabbitMQ: {}", todoDto);
        try {
            todoService.addTodo(todoDto);
            log.info("Todo saved successfully.");
            try {
                emailService.sendEmail(
                        todoDto.getEmail(),
                        "New task with id " + todoDto.getId() + " added!",
                        "Hi There, you added a new task: " + todoDto.getDescription() + " which you marked its completeness as " + todoDto.isCompleted() + "."
                );
                log.info("Email sent successfully for todo with id " + todoDto.getId());
            } catch (Exception e) {
                log.error("Failed to send email for todo with id " + todoDto.getId(), e);}
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            throw e;
        }
    }

}
