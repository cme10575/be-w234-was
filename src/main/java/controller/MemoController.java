package controller;

import exception.HttpErrorMessage;
import exception.HttpException;
import model.*;
import service.MemoService;
import util.ResponseUtil;

import java.io.IOException;
import java.util.Map;

public class MemoController implements Controller{
    MemoService memoService = new MemoService();

    @Override
    public HttpResponse map(HttpRequest request) throws IOException {
        if (request.getPath().equals("/memo")) {
            if (request.getMethod() == HttpMethod.POST) return createMemoByPost(request);
        }
        throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
    }

    private HttpResponse createMemoByPost(HttpRequest request) {
        memoService.addMemo(request.getBody().get("content"));
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
