package controller;

import Service.UserService;
import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * /user/* 관련 요청 처리
 */
public class UserController implements Controller {
    private static UserService userService = new UserService();

    public UserController() {
    }

    @Override
    public HttpResponse map(HttpRequest request) {
        if (request.getPath().equals("/user/create")) {
            if (request.getMethod().equals(HttpMethod.GET)) return  createUserByGet(request);
            if (request.getMethod().equals(HttpMethod.POST)) return createUserByPost(request);
        }
        throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
    }

    private HttpResponse createUserByGet(HttpRequest request) throws RuntimeException {
        byte[] body = userService.addUser(request.getParams()).toString().getBytes();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse createUserByPost(HttpRequest request) throws RuntimeException {
        byte[] body = userService.addUser(request.getBody()).toString().getBytes();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
        headers.put("Location", "/index.html");

        return HttpResponse.builder()
                .status(HttpStatus.MOVED_TEMPORARILY)
                .headers(headers)
                .body(body)
                .build();
    }
}
