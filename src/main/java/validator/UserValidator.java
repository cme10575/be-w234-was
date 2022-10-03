package validator;

import exception.UserExceptionMessage;
import exception.UserException;

import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern ID = Pattern.compile("^[a-zA-Z\\d]{4,20}$");
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$");
    private static final Pattern NAME = Pattern.compile("^[a-zA-Z가-힣]{2,30}$");
    private static final Pattern EMAIL = Pattern.compile("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*\\.[a-zA-Z]*$");

    public static boolean isValidId(String id) {
        return ID.matcher(id).matches();
    }

    public static boolean isValidPassword(String password) {
        return PASSWORD.matcher(password).matches();
    }

    public static boolean isValidName(String name) {
        return NAME.matcher(name).matches();
    }

    public static boolean isValidEmail(String email) {
        return EMAIL.matcher(email).matches();
    }

    public static boolean validateUser(Map<String, String> params) {
        if (params.get("userId") == null || !isValidId(params.get("userId")))
            throw new UserException(UserExceptionMessage.INVALID_ID_TYPE);
        if (params.get("password") == null || !isValidPassword(params.get("password")))
            throw new UserException(UserExceptionMessage.INVALID_PASSWORD_TYPE);
        if (params.get("name") == null || !isValidName(params.get("name")))
            throw new UserException(UserExceptionMessage.INVALID_NAME_TYPE);
        if (params.get("email") == null || !isValidEmail(params.get("email")))
            throw new UserException(UserExceptionMessage.INVALID_EMAIL_TYPE);
        return true;
    }

}
