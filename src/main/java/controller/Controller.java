package controller;

import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

public interface Controller {
    public void map(HttpRequest request, HttpResponse response) throws IOException;
}
