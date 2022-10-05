package webserver;

import java.io.*;
import java.net.Socket;

import controller.Controller;
import exception.HttpException;
import exception.UserException;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RequestParser;
import util.ResponseUtil;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private HandlerMapper handlerMapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.handlerMapper = HandlerMapper.getInstance();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest request = RequestParser.parseRequest(br);
            HttpResponse response = process(request);
            ResponseUtil.send(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse process(HttpRequest request) throws IOException {
        try {
            Controller controller = handlerMapper.findHandler(request);
            return controller.map(request);
        } catch (HttpException e) {
            return ResponseUtil.setErrorResponse(e.getErrorMessage().getStatus(), e.getErrorMessage().getMessage());
        } catch (UserException e) {
            return ResponseUtil.setErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            return ResponseUtil.setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
