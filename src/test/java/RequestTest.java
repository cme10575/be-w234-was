import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpMethod;
import model.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.RequestParser;

import java.io.*;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class RequestTest {
    String getIndexRequest =
            "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";
    String postLoginRequest =
            "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=박재성&email=javjigi@slipp.net\n";

    @Test
    @DisplayName("POST request parse 테스트")
    void postRequestParse() throws IOException {
        InputStream is = new ByteArrayInputStream(postLoginRequest.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        HttpRequest request = RequestParser.parseRequest(br);
        assertThat(request.getBody().get("userId")).isEqualTo("javajigi");
        assertThat(request.getCookies()).isNull();
        assertThat(request.getMethod()).isSameAs(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
    }

    @Test
    @DisplayName("GET request parse 테스트")
    void getRequestParse() throws IOException {
        InputStream is = new ByteArrayInputStream(getIndexRequest.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        HttpRequest request = RequestParser.parseRequest(br);
        assertThat(request.getBody()).isNull();
        assertThat(request.getCookies()).isNull();
        assertThat(request.getMethod()).isSameAs(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
    }
}
