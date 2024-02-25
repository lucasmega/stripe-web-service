package com.cutconnect.services;

import com.cutconnect.domains.stripe.Recurring;
import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.model.PriceCollection;
import com.stripe.param.PriceCreateParams;
import com.stripe.exception.StripeException;

import com.stripe.param.PriceListParams;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cutconnect.domains.stripe.Cost;
import com.cutconnect.domains.stripe.PriceData;

@Service
public class PriceStripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;
    @Value("${currency}")
    private String currency;
    public void createRecurringPrice(PriceData priceData) throws StripeException {
        Stripe.apiKey = stripeKey;

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(priceData.getUnitAmount())
                .setProduct(priceData.getProduct())
                .setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(getRecurringInterval(priceData
                                                        .getRecurring()
                                                        .getInterval()
                                        )
                                )
                                .build()
                ).build();

        Price price = Price.create(params);

    }

    private PriceCreateParams.Recurring.Interval getRecurringInterval(String intervalParam) {
        return switch (intervalParam.toLowerCase()) {
            case "day" -> PriceCreateParams.Recurring.Interval.DAY;
            case "week" -> PriceCreateParams.Recurring.Interval.WEEK;
            case "month" -> PriceCreateParams.Recurring.Interval.MONTH;
            case "year" -> PriceCreateParams.Recurring.Interval.YEAR;
            default -> throw new IllegalArgumentException("Intervalo de recorrência inválido: " + intervalParam);
        };
    }

    public void createUnitPrice(PriceData priceData) throws StripeException {
        Stripe.apiKey = stripeKey;

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(priceData.getUnitAmount())
                .setProduct(priceData.getProduct()).build();

        Price price = Price.create(params);
    }

    public Cost getAllPricing() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();

        PriceCollection pricing = Price.list(params);

        Cost cost = new Cost();

        cost.setUrl(pricing.getUrl());
        cost.setHasMore(pricing.getHasMore());
        cost.setObject(pricing.getObject());

        List<PriceData> allPricingData = getPriceData(pricing);
        cost.setPriceData(allPricingData);

        return cost;

    }

    private static List<PriceData> getPriceData(PriceCollection pricing) {
        List<PriceData> allPricingData = new ArrayList<>();

        for (Price price: pricing.getData()) {
            PriceData priceData = new PriceData();

            priceData.setActive(price.getActive());
            priceData.setCreated(price.getCreated());
            priceData.setId(price.getId());
            priceData.setLivemode(price.getLivemode());
            priceData.setObject(price.getObject());
            priceData.setType(price.getType());
            priceData.setBillingScheme(price.getBillingScheme());
            priceData.setCurrency(price.getCurrency());
            priceData.setLookupKey(price.getLookupKey());
            priceData.setNickname(price.getNickname());
            priceData.setProduct(price.getProduct());
            priceData.setTaxBehavior(price.getTaxBehavior());
            priceData.setTiersMode(price.getTiersMode());
            priceData.setUnitAmount(price.getUnitAmount());
            priceData.setUnitAmountDecimal(price.getUnitAmountDecimal());

            allPricingData.add(priceData);

            if (price.getRecurring() != null) {

                Recurring recurring = new Recurring();

                recurring.setInterval(price.getRecurring().getInterval());
                recurring.setAggregateUsage(price.getRecurring().getAggregateUsage());
                recurring.setIntervalCount(price.getRecurring().getIntervalCount());
                recurring.setTrialPeriodDays(price.getRecurring().getTrialPeriodDays());
                recurring.setUsageType(price.getRecurring().getUsageType());

                priceData.setRecurring(recurring);
            }

        }
        return allPricingData;
    }

    public Cost getPriceByProduct(String productId) throws StripeException {
        Stripe.apiKey = stripeKey;

        PriceListParams params = PriceListParams.builder()
                .setActive(true)
                .setProduct(productId)
                .build();

        PriceCollection pricing = Price.list(params);

        Cost cost = new Cost();

        cost.setUrl(pricing.getUrl());
        cost.setHasMore(pricing.getHasMore());
        cost.setObject(pricing.getObject());

        List<PriceData> allPricingData = getPriceData(pricing);
        cost.setPriceData(allPricingData);

        return cost;
    }

}
