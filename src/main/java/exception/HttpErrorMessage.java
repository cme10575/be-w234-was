package exception;

import model.HttpStatus;

public enum HttpErrorMessage {
    EMPTY_REQUEST("요청 내용이 비어있습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("지원하지 않는 형식의 요청입니다.", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus status;

    HttpErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.message = errorMessage;
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
