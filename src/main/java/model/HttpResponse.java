package model;

import java.util.Map;

public class HttpResponse {
    private HttpStatus status;
    private Map<String, String> headers;
    private byte[] body;


    public HttpResponse(byte[] body) {
        this.body = body;
    }

    public HttpResponse() {
    }

    public byte[] getBody() {
        return body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
