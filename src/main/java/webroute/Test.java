package webroute;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        Class routes = Routes.class;
        Constructor[] constructors = routes.getConstructors();
        Constructor constructor = constructors[0];
        Routes routesObj = (Routes) constructor.newInstance();
        for (Method m : Routes.class.getMethods()) {
            if (m.isAnnotationPresent(WebRoute.class)) {
                Annotation annotation = m.getAnnotation(WebRoute.class);
                WebRoute webRoute = (WebRoute) annotation;
                if (webRoute.value().equals("/test")) {
                    String result = (String) m.invoke(routesObj);
                    server.createContext(webRoute.value(), new MyHandler());
                } else if (webRoute.value().equals("/another")) {
                    server.createContext(webRoute.value(), new AnotherMyHandler());
                }
            }
        }
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String someStuff = "test";
            String template = "<h1>This is the %s response</h1>";
            String response = String.format(template, someStuff);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class AnotherMyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String someStuff = "another";
            String template = "<h1>This is the %s response</h1>";
            String response = String.format(template, someStuff);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}

