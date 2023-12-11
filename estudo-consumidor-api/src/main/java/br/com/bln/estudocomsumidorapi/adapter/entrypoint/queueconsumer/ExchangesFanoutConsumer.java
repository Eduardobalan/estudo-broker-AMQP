package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.domain.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesFanoutConsumer {

    @RabbitListener(queues = "#{durableQueueFanoutExchange1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, "durableQueueFanoutExchange1");
    }

    @RabbitListener(queues = "#{autoDeleteQueueFanoutExchange2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, "autoDeleteQueueFanoutExchange2");
    }

    @RabbitListener(queues = "#{autoDeleteQueueFanoutExchange3.name}")
    public void receive3(String in) throws InterruptedException {
        receive(in, "autoDeleteQueueFanoutExchange3");
    }

    @RabbitListener(queues = "#{autoDeleteQueueFanoutExchange4.name}")
    public void receive4(String in) throws InterruptedException {
        receive(in, "autoDeleteQueueFanoutExchange4");
    }

    public void receive(String in, String queueConsumo) throws InterruptedException {
        int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
        System.out.println(" [x] Leitura da Queue " + queueConsumo + ", iniciando trabalho: '" + in + "', que durará " + tempoDeTrabalho + "ms");
        Utils.trabalhar(tempoDeTrabalho);
    }
}