package com.codecool.buyourstuff.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Order {

    private final Cart cart;
    private final ShippingInfo shippingInfo;

    public Order(Cart cart, ShippingInfo shippingInfo) {
        this.cart = cart;
        this.shippingInfo = shippingInfo;
    }

    @Override
    public String toString() {
        return String.format("%1$s={shippingInfo: %2$s, cart: %3$s}",
                getClass().getSimpleName(),
                shippingInfo,
                cart
        );
    }
}
