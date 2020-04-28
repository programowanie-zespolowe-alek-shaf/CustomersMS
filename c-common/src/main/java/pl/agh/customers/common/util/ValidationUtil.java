package pl.agh.customers.common.util;


import pl.agh.customers.common.exception.BadRequestException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern emailPattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private static final Pattern phonePattern = Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$");

    public static void validateNotNull(FieldName fieldName, Object obj) throws BadRequestException {
        if (obj == null) {
            throw new BadRequestException(fieldName.getName() + " cannot be null");
        }
    }

    public static void validateEmailFormat(String email) throws BadRequestException {
        if (Objects.nonNull(email) && !emailPattern.matcher(email).matches()) {
            throw new BadRequestException("Email format is invalid");
        }
    }

    public static void validateGreaterThanZero(FieldName fieldName, Number number) throws BadRequestException {
        if (number == null || number.doubleValue() <= 0) {
            throw new BadRequestException(fieldName.getName() + " must be greater than zero");
        }
    }

    public static void validateGreaterOrEqualsZero(FieldName fieldName, Number number) throws BadRequestException {
        if (number == null || number.doubleValue() < 0) {
            throw new BadRequestException(fieldName.getName() + " must be greater or equals zero");
        }
    }

    public static void validatePhoneFormat(String phone) throws BadRequestException {
        if (Objects.nonNull(phone) && !phonePattern.matcher(phone).matches()) {
            throw new BadRequestException("Phone format is invalid");
        }
    }
}
