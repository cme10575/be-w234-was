package exception;

public class UserException extends RuntimeException {
    UserExceptionMessage errorMessage;

    public UserException(UserExceptionMessage message) {
        super(message.getMessage());
    }

    public UserExceptionMessage getErrorMessage() {
        return errorMessage;
    }
}
