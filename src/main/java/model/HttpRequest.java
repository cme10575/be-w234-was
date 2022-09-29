package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HttpRequest {
    private HttpMethod method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Map<String, String> cookies;
    private Map<String, String> body;

    public  HttpRequest() {}

    public HttpRequest(HttpMethod method, String path, Map<String, String> headers, Map<String, String> params, Map<String, String> cookies, Map<String, String> body) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.params = params;
        this.cookies = cookies;
        this.body = body;
    }
}
