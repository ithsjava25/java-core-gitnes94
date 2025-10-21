package com.example;

import java.math.BigDecimal;

public interface Shippable {

    Double weight();

    BigDecimal calculateShippingCost();
}
