package pl.agh.customers.mysql.enums;

public enum RoleEnum {
    ROLE_USER,
    ROLE_ADMIN;

    public static RoleEnum fromValue(String roleEnum) {
        for (RoleEnum value : RoleEnum.values()) {
            if (value.name().equalsIgnoreCase(roleEnum)) {
                return value;
            }
        }
        return null;
    }

}
