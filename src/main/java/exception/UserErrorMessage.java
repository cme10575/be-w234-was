package exception;

public enum UserErrorMessage {
    DUPLICATE_USER("이미 가입된 회원입니다."),
    UNSIGNED_USER("회원 정보가 없습니다.");

    private String message;

    UserErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
