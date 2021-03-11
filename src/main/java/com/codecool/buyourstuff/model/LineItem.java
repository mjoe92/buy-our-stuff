package com.codecool.buyourstuff.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LineItem extends BaseModel {

    private final Product product;
    private final int cartId;
    private int quantity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public LineItem(Product product,
                    @JsonProperty("cartId") int cartId,
                    @JsonProperty("quantity") int quantity) {
        this.product = product;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%1$s={quantity: %2$d, product: %3$s}",
                getClass().getSimpleName(),
                quantity,
                product
        );
    }
}
