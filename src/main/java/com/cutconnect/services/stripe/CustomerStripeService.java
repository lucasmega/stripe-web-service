package com.cutconnect.services.stripe;

import com.cutconnect.domains.stripe.CustomerData;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerStripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;

    public CustomerData createCustomer(CustomerData request) throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("email", request.getEmail());

        Customer stripeCustomer = Customer.create(params);
        request.setCustomerId(stripeCustomer.getId());
        return request;
    }

    public List<CustomerData> getAllCustomers() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        CustomerCollection customers = Customer.list(params);
        List<CustomerData> allCustomerData = new ArrayList<CustomerData>();

        for (Customer customer : customers.getData()) {
            CustomerData customerData = new CustomerData();
            customerData.setName(customer.getName());
            customerData.setEmail(customer.getEmail());
            customerData.setCustomerId(customer.getId());
            allCustomerData.add(customerData);
        }
        return allCustomerData;
    }

    public String deleteCustomer(String customerId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Customer customer = Customer.retrieve(customerId);
        Customer deletedCustomer = customer.delete();
        return "Cliente de id: " + customerId + " deletado com sucesso";
    }

    public CustomerData getCustomerById(String id) throws  StripeException {
        Stripe.apiKey = stripeKey;
        Customer customer = Customer.retrieve(id);
        return setCustomerData(customer);
    }
    private CustomerData setCustomerData(Customer customer) {
        CustomerData customerData = new CustomerData();
        customerData.setName(customer.getName());
        customerData.setEmail(customer.getEmail());
        customerData.setCustomerId(customer.getId());
        return customerData;
    }

    public CustomerData updateCustomer(CustomerData customerData) throws StripeException {
        Stripe.apiKey = stripeKey;

        Customer customer = Customer.retrieve(customerData.getCustomerId());

        Map<String, Object> params = new HashMap<>();
        params.put("name", customerData.getName());
        params.put("email", customerData.getEmail());

        Customer updatedCustomer = customer.update(params);

        return customerData;
    }

}
