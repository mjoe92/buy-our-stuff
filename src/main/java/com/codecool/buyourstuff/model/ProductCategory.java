package com.codecool.buyourstuff.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductCategory extends BaseModel {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String department;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductCategory(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("department") String department) {
        this.name = name;
        this.description = description;
        this.department = department;
    }

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
