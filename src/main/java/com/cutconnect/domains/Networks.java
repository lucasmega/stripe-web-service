package com.cutconnect.domains;

import java.util.List;

public class Networks {
    private List<String> available;
    private String preferred;

    public List<String> getAvailable() {
        return available;
    }

    public void setAvailable(List<String> available) {
        this.available = available;
    }

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }
}
