package br.com.bln.estudoprodutorapi.adapter.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "bln.exchangestopic.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesTopicProductorSend {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    AtomicInteger count = new AtomicInteger(0);

    private static final String[] keys = {
            "quick.orange.rabbit",
            "lazy.orange.elephant",
            "quick.orange.fox",
            "lazy.brown.fox",
            "lazy.pink.rabbit",
            "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String key = gerarKeysAleatorias();
        String message = String.format("ExchangesTopicProductorSend, key %s, msg id= %s", key, count.incrementAndGet());
        template.convertAndSend(topic.getName(), key, message);
        log.info(message);
    }

    public static String gerarKeysAleatorias() {
        double valorAleatorio = Math.random(); // Gera um valor de ponto flutuante entre 0.0 (inclusive) e 1.0 (exclusivo)

        int min = 0;
        int max = 5;

        return keys[min + (int) (valorAleatorio * ((max - min) + 1))];

    }
}
