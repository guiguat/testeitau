package com.itau.teste.transport.common.sns;

import com.itau.teste.core.common.outbound.events.Event;
import com.itau.teste.core.common.outbound.events.EventBroker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class SnsEventBrokerImplTest {
    private static final String TOPIC = "topic";
    private final NotificationMessagingTemplate snsTemplate = mock(NotificationMessagingTemplate.class);
    private final EventBroker eventBroker = new SnsEventBrokerImpl(snsTemplate, TOPIC);

    @Test
    void shouldPublish() {
        var event = new Event("eventname", "eventpayload");
        Mockito.doNothing().when(snsTemplate).convertAndSend(TOPIC, event);
        eventBroker.publish(event);
        Mockito.verify(snsTemplate, times(1)).convertAndSend(TOPIC, event);
    }
}
