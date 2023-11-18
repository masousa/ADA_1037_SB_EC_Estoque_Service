package tech.ada.tenthirty.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ada.tenthirty.ecommerce.model.ItemReservado;
import tech.ada.tenthirty.ecommerce.payload.ItemRequest;
import tech.ada.tenthirty.ecommerce.payload.ReservaRequest;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.repository.ItemRepository;
import tech.ada.tenthirty.ecommerce.repository.ItemReservadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservarItensService {

    private final ItemReservadoRepository itemReservadoRepository;
    private final ConsultarQuantidadeItemService consultarQuantidadeItemService;
    public void execute(ReservaRequest reservaRequest){
        List<String> skuIndisponiveis = new ArrayList<>();
        Map<String,Integer> items = reservaRequest.getItens()
                .stream().map(ItemRequest::getSkuId)
                .map(consultarQuantidadeItemService::execute)
                .collect(Collectors.toMap(ItemRequest::getSkuId, ItemRequest::getQuantidade));
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            List<ItemRequest> itemRequestList
                    = reservaRequest.getItens().stream()
                    .filter(itemRequest -> itemRequest.getSkuId().equals(entry.getKey())).toList();
            int quantidadeSolicitada = itemRequestList.stream().mapToInt(ItemRequest::getQuantidade)
                    .sum();
            if(quantidadeSolicitada <= entry.getValue()){
                ItemReservado itemReservado = new ItemReservado();
                itemReservado.setCompraId(reservaRequest.getCompraId());
                itemReservado.setSku(entry.getKey());
                itemReservado.setIdentificador(UUID.randomUUID().toString());
                itemReservado.setQuantidadeReservada(quantidadeSolicitada);
                itemReservadoRepository.save(itemReservado);
            }else{
                skuIndisponiveis.add(entry.getKey());
            }
            if(skuIndisponiveis.isEmpty()){
                //TODO proceder com o pagamento
            }
            //TODO enviar notificação de erro quando houver itens indisponiveis
        }
    }
}
