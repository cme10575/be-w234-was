package webserver;

import controller.Controller;
import controller.FileController;
import controller.MemoController;
import controller.UserController;
import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpRequest;

public class HandlerMapper {
    private static UserController userController;
    private static FileController fileController;
    private static MemoController memoController;

    public HandlerMapper() {
        userController = new UserController();
        fileController = new FileController();
        memoController = new MemoController();
    }

    public Controller findHandler(HttpRequest request) {
        if (request.getPath().matches("(.*).css")) {
            return fileController;
        } else if (request.getPath().matches("(.*).html")) {
            return fileController;
        } else if (request.getPath().matches("(.*).js")) {
            return fileController;
        } else if (request.getPath().matches("/user(.*)")) {
            return userController;
        } else if (request.getPath().matches("/memo(.*)")) {
            return memoController;
        } else {
            throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
        }
    }
}
