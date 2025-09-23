package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import servlet.annotations.*;

public class FrontServlet extends HttpServlet {

    private Map<String, Method> routes = new HashMap<>();

    // init est executé une seule fois au lancement de ce servlet
    @Override
    public void init() throws ServletException {
        try {
            // On teste de parcourir les methodes de la classe HelloController
            Class<?> clazz = Class.forName("com.itu.gest_emp.controller.HelloController");
            for (Method m : clazz.getDeclaredMethods()) {
                // On teste si les méthodes sont surmontés d'une annotation @Url ou non
                if (m.isAnnotationPresent(Url.class)) {
                    // Si oui , on recupere l'url en le stockant dans un map (valeur,methode)
                    Url ann = m.getAnnotation(Url.class);
                    routes.put(ann.value(), m);
                }
            }
            // donc on utilise la reflexion pour la seule raison de parcourir toutes les
            // méthodes d'une classe ici
            // car notre objectif ici est d'appeller une méthode d'une classe via url
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

   

}