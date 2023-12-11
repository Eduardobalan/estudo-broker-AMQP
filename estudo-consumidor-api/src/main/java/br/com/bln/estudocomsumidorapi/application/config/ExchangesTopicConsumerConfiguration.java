package br.com.bln.estudocomsumidorapi.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ExchangesTopicConsumerConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("ExchangeTopic");
    }

    @Bean
    public Queue queueTopic1() {
        return new Queue("queueTopic1", false, false, true);
    }

    @Bean
    public Queue queueTopic2() {
        return new Queue("queueTopic2", false, false, true);
    }

    @Bean
    public Binding bindingTopic1(TopicExchange topic, Queue queueTopic1) {
        return BindingBuilder
                .bind(queueTopic1)
                .to(topic)
                .with("*.orange.*");
    }

    @Bean
    public Binding bindingTopic2(TopicExchange topic, Queue queueTopic1) {
        return BindingBuilder
                .bind(queueTopic1)
                .to(topic)
                .with("*.*.rabbit");
    }

    @Bean
    public Binding bindingTopic3(TopicExchange topic, Queue queueTopic2) {
        return BindingBuilder
                .bind(queueTopic2)
                .to(topic)
                .with("lazy.#");
    }
}