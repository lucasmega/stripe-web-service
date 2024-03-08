package com.cutconnect.services.stripe;

import java.util.*;

import com.cutconnect.domains.dto.ProductWithPriceDTO;
import com.cutconnect.domains.stripe.Cost;
import com.cutconnect.domains.stripe.PriceData;
import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.exception.StripeException;

import com.stripe.net.RequestOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.cutconnect.domains.stripe.ProductData;

@Service
public class ProductStripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;

    private final PriceStripeService priceStripeService;

    @Autowired
    ProductStripeService(PriceStripeService priceStripeService) {
        this.priceStripeService = priceStripeService;
    }

    public ProductData createProduct(ProductData productData) throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();

        params.put("active", productData.isActive());
        params.put("description", productData.getDescription());
        params.put("metadata", productData.getMetadata());
        params.put("name", productData.getName());
        params.put("shippable", productData.getShippable());
        params.put("statement_descriptor", productData.getStatementDescriptor());
        params.put("tax_code", productData.getTaxCode());
        params.put("unit_label", productData.getUnitLabel());
        params.put("url", productData.getUrl());
        params.put("type", productData.getType());

        Product product = Product.create(params);

        productData.setId(product.getId());
        productData.setCreated(product.getCreated());
        productData.setUpdated(product.getUpdated());

        return productData;

    }



    /* ProductUpdateParams params = ProductUpdateParams.builder().putMetadata("order_id", "6735").build();*/
    public ProductData updateProduct(ProductData productData) throws StripeException {
        Stripe.apiKey = stripeKey;

        Product resource = Product.retrieve(productData.getId());

        Map<String, Object> params = new HashMap<>();
        params.put("active", productData.isActive());
        params.put("description", productData.getDescription());
        params.put("metadata", productData.getMetadata());
        params.put("name", productData.getName());
        params.put("shippable", productData.getShippable());
        params.put("statement_descriptor", productData.getStatementDescriptor());
        params.put("tax_code", productData.getTaxCode());
        params.put("unit_label", productData.getUnitLabel());
        params.put("url", productData.getUrl());

        Product updateProduct = resource.update(params);
        return productData;
    }

    public ProductData getProductById(String productId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Product resource = Product.retrieve(productId);
        return setProductData(resource);
    }

    private ProductData setProductData(Product product) {
        ProductData productData = new ProductData();
        productData.setActive(product.getActive());
        productData.setCreated(product.getCreated());
        productData.setDeleted(product.getDeleted());
        productData.setDescription(product.getDescription());
        productData.setId(product.getId());
        productData.setImages(product.getImages());
        productData.setLivemode(product.getLivemode());
        productData.setMetadata(product.getMetadata());
        productData.setName(product.getName());
        productData.setObject(product.getObject());
        productData.setShippable(product.getShippable());
        productData.setStatementDescriptor(product.getStatementDescriptor());
        productData.setTaxCode(product.getTaxCode());
        productData.setType(product.getType());
        productData.setUnitLabel(product.getUnitLabel());
        productData.setUpdated(product.getUpdated());
        productData.setUrl(product.getUrl());

        return productData;
    }

    public List<ProductData> getAllProducts(String connectedAccountId) throws StripeException {
        Stripe.apiKey = stripeKey;

        RequestOptions requestOptions = RequestOptions.builder()
                .setStripeAccount(connectedAccountId)
                .build();

        Map<String, Object> params = new HashMap<>();

        ProductCollection products = Product.list(params, requestOptions);
        List<ProductData> allProductData = new ArrayList<>();

        for (Product product : products.getData()) {
            ProductData productData = new ProductData();

            productData.setActive(product.getActive());
            productData.setCreated(product.getCreated());
            productData.setDeleted(product.getDeleted());
            productData.setDescription(product.getDescription());
            productData.setId(product.getId());
            productData.setImages(product.getImages());
            productData.setLivemode(product.getLivemode());
            productData.setMetadata(product.getMetadata());
            productData.setName(product.getName());
            productData.setObject(product.getObject());
            productData.setShippable(product.getShippable());
            productData.setStatementDescriptor(product.getStatementDescriptor());
            productData.setTaxCode(product.getTaxCode());
            productData.setType(product.getType());
            productData.setUnitLabel(product.getUnitLabel());
            productData.setUpdated(product.getUpdated());
            productData.setUrl(product.getUrl());

            allProductData.add(productData);
        }

        return allProductData;
    }

    public String deleteProduct(String productId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Product resource = Product.retrieve(productId);
        Product product = resource.delete();
        return "Produto de id: " +  productId + " deletado com sucesso";
    }

    public List<Product> getAllProductsFromConnectedAccounts() throws StripeException {
        Stripe.apiKey = stripeKey;

        AccountCollection accounts = Account.list((Map<String, Object>) null);

        List<Product> allProducts = new java.util.ArrayList<>();

        for (Account account : accounts.getData()) {
            ProductCollection products = Product.list(Map.of("limit", 100), RequestOptions.builder().setStripeAccount(account.getId()).build());
            allProducts.addAll(products.getData());
        }

        return allProducts;
    }


    public List<ProductWithPriceDTO> getProductsWithPrice() throws StripeException {

        List<ProductWithPriceDTO> list = new ArrayList<ProductWithPriceDTO>();

        List<Product> products = getAllProductsFromConnectedAccounts();
        List<Price> prices = priceStripeService.getAllPricesFromConnectedAccounts();

        for (Product product : products) {
            ProductWithPriceDTO object = getProductWithPriceDTO(product, prices);
            list.add(object);

            if (list.size() == 4) {
                return list;
            }
        }

        return list;
    }

    @NotNull
    private static ProductWithPriceDTO getProductWithPriceDTO(Product product, List<Price> prices) {
        ProductWithPriceDTO object = new ProductWithPriceDTO();

        object.setProductId(product.getId());
        object.setActive(product.getActive());
        object.setProductName(product.getName());
        object.setImage(product.getImages().get(0));

        for (Price price : prices) {
            if (product.getId().equals(price.getProduct())) {
                object.setPriceId(price.getId());
                object.setUnitAmount(price.getUnitAmount());
                object.setUnitAmountDecimal(price.getUnitAmountDecimal());
            }
        }
        return object;
    }


    public List<Product> getAllProductsFromConnectedAccount(String accountId) throws StripeException {
        Stripe.apiKey = stripeKey;

        List<Product> allProducts = new ArrayList<>();

        ProductCollection products = Product.list(Map.of("limit", 100), RequestOptions.builder().setStripeAccount(accountId).build());
        allProducts.addAll(products.getData());

        return allProducts;
    }

    public List<Price> getAllPricesFromConnectedAccount(String accountId) throws StripeException {
        Stripe.apiKey = stripeKey;

        List<Price> allPrices = new ArrayList<>();

        PriceCollection prices = Price.list(Map.of("limit", 100), RequestOptions.builder().setStripeAccount(accountId).build());
        allPrices.addAll(prices.getData());

        return allPrices;
    }

    public List<ProductWithPriceDTO> getProductsWithPriceFromAccount(String accountId) throws StripeException {
        List<ProductWithPriceDTO> list = new ArrayList<>();

        List<Product> products = getAllProductsFromConnectedAccount(accountId);
        List<Price> prices = getAllPricesFromConnectedAccount(accountId);

        for (Product product : products) {
            ProductWithPriceDTO object = getProductWithPriceDTO(product, prices);
            list.add(object);

            if (list.size() == 4) {
                return list;
            }
        }

        return list;
    }

}
