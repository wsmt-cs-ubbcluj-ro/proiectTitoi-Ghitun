package net.mom.todo.rabbit;

import net.mom.todo.dto.TodoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class DLQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(DLQConsumer.class);

    @RabbitListener(queues = "${rabbitmq.todo.dlq}")
    public void handleDeadLetter(TodoDto todoDto) {
        logger.error("DLQ message received: {}", todoDto);
    }
}
