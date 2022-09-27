package util;

import model.ContentType;
import model.HttpResponse;
import model.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static HttpResponse setErrorResponse(HttpStatus status, String errorMessage) {
        byte[] body = errorMessage.getBytes();
        Map<String, String> headers = makeDefaultHeader(body, ContentType.HTML);

        return HttpResponse.builder()
                .status(status)
                .headers(headers)
                .body(body)
                .build();
    }

    public static Map<String, String> makeDefaultHeader(byte[] body, ContentType contentType) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Length", String.valueOf(body.length));
        headers.put("Content-Type", contentType.getHeaderValue());
        return headers;
    }

    public static void send(DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes("HTTP/1.1 " + response.getStatus().getCode() + " " + response.getStatus().getMessage() + " \r\n");
        for (var header : response.getHeaders().entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }
}
