package util;

import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpMethod;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public static HttpRequest parseRequestStartLine(String firstLine) throws UnsupportedEncodingException {
        if (firstLine == null || firstLine.equals("")) {
            throw new HttpException(HttpErrorMessage.EMPTY_REQUEST);
        }

        String[] token = firstLine.split(" ");

        if (token.length != 3) {
            throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
        }

        logger.debug("method : {}", token[0]);
        logger.debug("url : {}", token[1]);
        String[] urlToken = splitPathAndParams(token[1]);
        if (urlToken.length == 1)
            return new HttpRequest(HttpMethod.valueOf(token[0]), urlToken[0], null);
        else
            return new HttpRequest(HttpMethod.valueOf(token[0]), urlToken[0], parseParams(urlToken[1]));
    }

    public static String[] splitPathAndParams(String url) {
        return url.split("\\?");
    }

    public static Map<String, String> parseParams(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            params.put(pair.substring(0, idx), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return params;
    }
}
