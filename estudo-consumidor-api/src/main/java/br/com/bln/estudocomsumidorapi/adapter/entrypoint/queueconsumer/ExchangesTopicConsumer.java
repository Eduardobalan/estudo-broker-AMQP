package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.domain.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesTopicConsumer {

    @RabbitListener(queues = "#{queueTopic1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{queueTopic2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws InterruptedException {
        int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
        System.out.println(" [x] instance " + receiver + " iniciando trabalho: '" + in + "', que durará " + tempoDeTrabalho + "ms");
        Utils.trabalhar(tempoDeTrabalho);
    }
}