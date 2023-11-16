package tech.ada.tenthirty.ecommerce.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.tenthirty.ecommerce.model.Item;
import tech.ada.tenthirty.ecommerce.model.ItemReservado;
import tech.ada.tenthirty.ecommerce.payload.ItemRequest;
import tech.ada.tenthirty.ecommerce.payload.ReservaRequest;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.repository.ItemRepository;
import tech.ada.tenthirty.ecommerce.repository.ItemReservadoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservarItensServiceTest {

    @InjectMocks
    private ReservarItensService reservarItensService;

    @Mock
    private ItemReservadoRepository itemReservadoRepository;

    @Mock
    private ConsultarQuantidadeItemService consultarQuantidadeItemService;

    @Test
    void shouldReserveAnItem(){
        ItemResponse itemResponse = generateAnItemResponse();
        when(consultarQuantidadeItemService.execute(anyString()))
                .thenReturn(itemResponse);
        reservarItensService.execute(generateReservaRequest());

        verify(itemReservadoRepository, atLeastOnce())
                .save(itemReservadoRepository.save(ArgumentMatchers.any(ItemReservado.class)));
    }

    @Test
    public void shouldNotReserveTheItem(){
        ItemResponse itemResponse = generateAnItemResponse();
        itemResponse.setQuantidade(9);
        when(consultarQuantidadeItemService.execute(anyString()))
                .thenReturn(itemResponse);
        reservarItensService.execute(generateReservaRequest());

        verify(itemReservadoRepository, never())
                .save(itemReservadoRepository.save(ArgumentMatchers.any(ItemReservado.class)));
    }

    private ItemResponse generateAnItemResponse() {
        ItemResponse item = new ItemResponse();
        item.setSkuId("9788");
        item.setQuantidade(50);
        return item;
    }

    private ReservaRequest generateReservaRequest(){
        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setCompraId(UUID.randomUUID().toString());
        reservaRequest.setDataCompra(LocalDateTime.now());

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setSkuId("9788");
        itemRequest.setQuantidade(10);
        reservaRequest.setItens(List.of(itemRequest));
        return reservaRequest;
    }
}
