package com.cutconnect.domains;

import java.util.List;

public class Cost {
    private String object;
    private String url;
    private Boolean hasMore;
    private List<PriceData> priceData;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<PriceData> getPriceData() {
        return priceData;
    }

    public void setPriceData(List<PriceData> priceData) {
        this.priceData = priceData;
    }
}
