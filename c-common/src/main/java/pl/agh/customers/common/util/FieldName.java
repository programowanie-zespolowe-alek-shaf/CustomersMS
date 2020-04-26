package pl.agh.customers.common.util;

public enum FieldName {

    TITLE("title"),
    AUTHOR("author"),
    CATEGORY("category"),
    PRICE("price"),
    AVAILABLE("available");

    private final String name;

    FieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
