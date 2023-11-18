package tech.ada.tenthirty.ecommerce.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservaRequest {
    private String compraId;
    private LocalDateTime dataCompra;
    @JsonProperty("items")
    private List<ItemRequest> itens;
}
