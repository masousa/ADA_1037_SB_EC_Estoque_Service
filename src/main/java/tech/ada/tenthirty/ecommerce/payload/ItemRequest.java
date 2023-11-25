package tech.ada.tenthirty.ecommerce.payload;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemRequest implements Serializable {
    private String skuId;
    private int quantidade;
}
