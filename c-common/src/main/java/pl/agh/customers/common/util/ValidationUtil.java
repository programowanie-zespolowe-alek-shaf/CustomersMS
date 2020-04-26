package pl.agh.customers.common.util;


import pl.agh.customers.common.exception.BadRequestException;

public class ValidationUtil {

    public static void validateNotNull(FieldName fieldName, Object obj) throws BadRequestException {
        if (obj == null) {
            throw new BadRequestException(fieldName.getName() + " cannot be null");
        }
    }

    public static void validateGreaterThanZero(FieldName fieldName, Number number) throws BadRequestException {
        if (number == null || number.doubleValue() <= 0) {
            throw new BadRequestException(fieldName.getName() + " must be greater than zero");
        }

    }
}
