package ba.nalaz.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.nalaz.model.core.Lab;
import ba.nalaz.util.ValidationKit;


@Component
public class LabValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Lab.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Lab lab = (Lab) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "Ime laboratorije ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.firstName", null, "Ime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.lastName", null, "Prezime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.city", null, "Grad ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.address1", null, "Adresa ne može biti prazna.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.email", null, "Email ne može biti prazan");
        if (!ValidationKit.isValidEmailAddress(lab.getContact().getEmail())){
            errors.rejectValue("contact.email", null, "Potreban validan email");
        }
    }
}