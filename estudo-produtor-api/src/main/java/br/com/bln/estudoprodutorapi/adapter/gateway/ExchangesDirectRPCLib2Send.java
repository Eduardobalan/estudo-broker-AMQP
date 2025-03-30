package br.com.bln.estudoprodutorapi.adapter.gateway;


import br.com.bln.estudoprodutorapi.application.config.ExchangesDirectRPCProductorConfiguration;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "bln.exchangesDirectRPCLib2.enabled", havingValue = "true", matchIfMissing = false)
public class ExchangesDirectRPCLib2Send {

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 20000, initialDelay = 500)
    public void send() {
        try {
            String resposta = callRemoteService("Lib2-"+ count.incrementAndGet());
            System.out.println("Resposta do trabalho: " + resposta);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String callRemoteService(String message) throws IOException, InterruptedException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            // Declaração da fila de resposta temporária
            String replyQueueName = channel.queueDeclare().getQueue();

            // Gera um correlationId único para identificar a resposta
            String correlationId = UUID.randomUUID().toString();

            // Configura as propriedades da mensagem
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    .correlationId(correlationId)  // Associa a resposta à requisição
                    .replyTo(replyQueueName)       // Define a fila de resposta
                    .build();

            // Publica a mensagem na fila RPC
            channel.basicPublish(
                    ExchangesDirectRPCProductorConfiguration.EXCHANGE_NAME,
                    ExchangesDirectRPCProductorConfiguration.ROUTING_KEY,
                    props,
                    message.getBytes(StandardCharsets.UTF_8));

            System.out.println("Enviando trabalho: " + message);

            // Aguarda a resposta
            final ArrayBlockingQueue<String> response = new ArrayBlockingQueue<>(1);
            // Adiciona uma configuração de timeout


            // Cria um consumidor para a fila de resposta
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                    response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            };

            channel.basicConsume(replyQueueName, true, deliverCallback, consumerTag -> {
            });

            // Bloqueia até receber a resposta
            return response.poll(20, TimeUnit.SECONDS);
        }
    }

}
