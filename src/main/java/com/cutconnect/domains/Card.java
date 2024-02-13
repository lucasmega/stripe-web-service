package com.cutconnect.domains;

public class Card {
    private String brand;
    private Checks checks;
    private String country;
    private Integer expMonth;
    private Integer expYear;
    private String fingerprint;
    private String funding;
    private String generatedFrom;
    private String last4;
    private Networks networks;
    private ThreeDSecureUsage threeDSecureUsage;
    private String wallet;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Checks getChecks() {
        return checks;
    }

    public void setChecks(Checks checks) {
        this.checks = checks;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getGeneratedFrom() {
        return generatedFrom;
    }

    public void setGeneratedFrom(String generatedFrom) {
        this.generatedFrom = generatedFrom;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public Networks getNetworks() {
        return networks;
    }

    public void setNetworks(Networks networks) {
        this.networks = networks;
    }

    public ThreeDSecureUsage getThreeDSecureUsage() {
        return threeDSecureUsage;
    }

    public void setThreeDSecureUsage(ThreeDSecureUsage threeDSecureUsage) {
        this.threeDSecureUsage = threeDSecureUsage;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
}
