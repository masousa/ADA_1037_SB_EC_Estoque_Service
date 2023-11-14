package tech.ada.tenthirty.ecommerce.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class EstoqueResponse {
    private String idReserva;
    List<String> itemsReservados;
}
