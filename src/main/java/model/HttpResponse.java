package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class HttpResponse {
    private HttpStatus status;
    private Map<String, String> headers;
    private byte[] body;

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
