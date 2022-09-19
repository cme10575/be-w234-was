package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import exception.HttpException;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private HandlerMapper handlerMapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.handlerMapper = new HandlerMapper();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest request = RequestParser.parseRequestStartLine(br.readLine());
            HttpResponse response = new HttpResponse(dos);
            process(request, response);
            response.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void process(HttpRequest request, HttpResponse response) throws IOException {
        try {
            Controller controller = handlerMapper.findHandler(request);
            controller.map(request, response);
        } catch (HttpException e) {
            response.setErrorResponse(e.getErrorMessage().getStatus(), e.getErrorMessage().getMessage());
        } catch (RuntimeException e) {
            response.setErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
