import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        Routes routes = new Routes();
        Method[] methods = Routes.class.getMethods();
        try {
            for (Method method : methods) {
                if (method.isAnnotationPresent(WebRoute.class)) {
                    Annotation annotation = method.getAnnotation(WebRoute.class);
                    WebRoute webRoute = (WebRoute) annotation;
                    if (t.getRequestURI().toString().equals(webRoute.path())) {
                        method.invoke(routes, t);
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }


}

