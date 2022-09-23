package model;

import exception.HttpErrorMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private HttpStatus status;
    private Map<String, String> headers;
    private byte[] body;
    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void send() throws IOException {
        dos.writeBytes("HTTP/1.1 " + status.getCode() + " " + status.getMessage() + " \r\n");
        for (var header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void setErrorResponse(HttpStatus status, String errorMessage) {
        setStatus(status);
        setBody(errorMessage.getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(errorMessage.length()));
        setHeaders(headers);
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
