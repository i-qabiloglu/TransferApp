package green.mailSender.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private String notificationQ;
    private String notificationExchange;
    private String notificationRoutingKey;
    private String notificationDLQ;
    private String notificationExchangeDLQ;
    private String notificationRoutingKeyDLQ;

    public RabbitConfig(@Value("${queue.notification}") String notificationQ,
                        @Value("${queue.notification-dlq}") String notificationDLQ) {

        this.notificationQ             = notificationQ;
        this.notificationExchange      = notificationQ + "_Exchange";
        this.notificationRoutingKey    = notificationQ + "_RoutingKey";
        this.notificationDLQ           = notificationDLQ;
        this.notificationExchangeDLQ   = notificationDLQ + "_Exchange";
        this.notificationRoutingKeyDLQ = notificationDLQ + "_RoutingKey";
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(notificationQ)
                           .withArgument("x-dead-letter-exchange", notificationExchangeDLQ)
                           .withArgument("x-dead-letter-routing-key", notificationRoutingKeyDLQ)
                           .build();
    }

    @Bean
    public Queue dlq() {
        return QueueBuilder.durable(notificationDLQ)
                           .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(notificationExchange);
    }

    public DirectExchange deadLetterExchange() {
        return new DirectExchange(notificationExchangeDLQ);
    }

    public Binding binding() {
        return BindingBuilder.bind(queue())
                             .to(exchange())
                             .with(notificationRoutingKey);
    }

    public Binding dlqBinding() {
        return BindingBuilder.bind(dlq())
                             .to(deadLetterExchange())
                             .with(notificationRoutingKeyDLQ);
    }
}
