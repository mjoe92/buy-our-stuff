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

    public ProductCategory(String name, String description, String department) {
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
