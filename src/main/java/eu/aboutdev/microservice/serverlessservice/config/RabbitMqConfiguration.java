package eu.aboutdev.microservice.serverlessservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class RabbitMqConfiguration {

    private final CachingConnectionFactory cachingConnectionFactory;

    public RabbitMqConfiguration(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Bean
    ContainerCustomizer<SimpleMessageListenerContainer> containerCustomizer() {
        return (container) -> container.setObservationEnabled(true);
    }

    @Bean
    Queue createQueue() {
        return new Queue("q.event-queue", true);
    }

    @Bean
    Exchange fanoutExchange() {
        return new FanoutExchange("test.exchange", true, false);
    }

    @Bean
    Binding queueBinding() {
        return new Binding("q.event-queue", Binding.DestinationType.QUEUE, "test.exchange", "", null);
    }

    /*@Bean
    public Queue createEventQueue() {

        return QueueBuilder.durable("q.event-queue")
                .withArgument("x-dead-letter-exchange","x.registration-failure")
                .withArgument("x-dead-letter-routing-key","fall-back")
                .build();
    }

    @Bean
    public Queue createBackendQueue() {

        return QueueBuilder.durable("q.backend-queue")
                .withArgument("x-dead-letter-exchange","x.registration-failure")
                .withArgument("x-dead-letter-routing-key","fall-back")
                .build();
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor(){
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(2000, 2.0, 100000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    @Bean
    public Declarables createDeadLetterSchema(){
        return new Declarables(
                new DirectExchange("x.registration-failure"),
                new Queue("q.fall-back-event-queue"),
                new Queue("q.fall-back-backend-queue"),
                new Binding("q.fall-back-event-queue", Binding.DestinationType.QUEUE,"x.registration-failure", "fall-back", null),
                new Binding("q.fall-back-backend-queue", Binding.DestinationType.QUEUE,"x.registration-failure", "fall-back", null)
        );
    }*/
}
