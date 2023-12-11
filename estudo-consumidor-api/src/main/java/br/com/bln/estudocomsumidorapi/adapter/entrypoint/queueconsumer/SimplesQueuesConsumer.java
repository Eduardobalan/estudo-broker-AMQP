package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.domain.Utils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SimplesQueuesConsumer {

    @RabbitListener(queues = "SimplesQueues", concurrency = "1")
    public void receive(Message message) throws InterruptedException, IOException {
        int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] iniciando trabalho: '" + body + "', que durará " + tempoDeTrabalho + "ms");
        Utils.trabalhar(tempoDeTrabalho);
    }
}