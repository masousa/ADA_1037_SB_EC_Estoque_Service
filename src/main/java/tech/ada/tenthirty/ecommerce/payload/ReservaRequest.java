package tech.ada.tenthirty.ecommerce.payload;

import lombok.Data;

import java.util.List;

@Data
public class ReservaRequest {
    private List<ItemRequest> itens;
}
