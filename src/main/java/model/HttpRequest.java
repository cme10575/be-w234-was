package model;

import java.util.Map;

public class HttpRequest {
    private HttpMethod method;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Map<String, String> body;

    public  HttpRequest() {}

    public HttpRequest(HttpMethod method, String path, Map<String, String> headers, Map<String, String> params, Map<String, String> body) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.params = params;
        this.body = body;
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

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }
}
