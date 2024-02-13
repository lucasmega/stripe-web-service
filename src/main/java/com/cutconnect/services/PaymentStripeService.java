package com.cutconnect.services;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentStripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;

    @Value("${domain.key}")
    private String domain;

    @Value("${currency}")
    private String currency;

    public Boolean handleWebhook(String payload, String sigHeader) throws StripeException {
        Stripe.apiKey = stripeKey;

        Event event = Webhook.constructEvent(payload, sigHeader, Stripe.apiKey);

        switch (event.getType()) {
            case "payment_intent.succeeded":
                System.out.println("Pagamento recebido!");
                break;
            case "payment_intent.payment_failed":
                System.out.println("Pagamento falhou!");
                break;
            default:
                System.out.println("Evento desconhecido: " + event.getType());
        }

        return true;
    }


    public RedirectView createCheckoutSession(String priceId, Long quantity) throws StripeException {
        Stripe.apiKey = stripeKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domain + "/success")
                .setCancelUrl(domain + "/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(quantity)
                                .setPrice(priceId)
                                .build()
                ).build();
        Session session = Session.create(params);
        return new RedirectView(session.getUrl());
    }

    public Map<String, String> createPayment(Long amount) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams
                        .builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                        ).build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, String> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());

        return map;
    }

    public Map<String, Object> createPaymentWithCard() throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentMethodCreateParams.CardDetails cardDetails =
                PaymentMethodCreateParams.CardDetails.builder()
                        .setNumber("4242424242424242")
                        .setExpMonth(8L)
                        .setExpYear(2026L)
                        .setCvc("314")
                        .build();

        PaymentMethodCreateParams params =
                PaymentMethodCreateParams.builder()
                        .setType(PaymentMethodCreateParams.Type.CARD)
                        .setCard(cardDetails)
                        .build();

        Map<String, Object> paramMap = params.toMap();

        PaymentIntentCreateParams paymentIntentParams = PaymentIntentCreateParams.builder()
                .setPaymentMethod(params.getPaymentMethod())
                .setAmount(1000L)
                .setCurrency("brl")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        Map<String, Object> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());

        return map;
    }


}
