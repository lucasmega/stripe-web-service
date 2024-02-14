package com.cutconnect.services;

import com.cutconnect.domains.CheckoutPayment;
import com.cutconnect.domains.PaymentWithRecurring;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.model.checkout.Session;
import com.stripe.exception.StripeException;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.stereotype.Service;
import com.stripe.param.checkout.SessionCreateParams;

import static spark.Spark.staticFiles;

import org.springframework.web.servlet.view.RedirectView;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.HashMap;

import com.stripe.model.PaymentIntent;
import com.cutconnect.domains.PaymentMethod;


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

        staticFiles.externalLocation("public");

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

    public RedirectView createCheckoutSessionSubscription(String priceId, Long quantity) throws StripeException {
        Stripe.apiKey = stripeKey;

        staticFiles.externalLocation("public");

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
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

    public Map<String, Object> createPaymentWithCard(PaymentMethod paymentMethod) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentMethodCreateParams.CardDetails cardDetails =
                PaymentMethodCreateParams.CardDetails.builder()
                        .setNumber(paymentMethod.getCard().getCardNumber())
                        .setExpMonth(paymentMethod.getCard().getExpMonth())
                        .setExpYear(paymentMethod.getCard().getExpYear())
                        .setCvc(paymentMethod.getCard().getCvc())
                        .build();

        PaymentMethodCreateParams params =
                PaymentMethodCreateParams.builder()
                        .setType(PaymentMethodCreateParams.Type.CARD)
                        .setCard(cardDetails)
                        .build();

        Map<String, Object> paramMap = params.toMap();

        PaymentIntentCreateParams paymentIntentParams = PaymentIntentCreateParams.builder()
                .setPaymentMethod(params.getPaymentMethod())
                .setAmount(paymentMethod.getAmount())
                .setCurrency(currency)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

        Map<String, Object> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());

        return map;
    }

    public Map<String, String> paymentWithCheckoutPage(CheckoutPayment checkoutPayment) throws StripeException {
        Stripe.apiKey = stripeKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(checkoutPayment.getSuccessUrl())
                .setCancelUrl(checkoutPayment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(checkoutPayment.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(checkoutPayment.getCurrency())
                                                .setUnitAmount(checkoutPayment.getAmount())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(checkoutPayment.getName()).build()
                                                ).build()
                                ).build()
                ).build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());

        return responseData;
    }

    public Map<String, Object> subscriptionWithCheckoutPage(PaymentWithRecurring paymentWithRecurring) throws StripeException {
        Stripe.apiKey = stripeKey;

        SessionCreateParams params = new SessionCreateParams.Builder()
                .setCancelUrl(paymentWithRecurring.getCancelUrl())
                .setSuccessUrl(paymentWithRecurring.getSuccessUrl())
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(
                        new SessionCreateParams.LineItem.Builder()
                                .setQuantity(paymentWithRecurring.getQuantity())
                                .setPrice(paymentWithRecurring.getPriceId()).build()
                ).build();

        Session session = Session.create(params);
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        return response;
    }

}
