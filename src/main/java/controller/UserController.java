package controller;

import Service.UserService;
import exception.HttpErrorMessage;
import exception.HttpException;
import exception.UserException;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import util.ResponseUtil;

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
            if (request.getMethod() == HttpMethod.GET) return  createUserByGet(request);
            if (request.getMethod() == HttpMethod.POST) return createUserByPost(request);
        } else if (request.getPath().equals("/user/login"))
            if (request.getMethod() == HttpMethod.POST) return loginUserByPost(request);
        throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
    }

    private HttpResponse createUserByGet(HttpRequest request) throws UserException {
        byte[] body = userService.addUser(request.getParams()).toString().getBytes();
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body);
        headers.put("Content-Type", "text/html;charset=utf-8");

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse createUserByPost(HttpRequest request) throws UserException {
        byte[] body = userService.addUser(request.getBody()).toString().getBytes();
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body);
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Location", "/index.html");

        return HttpResponse.builder()
                .status(HttpStatus.MOVED_TEMPORARILY)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse loginUserByPost(HttpRequest request) {
        byte[] body;
        boolean isLoginSuccess = true;

        try {
            body = userService.login(request.getBody()).toString().getBytes();
        } catch (UserException e) {
            body = e.getMessage().getBytes();
            isLoginSuccess = false;
        }

        Map<String, String> headers = setLoginByPostHeader(body, isLoginSuccess);
        return HttpResponse.builder()
                .status(HttpStatus.MOVED_TEMPORARILY)
                .headers(headers)
                .body(body)
                .build();
    }

    private Map<String, String> setLoginByPostHeader(byte[] body, boolean isLoginSuccess) {
        Map<String, String> headers = new HashMap<>();
        if (isLoginSuccess) {
            headers.put("Set-Cookie", "logined=true; Path=/");
            headers.put("Location", "/index.html");
        } else {
            headers.put("Set-Cookie", "logined=false;");
            headers.put("Location", "/user/login_failed.html");
        }
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
        return headers;
    }
}
