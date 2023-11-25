package tech.ada.tenthirty.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ada.tenthirty.ecommerce.model.Item;
import tech.ada.tenthirty.ecommerce.payload.ItemRequest;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.repository.ItemRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdicionarProdutoService {
    private final ItemRepository itemRepository;
    public ItemResponse execute(ItemRequest itemRequest){
        Item item = itemRepository.findBySku(itemRequest.getSkuId()).orElse(createEmptyItem(itemRequest));
        item.setQuantidadeEstoque(item.getQuantidadeEstoque()+itemRequest.getQuantidade());
        itemRepository.save(item);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setQuantidade(itemRequest.getQuantidade());
        itemResponse.setSkuId(itemRequest.getSkuId());
        return itemResponse;
    }

    private Item createEmptyItem(ItemRequest itemRequest) {
        Item item = new Item();
        item.setSku(itemRequest.getSkuId());
        item.setQuantidadeEstoque(0);
        item.setValorUnitario(BigDecimal.ZERO);
        item.setIdentificador(UUID.randomUUID().toString());
        return item;
    }
}
