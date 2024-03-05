package com.cutconnect.services;

import java.util.Map;
import java.util.HashMap;

import com.cutconnect.domains.User;
import com.cutconnect.repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.exception.StripeException;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;

import com.stripe.param.AccountUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AccountStripeService {
    @Value("${stripe.api-key}")
    private String stripeKey;

    @Value("${domain.key}")
    private String domain;

    private final UserRepository userRepository;

    @Autowired
    AccountStripeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> createStripeAccount(String email) throws StripeException {
        Stripe.apiKey = stripeKey;

        AccountCreateParams params =
                AccountCreateParams.builder()
                        .setCountry("BR")
                        .setType(AccountCreateParams.Type.EXPRESS)
                        .setCapabilities(
                                AccountCreateParams.Capabilities.builder()
                                        .setCardPayments(
                                                AccountCreateParams.Capabilities.CardPayments.builder()
                                                        .setRequested(true)
                                                        .build()
                                        )
                                        .setTransfers(
                                                AccountCreateParams.Capabilities.Transfers.builder().setRequested(true).build()
                                        )
                                        .build()
                        )
                        .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                        .setBusinessProfile(
                                AccountCreateParams.BusinessProfile.builder().setUrl("https://cutconnect-aa86b.web.app/sign-in").build()
                        )
                        .build();

        Account account = Account.create(params);

        userRepository.save(createObject(email, account.getId()));

        return createLinkStripe(account.getId());
    }

    private User createObject(String email, String accountId) {
        return new User(null, email, accountId);
    }

    private Map<String, Object> createLinkStripe(String id) throws StripeException {
        AccountLinkCreateParams params =
                AccountLinkCreateParams.builder()
                        .setAccount(id)
                        .setRefreshUrl(domain + "/reauth")
                        .setReturnUrl(domain + ":4200/success")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build();

        AccountLink accountLink = AccountLink.create(params);

        Map<String, Object> map = new HashMap<>();
        map.put("url", accountLink.getUrl());
        map.put("created", accountLink.getCreated());

        return map;
    }

    public String deleteStripeAccount(String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Account resource = Account.retrieve(id);
        Account account = resource.delete();

        return "Conta de ID: " + account.getId() + " foi deletada";
    }

}
