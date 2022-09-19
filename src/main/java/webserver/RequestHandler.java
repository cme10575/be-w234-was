package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import Service.UserService;
import exception.HttpErrorMessage;
import exception.HttpException;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private UserService userService;
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.userService = new UserService();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest request = RequestParser.parseRequestStartLine(br.readLine());
            HttpResponse response = new HttpResponse(dos);
            mapHandler(request, response);
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void mapHandler(HttpRequest request, HttpResponse response) throws IOException {
        try {
            if (request.getPath().equals("/user/create")) {
                createUser(request, response);
            } else if (request.getPath().matches("(.*).css")) {
                getCssFile(request, response);
            } else if (request.getPath().matches("(.*).html")) {
                getTextFile(request, response);
            } else {
                response.setErrorResponse(HttpErrorMessage.INVALID_REQUEST.getStatus(), HttpErrorMessage.INVALID_REQUEST.getMessage());
                return;
            }
            response.setStatus(HttpStatus.OK);
        } catch (HttpException e) {
            response.setErrorResponse(e.getErrorMessage().getStatus(),e.getErrorMessage().getMessage());
        } catch (RuntimeException e) {
            response.setErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        response.setBody(userService.addUser(request.getParams()).toString().getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
    }

    private void getTextFile(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
    }

    private void getCssFile(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/css;charset=utf-8");
        headers.put("Content-Length", String.valueOf(response.getBody().length));
        response.setHeaders(headers);
    }

    private void printRequest(InputStream in) throws UnsupportedEncodingException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();
        while (!"".equals(line)) {
            line = br.readLine();
            logger.debug("header: {}", line);
            if (line == null) {
                return;
            }
        }
    }
}
