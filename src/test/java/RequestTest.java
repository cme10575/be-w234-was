import exception.HttpErrorMessage;
import exception.HttpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.RequestParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class RequestTest {
    String createUserStartLine = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";

    @Test
    @DisplayName("url parse 테스트")
    void verifyUrl() throws UnsupportedEncodingException {
        assertThat(RequestParser.parseRequestStartLine("GET /index.html HTTP/1.1").getPath()).isEqualTo("/index.html");
    }

    @Test
    @DisplayName("빈 url 검증")
    void verifyEmptyUrl() {
        assertThatThrownBy(() -> {
            RequestParser.parseRequestStartLine("");
        }).isInstanceOf(HttpException.class).hasMessageContaining(HttpErrorMessage.EMPTY_REQUEST.getMessage());
        assertThatThrownBy(() -> {
            RequestParser.parseRequestStartLine(null);
        }).isInstanceOf(HttpException.class).hasMessageContaining(HttpErrorMessage.EMPTY_REQUEST.getMessage());
    }

    @Test
    @DisplayName("잘못된 url 검증")
    void verifyInvalidUrl() {
        assertThatThrownBy(() -> {
            RequestParser.parseRequestStartLine("GET");
        }).isInstanceOf(HttpException.class).hasMessageContaining(HttpErrorMessage.INVALID_REQUEST.getMessage());
    }

    @Test
    @DisplayName("parameter parse 테스트")
    void parameterParse() throws UnsupportedEncodingException {
        assertThat(RequestParser.parseRequestStartLine(createUserStartLine).
                getParams()).isEqualTo(Map.ofEntries(
                    Map.entry("userId", "javajigi"),
                    Map.entry("password", "password"),
                    Map.entry("name", "박재성"),
                    Map.entry("email", "javajigi@slipp.net")));
    }
}
