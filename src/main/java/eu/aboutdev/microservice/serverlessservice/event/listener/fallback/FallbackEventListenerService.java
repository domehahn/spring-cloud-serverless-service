package eu.aboutdev.microservice.serverlessservice.event.listener.fallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FallbackEventListenerService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.fail.queue}")
    private String eventQueue;

    public void send(final String eventId) {
        rabbitTemplate.convertAndSend(eventQueue, eventId);
    }

    //@RabbitListener(queues = {"q.fall-back-event-queue"})
    public void onEventFailure(final String eventId) {
        log.error("Failure: EventId Received: {}", eventId);
        log.error("Publish to fail-service.");
        send(eventId);
    }
}
