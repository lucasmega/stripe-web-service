package com.cutconnect.controllers;



import com.cutconnect.domains.CheckoutPayment;
import com.cutconnect.domains.PaymentWithRecurring;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cutconnect.domains.PaymentMethod;
import com.cutconnect.domains.CheckoutSession;
import com.cutconnect.services.PaymentStripeService;
import com.cutconnect.services.CustomerStripeService;

import java.util.Map;

@RestController
public class PaymentStripeController {
    private final PaymentStripeService paymentStripeService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStripeService.class);


    public PaymentStripeController(PaymentStripeService paymentStripeService) {
        this.paymentStripeService = paymentStripeService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Boolean> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            return ResponseEntity.ok(paymentStripeService.handleWebhook(payload, sigHeader));
        } catch (Exception e) {
            logger.error("Erro ao receber status: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/create-checkout-session")
    public ResponseEntity<RedirectView> createCheckoutSession(@RequestBody CheckoutSession checkoutSession) {
        try {
            return ResponseEntity.ok(paymentStripeService.createCheckoutSession(checkoutSession.getPriceId(), checkoutSession.getQuantity()));
        } catch (Exception e) {
            logger.error("Erro ao receber pagamento: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody Long amount) {
        try {
            return ResponseEntity.ok(paymentStripeService.createPayment(amount));
        } catch (Exception e) {
            logger.error("Erro ao receber pagamento: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-payment-with-card")
    public ResponseEntity<Map<String, Object>> createPaymentWithCard(@RequestBody PaymentMethod paymentMethod) {
        try {
            return ResponseEntity.ok(paymentStripeService.createPaymentWithCard(paymentMethod));
        } catch (Exception e) {
            logger.error("Erro ao receber pagamento: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/one-time-payment")
    public ResponseEntity<Map<String, String>> paymentWithCheckoutPage(@RequestBody CheckoutPayment checkoutPayment) {
        try {
            return ResponseEntity.ok(paymentStripeService.paymentWithCheckoutPage(checkoutPayment));
        } catch (Exception e) {
            logger.error("Erro ao receber pagamento: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("payment-with-recurring")
    public ResponseEntity<Map<String, Object>> subscriptionWithCheckoutPage(@RequestBody PaymentWithRecurring paymentWithRecurring) {
        try {
            return ResponseEntity.ok(paymentStripeService.subscriptionWithCheckoutPage(paymentWithRecurring));
        } catch (Exception e) {
            logger.error("Erro ao receber pagamento recorrente: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
