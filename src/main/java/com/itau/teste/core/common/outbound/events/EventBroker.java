package com.itau.teste.core.common.outbound.events;


public interface EventBroker {
    void publish(Event event);
}
