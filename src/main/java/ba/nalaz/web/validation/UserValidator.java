package ba.nalaz.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ba.nalaz.service.UserManager;
import ba.nalaz.util.ValidationKit;
import ba.nalaz.web.form.UserForm;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserManager userManager = null;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;
        String password = userForm.getUser().getPassword();
        String confirmPassword = userForm.getConfirmPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.firstName", null, "Ime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.lastName", null, "Prezime ne može biti prazno.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.username", null, "Korisničko ime ne može biti prazno.");

        
        String email = userForm.getUser().getEmail();
        if (email == null || "".equals(email) || !ValidationKit.isValidEmailAddress(email)){
            errors.rejectValue("user.email", null, "Potreban validan email.");
        }
//        String phoneNo = userForm.getUser().getPhoneNumber();
//        if (phoneNo != null && !phoneNo.isEmpty() && !ValidationKit.isValidPhoneNo(phoneNo)){
//            errors.rejectValue("user.phoneNumber", null, "Potreban validan broj telefona.");
//        }
//
//        String mobileNo = userForm.getUser().getPhoneNumber();
//        if (mobileNo != null && !mobileNo.isEmpty() && !ValidationKit.isValidPhoneNo(mobileNo)){
//            errors.rejectValue("user.mobileNumber", null, "Potreban validan broj mobilnog telefona.");
//        }
        
            String username = userForm.getUser().getUsername();
            if (username != null && (username.length() < 4 || username.length() > 40)) {
        		errors.rejectValue("user.username", null, "Korisničko ime mora sadržavati između 4 i 40 karaktera.");
            }
    		else if (!ValidationKit.isValidUsername(username)) {
    			errors.rejectValue("user.username", null, "Korisničko ime ne može sadržavati specijalne karaktere (!.,:-_$%)");
    		}
    }
}