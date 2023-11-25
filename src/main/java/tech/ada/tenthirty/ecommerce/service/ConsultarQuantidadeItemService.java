package tech.ada.tenthirty.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tech.ada.tenthirty.ecommerce.exception.NotFoundException;
import tech.ada.tenthirty.ecommerce.model.Item;
import tech.ada.tenthirty.ecommerce.model.ItemReservado;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.repository.ItemRepository;
import tech.ada.tenthirty.ecommerce.repository.ItemReservadoRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultarQuantidadeItemService {
    private final ItemRepository itemRepository;
    private final ItemReservadoRepository itemReservadoRepository;

    @Cacheable(cacheNames = "cQuantidadeEstoque", key = "#sku")
    public ItemResponse execute(String sku){
        log.info("Consultando quantidade de itens do sku {}", sku);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setSkuId(sku);
        int quantidadeTotal = itemRepository.findBySku(sku)
                .orElseThrow(() -> new NotFoundException(sku, Item.class))
                .getQuantidadeEstoque();
        int quantidadesReservadas = itemReservadoRepository.findBySku(sku)
                .stream().mapToInt(ItemReservado::getQuantidadeReservada)
                .sum();
        itemResponse.setQuantidade(quantidadeTotal-quantidadesReservadas);
        return itemResponse;
    }
}
