package tech.ada.tenthirty.ecommerce.payload;

import lombok.Data;

@Data
public class ItemRequest {
    private String skuId;
    private int quantidade;
}
