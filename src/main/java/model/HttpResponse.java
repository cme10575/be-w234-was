package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
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
}
