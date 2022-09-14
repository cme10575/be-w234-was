package model;

public class HttpResponse {
    private byte[] body;

    public HttpResponse(byte[] body) {
        this.body = body;
    }

    public HttpResponse() {
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
