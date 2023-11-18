package tech.ada.tenthirty.ecommerce.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tech.ada.tenthirty.ecommerce.payload.ReservaRequest;
import tech.ada.tenthirty.ecommerce.service.ReservarItensService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservarItemConsumer {
    private final ObjectMapper objectMapper;
    private final ReservarItensService reservarItensService;

    @RabbitListener(queues = {"${negocio.fila.reserva.in}"})
    public void receber(Message message , Channel channel) throws IOException {
        String mensagem = new String(message.getBody());
        log.info("[ReservarItemConsumer] Mensagem recebida {}", mensagem);

        ReservaRequest reservaRequest = objectMapper.readValue(mensagem, ReservaRequest.class);
        reservarItensService.execute(reservaRequest);
        log.info("[ReservarItemConsumer] mensage consumida.");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
