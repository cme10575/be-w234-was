package controller;

import Service.UserService;
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
    public void map(HttpRequest request, HttpResponse response) {
        if (request.getPath().equals("/user/create")) {
            createUser(request, response);
        }
    }

    private void createUser(HttpRequest request, HttpResponse response) throws RuntimeException{
        response.setBody(userService.addUser(request.getParams()).toString().getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
        response.setStatus(HttpStatus.OK);
    }
}
