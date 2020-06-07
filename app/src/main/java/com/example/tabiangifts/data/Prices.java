package com.example.tabiangifts.data;

import com.example.tabiangifts.models.Product;

import java.math.BigDecimal;
import java.util.HashMap;

public class Prices {

    private static final HashMap<String, BigDecimal> PRICES;

    static {
        PRICES = new HashMap<>();
        Products products = new Products();
        for (Product product : products.PRODUCTS) {
            PRICES.put(String.valueOf(product.getSerialNumber()), product.getPrice());
        }
    }

    public static HashMap<String, BigDecimal> getPrices() {
        return PRICES;
    }
}
