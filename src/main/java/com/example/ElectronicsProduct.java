package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{

    private final int warrantyMonths;
    private final BigDecimal weight;

    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        if (warrantyMonths < 0){
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

        this.weight = weight;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public Double weight(){
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost(){
        BigDecimal baseCost = new BigDecimal("79.00");
        BigDecimal surCharge = new BigDecimal("49.00");
        BigDecimal weightLimit = new BigDecimal("5.0");

        BigDecimal totalCost = baseCost;

        if (this.weight.compareTo(weightLimit) > 0) {
            totalCost = totalCost.add(surCharge);
        }

        return totalCost.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String productDetails(){
        return String.format(
                "Electronics: %s, Warranty: %d months",
                name(),
                warrantyMonths
        );
    }
}