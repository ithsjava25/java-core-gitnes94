package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Shippable, Perishable {

    private final LocalDate expirationDate;
    private final BigDecimal weight;

    public FoodProduct(UUID id, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight){
        super(id, name, category, price);

        if (price.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        if (weight.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public LocalDate expirationDate(){
        return expirationDate;
    }

    @Override
    public Double weight(){
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost(){
        BigDecimal costPerKG = new BigDecimal("50.00");

        return weight.multiply(costPerKG).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String productDetails(){
        return String.format(
                "Food: %s, Expires: %s", name(), expirationDate().toString()
        );
    }
}
