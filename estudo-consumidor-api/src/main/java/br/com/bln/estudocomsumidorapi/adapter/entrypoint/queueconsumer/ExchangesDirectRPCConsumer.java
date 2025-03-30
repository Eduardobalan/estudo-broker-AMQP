package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.application.config.ExchangesDirectRPCConsumerConfiguration;
import br.com.bln.estudocomsumidorapi.domain.Utils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangesDirectRPCConsumer {

    @RabbitListener(queues = ExchangesDirectRPCConsumerConfiguration.REQUEST_QUEUE)
    public String processRequest(String message) throws InterruptedException {

        int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
        System.out.println(" [x] iniciando trabalho '" + message + "', que durará " + tempoDeTrabalho + "ms");
        Utils.trabalhar(tempoDeTrabalho);

        return "Resposta do trabalho: '" + message + "'";
    }
}