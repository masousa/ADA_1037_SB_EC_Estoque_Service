package tech.ada.tenthirty.ecommerce.payload;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservaRequest {
    private String compraId;
    private LocalDateTime dataCompra;
    private List<ItemRequest> itens;
}
