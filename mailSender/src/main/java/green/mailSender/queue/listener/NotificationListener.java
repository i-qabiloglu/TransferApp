package green.mailSender.queue.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import green.mailSender.model.view.EmailView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${queue.notification}")
    public void listenMessage(String message) {
        log.info("NotificationListener.listenMessage.start: {}", message);

        try {
            EmailView email = objectMapper.readValue(message, EmailView.class);
            System.out.println("Result: id- " + email.getId() + " " + email.getMessage());
        } catch (Exception ex) {
            log.error("NotificationListener.listenMessage.error: {}", ex.getMessage());
        }
        log.info("NotificationListener.listenMessage.end");
    }
}
