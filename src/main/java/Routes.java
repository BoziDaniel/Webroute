import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Routes {
    @WebRoute(path = "/test")
    public void test(HttpExchange t) throws IOException {
        String message = "test";
        String template = "<h1>This is the %s route</h1>";
        String response = String.format(template, message);
        handleRequest(t, response);
    }

    @WebRoute(path = "/another")
    public void another(HttpExchange t) throws IOException {
        String message = "another";
        String template = "<h1>This is the %s route</h1>";
        String response = String.format(template, message);
        handleRequest(t, response);
    }

    private void handleRequest(HttpExchange t, String response) throws IOException {
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
