package com.cutconnect.domains.dto;

import com.cutconnect.domains.stripe.PriceData;

import java.math.BigDecimal;

public class ProductWithPriceDTO {
    private String productId;

    private Boolean active;

    private String image;

    private String productName;

    private String priceId;

    private Long unitAmount;

    private BigDecimal unitAmountDecimal;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public Long getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Long unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getUnitAmountDecimal() {
        return unitAmountDecimal;
    }

    public void setUnitAmountDecimal(BigDecimal unitAmountDecimal) {
        this.unitAmountDecimal = unitAmountDecimal;
    }
}
