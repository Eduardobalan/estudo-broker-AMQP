package br.com.bln.estudoprodutorapi.adapter.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "bln.exchangesfanout.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesFanoutProductorSend {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanout;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = String.format("ExchangesFanoutProductorSend, msg id= %s", count.incrementAndGet());
        template.convertAndSend(fanout.getName(), "", message);
        log.info(message);
    }
}
