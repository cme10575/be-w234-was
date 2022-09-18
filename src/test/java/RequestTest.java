import exception.HttpErrorMessage;
import exception.HttpException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.RequestParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class RequestTest {
    String createUserStartLine = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";

    @Test
    @DisplayName("url parse 테스트")
    void verifyUrl() throws UnsupportedEncodingException {
        assertThat(RequestParser.parseRequestStartLine("GET /index.html HTTP/1.1").getPath()).isEqualTo("/index.html");
    }

    private static Stream<Arguments> urlParameters() {
        return Stream.of(
                Arguments.of("빈 url 검증", "", HttpErrorMessage.EMPTY_REQUEST),
                Arguments.of("null url 검증", null, HttpErrorMessage.EMPTY_REQUEST),
                Arguments.of("잘못된 url 검증", "GET", HttpErrorMessage.INVALID_REQUEST)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("urlParameters")
    @DisplayName("url 검사")
    void verifyUrl(String testName, String firstLine, HttpErrorMessage message) {
        assertThatThrownBy(() -> {
            RequestParser.parseRequestStartLine(firstLine);
        }).isInstanceOf(HttpException.class).hasMessageContaining(message.getMessage());
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
