package br.com.bln.estudoprodutorapi.adapter.gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "bln.simplesqueuescomack.enabled", havingValue = "true", matchIfMissing = false)
public class SimplesQueuesComAckManualProductorSend {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        int id = count.incrementAndGet();
        String textoEnvio = String.format("SimplesQueuesComAckManualProductorSend, msg id=%s", id);
        this.template.convertAndSend(
                queue.getName(),
                MessageBuilder
                        .withBody(textoEnvio.getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                        .setMessageId(String.valueOf(id))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .setHeader("MyHeader", "MyValueHeader")
                        .build()
        );
        log.info(textoEnvio);
    }
}
