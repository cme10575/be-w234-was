package controller;

import entity.Memo;
import entity.User;
import exception.HttpErrorMessage;
import exception.HttpException;
import exception.UserErrorMessage;
import model.*;
import service.MemoService;
import service.UserService;
import util.ResponseUtil;
import view.MemoViewResolver;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class MemoController implements Controller{
    MemoService memoService = new MemoService();
    UserService userService = new UserService();
    MemoViewResolver viewResolver = new MemoViewResolver();

    @Override
    public HttpResponse map(HttpRequest request) throws IOException {
        if (request.getPath().equals("/memo")) {
            if (request.getMethod() == HttpMethod.POST) return createMemoByPost(request);
        } else if (request.getPath().equals("/index.html")) {
            if (request.getMethod() == HttpMethod.GET) return getMemoListByGet();
        }
        throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
    }

    private HttpResponse getMemoListByGet() throws IOException {
        byte[] body;
        Collection<Memo> memoList = memoService.getMemoList();
        body = viewResolver.getMemoListHtml(memoList).getBytes();

        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);
        return HttpResponse.builder()
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body)
                .build();
    }

    private HttpResponse createMemoByPost(HttpRequest request) {
        if (request.getCookies() == null || request.getCookies().get("logined") == null || request.getCookies().get("logined").equals("false"))
            return ResponseUtil.setErrorResponse(HttpStatus.BAD_REQUEST, UserErrorMessage.UNAUTHROIZED_USER.getMessage());

        User user = userService.getUser(request.getCookies().get("Id"));
        if (user == null)
            return ResponseUtil.setErrorResponse(HttpStatus.BAD_REQUEST, UserErrorMessage.UNAUTHROIZED_USER.getMessage());

        memoService.addMemo(request.getBody().get("content"), user.getName());
        byte[] body = "".getBytes();
        Map<String, String> headers = ResponseUtil.makeDefaultHeader(body, ContentType.HTML);
        headers.put("Location", "/index.html");

        return HttpResponse.builder()
                .status(HttpStatus.MOVED_TEMPORARILY)
                .headers(headers)
                .body(body)
                .build();
    }
}
