package com.tyler.api.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticResourceServlet extends HttpServlet {

    public StaticResourceServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(req.getRequestURI());
        String resource = req.getRequestURI().replace("APIv2-0.0.1/StaticServlet", "");
        System.out.println(resource);
//        if (resource != null) {
            req.getRequestDispatcher("index.html").forward(req, resp);

//            switch (resource) {
//                case "/":
//                    System.out.println("invoked forwarding of index.html");
//                    req.getRequestDispatcher("index").forward(req, resp);
//                    break;
//                case "/list":
//                    System.out.println("invoked forwarding of pirate-list.html");
//                    req.getRequestDispatcher("static/APIv2-0.0.1").include(req, resp);
//                    break;
//                default:
//                    resp.setStatus(404);
//                    break;
//            }
//        } else {
//            resp.setStatus(404);
//        }

    }
}
