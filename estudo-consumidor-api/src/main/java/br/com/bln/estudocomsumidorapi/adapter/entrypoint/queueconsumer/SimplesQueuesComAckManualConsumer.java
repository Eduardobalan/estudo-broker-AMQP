package br.com.bln.estudocomsumidorapi.adapter.entrypoint.queueconsumer;

import br.com.bln.estudocomsumidorapi.domain.Utils;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Configuration
public class SimplesQueuesComAckManualConsumer {


    @RabbitListener(queues = "SimplesQueuesComACK", ackMode = "MANUAL")
    public void receive(Message message, Channel channel) throws InterruptedException, IOException {
        String textoMensagem = new String(message.getBody(), StandardCharsets.UTF_8);

        try {
            int tempoDeTrabalho = Utils.gerarTempoDeTrabalhoAleatorio();
            log.info(" [x] iniciando trabalho: '" + textoMensagem + "', que durará " + tempoDeTrabalho + "ms");
            Utils.trabalhar(tempoDeTrabalho);

            if (isRejeitarMensagem()) {
                throw new RuntimeException("Mensagem rejeitada aleatoriamente");
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error(" [x] Erro no processamento da mensagem '" + textoMensagem + "', a mensgem será colocada novamente na fila.");

            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    public static Boolean isRejeitarMensagem() {
        double valorAleatorio = Math.random();
        double percentualDeErro = 0.2;

        return (valorAleatorio < percentualDeErro);
    }
}