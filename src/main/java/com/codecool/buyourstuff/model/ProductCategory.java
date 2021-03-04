package com.codecool.buyourstuff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductCategory extends BaseModel {
    private String name;
    private String description;
    private String department;

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "department: %4$s, " +
                        "description: %5$s}",
                getClass().getSimpleName(),
                id,
                name,
                department,
                description
        );
    }

}
