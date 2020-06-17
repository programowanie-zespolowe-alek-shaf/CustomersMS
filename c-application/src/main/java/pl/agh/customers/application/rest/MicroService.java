package pl.agh.customers.application.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MicroService {

    PRODUCT_MS("product-catalog-ms", "PRODUCT_MS_URL"),
    ORDER_MS("order-management-ms", "ORDER_MS_URL"),
    CUSTOMER_MS("customers-ms", "CUSTOMER_MS_URL"),
    PAYMENT_MS("payment-ms", "PAYMENT_MS_URL"),
    CART_MS("shopping-card-ms", "CART_MS_URL");

    private final String serviceId;
    private final String envVariableName;
}
