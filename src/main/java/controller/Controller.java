package controller;

import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

public interface Controller {
    public HttpResponse map(HttpRequest request) throws IOException;
}
