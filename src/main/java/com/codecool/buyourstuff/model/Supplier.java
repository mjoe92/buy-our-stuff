package com.codecool.buyourstuff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Supplier extends BaseModel {
    private final String name;
    private final String description;

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
}
