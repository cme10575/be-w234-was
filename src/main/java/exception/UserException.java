package exception;

public class UserException extends RuntimeException {
    UserErrorMessage errorMessage;

    public UserException(UserErrorMessage message) {
        super(message.getMessage());
    }

    public UserErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
