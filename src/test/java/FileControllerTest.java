import controller.FileController;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileControllerTest {

    private FileController fileController = FileController.getInstance();

    @Test
    @DisplayName("알맞은 파일을 가져옴")
    void getContents() throws IOException {
        String path = "/user/login.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");

        HttpRequest request = new HttpRequest(HttpMethod.GET, path, headers, null, null, null);
        HttpResponse response = fileController.map(request);

        assertTrue(new String(response.getBody()).contains("사용자 아이디"));
    }

    @Test
    @DisplayName("없는 파일 요청")
    void notExistFileTest() throws IOException {
        String path = "/aaaaaaa.html";
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");

        HttpRequest request = new HttpRequest(HttpMethod.GET, path, headers, null, null, null);

        assertThrows(NoSuchFileException.class, () ->fileController.map(request));
    }
}
