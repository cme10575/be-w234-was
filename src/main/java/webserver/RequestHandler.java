package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import model.HttpRequest;
import model.HttpResponse;
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
            HttpRequest request = RequestParser.parseRequestStartLine(br.readLine());
            HttpResponse response = new HttpResponse();
            mapHandler(request, response);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, response.getBody().length);
            responseBody(dos, response.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void mapHandler(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getPath().equals("/user/create")) {
            createUser(request, response);
        }
        if (request.getPath().equals("/index.html") || request.getPath().equals("/user/form.html")) {
            response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        } else {
            response.setBody(Files.readAllBytes(new File("./webapp" + request.getPath()).toPath()));
        }
    }

    public void createUser(HttpRequest request, HttpResponse response) {
        response.setBody(userService.addUser(request.getParams()).toString().getBytes());
        System.out.println(userService.addUser(request.getParams()).toString());
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

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
