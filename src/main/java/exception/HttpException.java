package exception;

public class HttpException extends RuntimeException {
    HttpExceptionMessage errorMessage;

    public HttpException(HttpExceptionMessage message) {
        super(message.getMessage());
        errorMessage  = message;
    }

    public HttpExceptionMessage getErrorMessage() {
        return errorMessage;
    }
}
