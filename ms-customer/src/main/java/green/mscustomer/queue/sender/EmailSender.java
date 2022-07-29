package green.mscustomer.queue.sender;

import green.mscustomer.model.view.EmailView;
import green.mscustomer.queue.QueueHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    private final QueueHelper queueHelper;
    private final String notificationQueue;

    public EmailSender(@Value("${queue.notification}") String notificationQueue, QueueHelper queueHelper) {
        this.notificationQueue = notificationQueue;
        this.queueHelper       = queueHelper;
    }

    public void sendNotification(EmailView emailView) {
        queueHelper.sendToQueue(notificationQueue, emailView);
    }
}
