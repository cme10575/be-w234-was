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
    private UserController userController;
    private FileController fileController;
    private MemoController memoController;
    private Map<Pattern, Controller> controllerMap;

    private HandlerMapper() {
        userController = UserController.getInstance();
        fileController = FileController.getInstance();
        memoController = MemoController.getInstance();
        controllerMap = new LinkedHashMap<>();
        controllerMap.put(Pattern.compile("/index.html"), memoController);
        controllerMap.put(Pattern.compile("(.*).css"), fileController);
        controllerMap.put(Pattern.compile("(.*).html"), fileController);
        controllerMap.put(Pattern.compile("/user(.*)"), userController);
        controllerMap.put(Pattern.compile("/memo(.*)"), memoController);
        controllerMap.put(Pattern.compile("(.*)"), fileController);
    }

    private static class HandlerMapperHolder {
        public static final HandlerMapper INSTANCE = new HandlerMapper();
    }

    public static HandlerMapper getInstance() {
        return HandlerMapper.HandlerMapperHolder.INSTANCE;
    }

    public Controller findHandler(HttpRequest request) {
        for (var entry : controllerMap.entrySet()) {
            if (entry.getKey().matcher(request.getPath()).matches())
                return entry.getValue();
        }
        throw new HttpException(HttpExceptionMessage.INVALID_REQUEST);
    }
}
