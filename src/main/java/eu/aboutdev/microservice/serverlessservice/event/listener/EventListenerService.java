package eu.aboutdev.microservice.serverlessservice.event.listener;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventListenerService {

    private final RabbitTemplate rabbitTemplate;

    private final Tracer tracer;

    private final ObservationRegistry observationRegistry;

    @Value("${rabbit.backend.queue}")
    private String eventQueue;

    public void send(final String eventId) {
        rabbitTemplate.convertAndSend(eventQueue, eventId);
    }

    @RabbitListener(queues = {"q.event-queue"})
    public void onEvent(final String eventId) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("EventId Received: {}", eventId);
            log.info("<ACCEPTANCE_TEST> <TRACE:{}> Hello from consumer", this.tracer.currentSpan().context().traceId());
            send(eventId);
        });
    }

    @Bean
    public Function<String, Boolean> retryCall() {
        return value -> true;
    }
}
