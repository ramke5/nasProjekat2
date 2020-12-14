package ba.nalaz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidationKit {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    private static final String PHONE_NO_PATTERN = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
    // Password must contain capital letters and numbers
    private static final String PASSWORD_PATTERN = "^\\S*(?=\\S*[A-Z])(?=\\S*[0-9])\\S*$";
    // Username must contain capital letters and numbers
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_.-@]*$";


    public static boolean isValidEmailAddress(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(email);
        return  m.matches();
    }
//    public static boolean isValidPhoneNo(String phoneNo) {
//        Pattern p = Pattern.compile(PHONE_NO_PATTERN);
//        Matcher m = p.matcher(phoneNo);
//        return  m.matches();
//    }
//    public static boolean isValidMobileNo(String mobile) {
//        Pattern p = Pattern.compile("\\d{10}");
//        Matcher m = p.matcher(mobile);
//        return m.matches();
//    }
//    public static boolean isValidOfficeNo(String officeNo) {
//        Pattern p = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
//        Matcher m = p.matcher(officeNo);
//        return m.matches();
//    }
//    public static boolean isValidNumber(String num) {
//        Pattern p = Pattern.compile("\\d+");
//        Matcher m = p.matcher(num);
//        return m.matches();
//    }   
    public static boolean isValidPassword(String password) {
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    public static boolean isValidUsername(String username) {
        Pattern p = Pattern.compile(USERNAME_PATTERN);
        Matcher m = p.matcher(username);
        return m.matches();
    }
}