package com.codecool.buyourstuff.model;

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

    @Override
    public String toString() {
        return String.format("%1$s={quantity: %2$d, product: %3$s}",
                getClass().getSimpleName(),
                quantity,
                product
        );
    }
}
