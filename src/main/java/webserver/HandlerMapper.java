package webserver;

import controller.Controller;
import controller.FileController;
import controller.UserController;
import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpRequest;

public class HandlerMapper {
    private static UserController userController;
    private static FileController fileController;

    public HandlerMapper() {
        userController = new UserController();
        fileController = new FileController();
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
        } else {
            throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
        }
    }
}
