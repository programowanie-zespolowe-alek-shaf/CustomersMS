package pl.agh.customers.common.util;

public enum FieldName {

    USERNAME("username"),
    PASSWORD("password"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    PHONE("phone"),
    ADDRESS("address"),
    ENABLED("enabled"),
    LIMIT("limit"),
    LAST_SHOPPING_CARD_ID("lastShoppingCardId"),
    OFFSET("offset");

    private final String name;

    FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
