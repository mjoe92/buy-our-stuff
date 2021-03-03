package com.codecool.buyourstuff.model;

import lombok.Getter;

@Getter
public class Supplier extends BaseModel {
    private final String name;
    private final String description;

    public Supplier(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "description: %4$s}",
                getClass().getSimpleName(),
                id,
                name,
                description
        );
    }

    public String getName() {
        return name;
    }
}
