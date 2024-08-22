package com.bakerhughes.ecommerce.product.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String userId;
    private List<Long> productIds;
    private BigDecimal totalAmount;
    private boolean rushDelivery;
}
