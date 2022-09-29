package util;

import com.google.common.base.Strings;
import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpMethod;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public static HttpRequest parseRequest(BufferedReader br) throws IOException {
        HttpRequest request = new HttpRequest();
        parseFirstLine(request, br);
        parseHeader(request, br);
        parseBodys(request, br);

        return request;
    }

    static void parseFirstLine(HttpRequest request, BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        if (firstLine == null || firstLine.equals("")) {
            throw new HttpException(HttpErrorMessage.EMPTY_REQUEST);
        }

        String[] token = firstLine.split(" ");

        if (token.length != 3) {
            throw new HttpException(HttpErrorMessage.INVALID_REQUEST);
        }
        request.setMethod(HttpMethod.valueOf(token[0]));

        logger.debug("method : {}", token[0]);
        logger.debug("url : {}", token[1]);
        String[] urlToken = splitPathAndParams(token[1]);
        request.setPath(urlToken[0]);

        Map<String, String> params;
        if (urlToken.length > 1)
            params = parseParams(urlToken[1]);
        else
            params = new HashMap<>();
        request.setParams(params);
    }

    static String[] splitPathAndParams(String url) {
        return url.split("\\?");
    }

    static void parseHeader(HttpRequest request, BufferedReader br) throws UnsupportedEncodingException,IOException {
        Map<String, String> heaerDatas = new HashMap<>();
        String line = br.readLine();
        while (!Strings.isNullOrEmpty(line)) {
            logger.debug("header: {}", line);
            String[] token = line.split(":", 2);
            heaerDatas.put(token[0].trim(), token[1].trim());
            line = br.readLine();
        }

        Map<String, String> cookies = new HashMap<>();
        String[] token = heaerDatas.get("Cookie").split(";");
        for(var t : token) {
            String[] cookie = t.split("=", 2);
            cookies.put(cookie[0].trim(), cookie[1].trim());
        }
        logger.debug("cookies: {}", cookies);
        request.setHeaders(heaerDatas);
        request.setCookies(cookies);
    }

    static void parseBodys(HttpRequest request, BufferedReader br) throws UnsupportedEncodingException,IOException {
        Map<String, String> bodyDatas;
        int contentLength = request.getHeaders().get("Content-Length") != null ?
                Integer.parseInt(request.getHeaders().get("Content-Length")) : 0;

        char[] cbuf = new char[contentLength];
        br.read(cbuf);

        bodyDatas = parseParams(String.copyValueOf(cbuf));
        logger.debug("body: {}", bodyDatas);
        request.setBody(bodyDatas);
    }

    static Map<String, String> parseParams(String query) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx != -1)
                params.put(pair.substring(0, idx), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return params;
    }
}
