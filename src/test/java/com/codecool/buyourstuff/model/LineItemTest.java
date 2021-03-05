package com.codecool.buyourstuff.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LineItemTest {

    final ProductCategory PRODCAT_DEFAULT = new ProductCategory("ProductCat1", "ProductCat description", "Dept1");
    final Supplier SUPPL_DEFAULT = new Supplier("Supplier1", "Supplier description");
    final Product PROD_DEFAULT = new Product("Product1", "Description text", new BigDecimal(500), "USD", PRODCAT_DEFAULT, SUPPL_DEFAULT);

    @Test
    void zeroQuantityThrows() {
        assertThrows(IllegalStateException.class, () -> new LineItem(PROD_DEFAULT, 12345, 0));
    }

    @Test
    void negativeQuantityThrows() {
        assertThrows(IllegalStateException.class, () -> new LineItem(PROD_DEFAULT, 12345, -7));
    }

}