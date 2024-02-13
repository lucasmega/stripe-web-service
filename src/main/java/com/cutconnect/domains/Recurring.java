package com.cutconnect.domains;

public class Recurring {

    private String aggregateUsage;
    private String interval;
    private int intervalCount;
    private Long trialPeriodDays;
    private String usageType;

    public String getAggregateUsage() {
        return aggregateUsage;
    }

    public void setAggregateUsage(String aggregateUsage) {
        this.aggregateUsage = aggregateUsage;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(int intervalCount) {
        this.intervalCount = intervalCount;
    }

    public Long getTrialPeriodDays() {
        return trialPeriodDays;
    }

    public void setTrialPeriodDays(Long trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }
}
