package com.cutconnect.domains.stripe;

public class Checks {
    private String addressLine1Check;
    private String addressPostalCodeCheck;
    private String cvcCheck;

    public String getAddressLine1Check() {
        return addressLine1Check;
    }

    public void setAddressLine1Check(String addressLine1Check) {
        this.addressLine1Check = addressLine1Check;
    }

    public String getAddressPostalCodeCheck() {
        return addressPostalCodeCheck;
    }

    public void setAddressPostalCodeCheck(String addressPostalCodeCheck) {
        this.addressPostalCodeCheck = addressPostalCodeCheck;
    }

    public String getCvcCheck() {
        return cvcCheck;
    }

    public void setCvcCheck(String cvcCheck) {
        this.cvcCheck = cvcCheck;
    }
}
