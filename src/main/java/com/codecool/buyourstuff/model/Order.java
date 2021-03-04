package com.codecool.buyourstuff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Order {

    private final Cart cart;
    private final ShippingInfo shippingInfo;

    @Override
    public String toString() {
        return String.format("%1$s={shippingInfo: %2$s, cart: %3$s}",
                getClass().getSimpleName(),
                shippingInfo,
                cart
        );
    }
}
