package com.cutconnect.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public class PriceData {

    private String id;
    private String object;
    private boolean active;
    private String billingScheme;
    private long created;
    private String currency;
    private Long customUnitAmount;
    private boolean livemode;
    private String lookupKey;
    private Map<String, Object> metadata;
    private String nickname;
    private String product;
    private Recurring recurring;
    private String taxBehavior;
    private String tiersMode;
    private String transformQuantity;
    private String type;

    @JsonProperty("unit_amount")
    private Long unitAmount;
    @JsonProperty("unit_amount_decimal")
    private BigDecimal unitAmountDecimal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBillingScheme() {
        return billingScheme;
    }

    public void setBillingScheme(String billingScheme) {
        this.billingScheme = billingScheme;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getCustomUnitAmount() {
        return customUnitAmount;
    }

    public void setCustomUnitAmount(Long customUnitAmount) {
        this.customUnitAmount = customUnitAmount;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Recurring getRecurring() {
        return recurring;
    }

    public void setRecurring(Recurring recurring) {
        this.recurring = recurring;
    }

    public String getTaxBehavior() {
        return taxBehavior;
    }

    public void setTaxBehavior(String taxBehavior) {
        this.taxBehavior = taxBehavior;
    }

    public String getTiersMode() {
        return tiersMode;
    }

    public void setTiersMode(String tiersMode) {
        this.tiersMode = tiersMode;
    }

    public String getTransformQuantity() {
        return transformQuantity;
    }

    public void setTransformQuantity(String transformQuantity) {
        this.transformQuantity = transformQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
