package validator;

import exception.UserErrorMessage;
import exception.UserException;

import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static String idReg = "^[a-zA-Z0-9]{4,20}$";
    private static String passwordReg = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$";
    private static String nameReg = "^[a-zA-Z가-힣]{2,30}$";
    private static String emailReg = "^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*\\.[a-zA-Z]*$";

    public static boolean isValidId(String id) {
        return Pattern.matches(idReg, id);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(passwordReg, password);
    }

    public static boolean isValidName(String name) {
        return Pattern.matches(nameReg, name);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(emailReg, email);
    }

    public static boolean validateUser(Map<String, String> params) {
        if (params.get("userId") == null || !isValidId(params.get("userId")))
            throw new UserException(UserErrorMessage.INVALID_ID_TYPE);
        if (params.get("password") == null || !isValidPassword(params.get("password")))
            throw new UserException(UserErrorMessage.INVALID_PASSWORD_TYPE);
        if (params.get("name") == null || !isValidName(params.get("name")))
            throw new UserException(UserErrorMessage.INVALID_NAME_TYPE);
        if (params.get("email") == null || !isValidEmail(params.get("email")))
            throw new UserException(UserErrorMessage.INVALID_EMAIL_TYPE);
        return true;
    }

}
