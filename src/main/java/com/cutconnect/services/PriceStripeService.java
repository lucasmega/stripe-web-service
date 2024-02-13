package com.cutconnect.services;

import com.stripe.Stripe;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;
import com.stripe.exception.StripeException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.cutconnect.domains.PriceData;
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

}
