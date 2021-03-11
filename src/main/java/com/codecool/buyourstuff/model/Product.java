package com.codecool.buyourstuff.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseModel {

    @NonNull
    private String name;
    @NonNull
    private String description;
    private BigDecimal defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;

    public Product(String name, String description, BigDecimal defaultPrice, String currencyString, ProductCategory productCategory, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
    }

    public BigDecimal getDefaultPrice(int decimals) {
        return defaultPrice.setScale(decimals, RoundingMode.HALF_DOWN);
    }
    @JsonIgnore
    public String getPrice() {
        return getDefaultPrice(2) + " " + defaultCurrency.toString();
    }
    @JsonIgnore
    public void setPrice(BigDecimal price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "id: %2$d, " +
                        "name: %3$s, " +
                        "defaultPrice: %4$s, " +
                        "defaultCurrency: %5$s, " +
                        "productCategory: %6$s, " +
                        "supplier: %7$s}",
                getClass().getSimpleName(),
                id,
                name,
                getPrice(),
                defaultCurrency,
                productCategory.getName(),
                supplier.getName()
        );
    }
}
