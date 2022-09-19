package controller;

import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileController implements Controller {

    @Override
    public void map(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getPath().matches("(.*).css")) {
            getCssFile(request, response);
        } else if (request.getPath().matches("(.*).html")) {
            getTextFile(request, response);
        } else if (request.getPath().matches("(.*).js")) {
            getTextFile(request, response);
        }
    }

    private void getTextFile(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
        response.setStatus(HttpStatus.OK);
    }

    private void getCssFile(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/css;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
        response.setStatus(HttpStatus.OK);
    }
}
