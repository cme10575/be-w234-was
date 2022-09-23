package model;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private Map<String, String> params;

    public HttpRequest(HttpMethod method, String path, Map params) {
        this.method = method;
        this.path = path;
        this.params = params;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
