package com.itau.teste.transport.common.sns;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("sns")
public class SnsEventBrokerImpl implements EventBroker {
    private final NotificationMessagingTemplate template;
    private final String TOPIC;
    private static final Logger logger = LoggerFactory.getLogger(EventBroker.class);

    public SnsEventBrokerImpl(
        NotificationMessagingTemplate template,
        @Value("${app.aws.topic}") String topic
    ) {
        this.template = template;
        TOPIC = topic;
    }

    @Override
    public void publish(Event event) {
        template.convertAndSend(TOPIC, event);
        logger.info("event sent via SNS: " + event);
    }
}
