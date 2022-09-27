package controller;

import exception.HttpErrorMessage;
import exception.HttpException;
import model.ContentType;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import util.ResponseUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileController implements Controller {
    @Override
    public HttpResponse map(HttpRequest request) throws IOException {
        if (request.getPath().matches("(.*).css")) {
            return getCssFile(request);
        } else if (request.getPath().matches("(.*).html")) {
            return getTextFile(request);
        } else if (request.getPath().matches("(.*).js")) {
            return getTextFile(request);
        } else {
            throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
        }
    }

    private HttpResponse getTextFile(HttpRequest request) throws IOException {
        byte[] body = getFile(request.getPath());
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse getCssFile(HttpRequest request) throws IOException {
        byte[] body = getFile(request.getPath());
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.CSS);

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private byte[] getFile(String path) throws IOException {
        return Files.readAllBytes(new File("./webapp" + path).toPath());
    }
}
