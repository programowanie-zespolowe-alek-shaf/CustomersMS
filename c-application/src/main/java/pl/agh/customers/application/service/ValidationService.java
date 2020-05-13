package pl.agh.customers.application.service;

import org.springframework.stereotype.Service;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.application.dto.UserPutRequestDTO;
import pl.agh.customers.common.exception.BadRequestException;
import pl.agh.customers.common.util.FieldName;
import pl.agh.customers.common.util.ValidationUtil;

@Service
public class ValidationService {
    public void validate(UserPostRequestDTO user) throws BadRequestException {
        ValidationUtil.validateNotNull(FieldName.USERNAME, user.getUsername());
        ValidationUtil.validateNotNull(FieldName.PASSWORD, user.getPassword());
        ValidationUtil.validateNotNull(FieldName.FIRST_NAME, user.getFirstName());
        ValidationUtil.validateNotNull(FieldName.LAST_NAME, user.getLastName());
        ValidationUtil.validateNotNull(FieldName.EMAIL, user.getEmail());
        ValidationUtil.validateEmailFormat(user.getEmail());
        ValidationUtil.validateNotNull(FieldName.PHONE, user.getPhone());
        ValidationUtil.validatePhoneFormat(user.getPhone());
        ValidationUtil.validateNotNull(FieldName.ADDRESS, user.getAddress());
        ValidationUtil.validateNotNull(FieldName.ENABLED, user.getEnabled());
        ValidationUtil.validateGreaterThanZeroOrNull(FieldName.LAST_SHOPPING_CARD_ID, user.getLastShoppingCardId());
    }

    public void validate(int limit, int offset) throws BadRequestException {
        ValidationUtil.validateGreaterThanZero(FieldName.LIMIT, limit);
        ValidationUtil.validateGreaterOrEqualsZero(FieldName.OFFSET, offset);
    }

    public void validate(UserPutRequestDTO userDTO) throws BadRequestException {
        ValidationUtil.validateNotNull(FieldName.FIRST_NAME, userDTO.getFirstName());
        ValidationUtil.validateNotNull(FieldName.LAST_NAME, userDTO.getLastName());
        ValidationUtil.validateNotNull(FieldName.EMAIL, userDTO.getEmail());
        ValidationUtil.validateEmailFormat(userDTO.getEmail());
        ValidationUtil.validateNotNull(FieldName.PHONE, userDTO.getPhone());
        ValidationUtil.validatePhoneFormat(userDTO.getPhone());
        ValidationUtil.validateNotNull(FieldName.ADDRESS, userDTO.getAddress());
        ValidationUtil.validateNotNull(FieldName.ENABLED, userDTO.getEnabled());
        ValidationUtil.validateGreaterThanZeroOrNull(FieldName.LAST_SHOPPING_CARD_ID, userDTO.getLastShoppingCardId());
    }
}
