package exception;

public enum UserErrorMessage {
    DUPLICATE_USER("이미 가입된 회원입니다."),
    UNSIGNED_USER("회원 정보가 없습니다."),
    INVALID_ID_TYPE("유효하지 않은 ID입니다."),
    INVALID_PASSWORD_TYPE("유효하지 않은 패스워드 타입입니다."),
    INVALID_NAME_TYPE("유효하지 않은 이름 형식입니다."),
    INVALID_EMAIL_TYPE("유효하지 않은 email 형식입니다.");

    private String message;

    UserErrorMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}
