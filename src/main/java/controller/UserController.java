package controller;

import Service.UserService;
import exception.HttpErrorMessage;
import exception.HttpException;
import exception.UserErrorMessage;
import exception.UserException;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResponseUtil;
import view.UserViewResolver;
import webserver.RequestHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * /user/* 관련 요청 처리
 */
public class UserController implements Controller {
    private static UserService userService = new UserService();
    private static UserViewResolver userViewResolver = new UserViewResolver();

    public UserController() {
    }

    @Override
    public HttpResponse map(HttpRequest request) throws IOException {
        if (request.getPath().equals("/user/create")) {
            if (request.getMethod() == HttpMethod.GET) return createUserByGet(request);
            if (request.getMethod() == HttpMethod.POST) return createUserByPost(request);
        } else if (request.getPath().equals("/user/login")) {
            if (request.getMethod() == HttpMethod.POST) return loginUserByPost(request);
        } else if (request.getPath().equals("/user/list")) {
            if (request.getMethod() == HttpMethod.GET) return getUserListFile(request);
        }
        throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
    }

    private HttpResponse getUserListFile(HttpRequest request) throws UserException, IOException {
        byte[] body;
        if (request.getHeaders().get("Cookie").contains("logined=true")) {
            Collection<User> userList = userService.getUserList();
            body = userViewResolver.getUserListHtml(userList).getBytes();
        } else {
            body = UserErrorMessage.UNAUTHROIZED_USER.getMessage().getBytes();
        }

        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);
        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse createUserByGet(HttpRequest request) throws UserException {
        byte[] body = userService.addUser(request.getParams()).toString().getBytes();
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);

        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse createUserByPost(HttpRequest request) throws UserException {
        byte[] body = userService.addUser(request.getBody()).toString().getBytes();
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);
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
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);

        if (isLoginSuccess) {
            headers.put("Set-Cookie", "logined=true; Path=/");
            headers.put("Location", "/index.html");
        } else {
            headers.put("Set-Cookie", "logined=false;");
            headers.put("Location", "/user/login_failed.html");
        }

        return headers;
    }
}
