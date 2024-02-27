package com.cutconnect.services.stripe;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.stripe.Stripe;
import com.stripe.model.Product;
import com.stripe.model.ProductCollection;
import com.stripe.exception.StripeException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.cutconnect.domains.stripe.ProductData;

@Service
public class ProductStripeService {

    @Value("${stripe.api-key}")
    private String stripeKey;

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
        productData.setAttributes(product.getAttributes());
        productData.setCaption(product.getCaption());
        productData.setCreated(product.getCreated());
        productData.setDeactivateOn(product.getDeactivateOn());
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

    public List<ProductData> getAllProducts() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();

        ProductCollection products = Product.list(params);
        List<ProductData> allProductData = new ArrayList<>();

        for (Product product : products.getData()) {
            ProductData productData = new ProductData();

            productData.setActive(product.getActive());
            productData.setAttributes(product.getAttributes());
            productData.setCaption(product.getCaption());
            productData.setCreated(product.getCreated());
            productData.setDeactivateOn(product.getDeactivateOn());
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


}
