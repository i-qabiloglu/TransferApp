package green.mscustomer.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class QueueHelper {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void sendToQueue(String queue, Object value) {
        log.info("QueueHelper.sendToQueue.start :{}", queue);
        try {
            amqpTemplate.convertAndSend(queue, objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException ex) {
            log.error("QueueHelper.sendToQueue.error: {}", ex.getMessage());
        }
        log.info("QueueHelper.sendToQueue.end");
    }
}
