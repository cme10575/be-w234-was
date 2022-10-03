package webserver;

import controller.Controller;
import controller.FileController;
import controller.MemoController;
import controller.UserController;
import exception.HttpExceptionMessage;
import exception.HttpException;
import model.HttpRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HandlerMapper {
    private static UserController userController;
    private static FileController fileController;
    private static MemoController memoController;

    private Map<Pattern, Controller> controllerMap;

    public HandlerMapper() {
        userController = new UserController();
        fileController = new FileController();
        memoController = new MemoController();
        controllerMap = new LinkedHashMap<>();
        controllerMap.put(Pattern.compile("/index.html"), memoController);
        controllerMap.put(Pattern.compile("(.*).css"), fileController);
        controllerMap.put(Pattern.compile("(.*).html"), fileController);
        controllerMap.put(Pattern.compile("/user(.*)"), userController);
        controllerMap.put(Pattern.compile("/memo(.*)"), memoController);
        controllerMap.put(Pattern.compile("(.*)"), fileController);
    }

    public Controller findHandler(HttpRequest request) {
        for (var entry : controllerMap.entrySet()) {
            if (entry.getKey().matcher(request.getPath()).matches())
                return entry.getValue();
        }
        throw new HttpException(HttpExceptionMessage.INVALID_REQUEST);
    }
}
