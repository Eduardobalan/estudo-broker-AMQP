package br.com.bln.estudocomsumidorapi.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ExchangesFanoutConsumerConfiguration {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("ExchangesFanout");
    }

    @Bean
    public Queue durableQueueFanoutExchange1() {
        return new Queue("queueFanout1", true, false, false);
    }

    @Bean
    public Queue autoDeleteQueueFanoutExchange2() {
        return new Queue("queueFanout2", true, false, false);
    }

    @Bean
    public Queue autoDeleteQueueFanoutExchange3() {
        return new Queue("queueFanout3", true, false, true);
    }

    @Bean
    public Queue autoDeleteQueueFanoutExchange4() {
        return new Queue("queueFanout4", true, false, true);
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, Queue durableQueueFanoutExchange1) {
        return BindingBuilder
                .bind(durableQueueFanoutExchange1)
                .to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueueFanoutExchange2) {
        return BindingBuilder
                .bind(autoDeleteQueueFanoutExchange2)
                .to(fanout);
    }

    @Bean
    public Binding binding3(FanoutExchange fanout, Queue autoDeleteQueueFanoutExchange3) {
        return BindingBuilder
                .bind(autoDeleteQueueFanoutExchange3)
                .to(fanout);
    }

    @Bean
    public Binding binding4(FanoutExchange fanout, Queue autoDeleteQueueFanoutExchange4) {
        return BindingBuilder
                .bind(autoDeleteQueueFanoutExchange4)
                .to(fanout);
    }
}