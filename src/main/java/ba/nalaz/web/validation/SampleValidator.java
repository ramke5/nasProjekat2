package ba.nalaz.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.Sample;
import ba.nalaz.util.ValidationKit;

@Component
public class SampleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Sample.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	Sample sample = (Sample) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientName", null, "Ime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "patientSurname", null, "Prezime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", null, "Datum rođenja ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", null, "Spol ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", null, "Adresa ne može biti prazna.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", null, "Grad ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", null, "Broj telefona ne može biti prazan.");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sampleType", null, "Tip uzorka ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "analysisType", null, "Vrsta analize ne može biti prazna.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "method", null, "Metod rada ne može biti prazan.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "analysisReason", null, "Razlog testiranja ne može biti prazan.");
        if (!ValidationKit.isValidEmailAddress(sample.getEmail()) && !sample.getEmail().isEmpty()){
            errors.rejectValue("email", null, "Potreban validan email");
        }
    }
}
