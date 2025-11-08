package servlet.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import servlet.annotations.Controller;
import servlet.annotations.Url;
import servlet.utils.ClassDetector;
import servlet.utils.MethodInvoker;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class RouteInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();
            Map<String, MethodInvoker> routes = new HashMap<>();

            List<Class<?>> classes = ClassDetector.getAllClassesFromClasspath();
            for (Class<?> c : classes) {
                if (c.isAnnotationPresent(Controller.class)) {
                    for (var m : c.getDeclaredMethods()) {
                        if (m.isAnnotationPresent(Url.class)) {
                            Url annotation = m.getAnnotation(Url.class);
                            routes.put(annotation.value(), new MethodInvoker(c, m));
                        }
                    }
                }
            }

            context.setAttribute("routes", routes);
            System.out.println("✅ Routes enregistrées (" + routes.size() + ") dans ServletContext");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
