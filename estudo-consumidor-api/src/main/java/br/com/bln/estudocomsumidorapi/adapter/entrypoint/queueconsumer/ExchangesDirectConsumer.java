package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.domain.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesDirectConsumer {

    @RabbitListener(queues = "#{autoDeleteQueueDirectExchange1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, "autoDeleteQueueDirectExchange1");
    }

    @RabbitListener(queues = "#{autoDeleteQueueDirectExchange2.name}", concurrency = "1")
    public void receive2(String in) throws InterruptedException {
        receive(in, "autoDeleteQueueDirectExchange2");
    }

    public void receive(String in, String queueConsumo) throws InterruptedException {
        int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
        System.out.println(" [x] Queue " + queueConsumo + ", iniciando trabalho: '" + in + "', que durará " + tempoDeTrabalho + "ms");
        Utils.trabalhar(tempoDeTrabalho);
    }
}