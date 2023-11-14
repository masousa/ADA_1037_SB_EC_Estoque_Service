package tech.ada.tenthirty.ecommerce.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.tenthirty.ecommerce.exception.NotFoundException;
import tech.ada.tenthirty.ecommerce.model.Item;
import tech.ada.tenthirty.ecommerce.model.ItemReservado;
import tech.ada.tenthirty.ecommerce.payload.response.ItemResponse;
import tech.ada.tenthirty.ecommerce.repository.ItemRepository;
import tech.ada.tenthirty.ecommerce.repository.ItemReservadoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarQuantidadeItemServiceTest {

    @InjectMocks
    private ConsultarQuantidadeItemService consultarQuantidadeItemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemReservadoRepository itemReservadoRepository;


    @Test
    void shouldReturnACalculationOfAnItemReservedAndTheQuantityStored(){
        Item item = mock(Item.class);
        when(item.getQuantidadeEstoque()).thenReturn(12);
        ItemReservado itemReservado = mock(ItemReservado.class);
        when(itemReservado.getQuantidadeReservada()).thenReturn(10);

        when(itemRepository.findBySku(anyString()))
                .thenReturn(Optional.of(item));

        when(itemReservadoRepository.findBySku(anyString()))
                .thenReturn(List.of(itemReservado));

        ItemResponse response = consultarQuantidadeItemService.execute("1234");
        assertEquals(2, response.getQuantidade());
    }

    @Test
    void shouldReturnTheQuantityStored(){
        Item item = mock(Item.class);
        when(item.getQuantidadeEstoque()).thenReturn(12);

        when(itemRepository.findBySku(anyString()))
                .thenReturn(Optional.of(item));

        when(itemReservadoRepository.findBySku(anyString()))
                .thenReturn(List.of());

        ItemResponse response = consultarQuantidadeItemService.execute("1234");
        assertEquals(12, response.getQuantidade());
    }

    @Test
    void shouldReturnANotFoundError(){

       when(itemRepository.findBySku(anyString()))
                .thenReturn(Optional.empty());

       assertThrows(NotFoundException.class, ()-> consultarQuantidadeItemService.execute("1234"));

    }




}
