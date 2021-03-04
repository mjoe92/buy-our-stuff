package com.codecool.buyourstuff.dao;

enum Security {

    USER_NAME("postgres"),
    PASSWORD(""),
    DB_NAME("buy_our_stuff");

    Security(String pd) {
        this.pd = pd;
    }

    private final String pd;

    public String getPd() {
        return pd;
    }
}
