package exception;

public class HttpException extends RuntimeException {
    HttpErrorMessage errorMessage;

    public HttpException(HttpErrorMessage message) {
        super(message.getMessage());
        errorMessage  = message;
    }

    public HttpErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
