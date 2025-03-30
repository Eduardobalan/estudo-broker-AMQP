package br.com.bln.estudoprodutorapi.adapter.gateway;


import br.com.bln.estudoprodutorapi.application.config.ExchangesDirectRPCProductorConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@ConditionalOnProperty(name = "bln.exchangesDirectRPC.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesDirectRPCSend {

    @Autowired
    private RabbitTemplate template;

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 20000, initialDelay = 500)
    public void send() {
        callRemoteService(String.valueOf(count.incrementAndGet()));
    }

    public String callRemoteService(String message) {
        System.out.println("Enviando trabalho: " + message);

        // Define um timeout para evitar bloqueios eternos
        template.setReplyTimeout(10000);

        // Envia a mensagem e aguarda a resposta
        String response = (String) template.convertSendAndReceive(
                ExchangesDirectRPCProductorConfiguration.EXCHANGE_NAME,
                ExchangesDirectRPCProductorConfiguration.ROUTING_KEY,
                message
        );

        System.out.println("Resposta do trabalho: " + response);
        return response;
    }
}
