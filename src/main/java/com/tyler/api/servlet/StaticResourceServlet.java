package com.tyler.api.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StaticResourceServlet
 */
@WebServlet("/StaticResourceServlet")
public class StaticResourceServlet extends HttpServlet {
	public StaticResourceServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println(req.getRequestURI());
        String resource = request.getRequestURI().replace("/APIv2-0.0.1", "");
        System.out.println(resource);
        if(resource != null ) {
			switch (resource) {
				case "/":
					System.out.println("invoked forwarding of index.html");
					request.getRequestDispatcher("src/index.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/RoomServlet/all":
					request.getRequestDispatcher("/src/view-room.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/UserServlet/all":
					request.getRequestDispatcher("/src/view-user.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/RoomServlet/create":
					request.getRequestDispatcher("/src/create-room.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/RoomServlet/deleteRoom":
					request.getRequestDispatcher("/src/delete-room.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/RoomServlet/addUser":
					request.getRequestDispatcher("/src/addRoomUser.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/RoomServlet/removeUser":
					request.getRequestDispatcher("/src/delete-user.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/Login":
					request.getRequestDispatcher("/src/Login.html").include(request, response);
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				
				
				case "/styles/stylesheet":
					System.out.println("invoked forwarding of styles.css");
					request.getRequestDispatcher("/src/css/styles.css").include(request, response);
					response.setContentType("text/css");
					response.setStatus(200);
					break;
				case "/js/login":
					System.out.println("invoked forwarding of login.js");
					request.getRequestDispatcher("/src/js/login.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/viewRoom":
					System.out.println("invoked forwarding of productlist.js");
					request.getRequestDispatcher("/src/js/viewroom.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/viewUser":
					System.out.println("invoked forwarding of productlist.js");
					request.getRequestDispatcher("/src/js/viewuser.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/createRoom":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/create-room.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/deleteRoom":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/delete-room.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/addRoomUser":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/addRoomUser.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/deleteRoomUser":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/deleteRoomUser.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/componentInjector":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/componentInjector.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/router":
					System.out.println("invoked forwarding of shoppingcart.js");
					request.getRequestDispatcher("/src/js/router.js").include(request, response);
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
					
				case "/img/2.jpg":
					System.out.println("invoked forwarding of 2.jpg");
					request.getRequestDispatcher("/src/img/2.jpg").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/4.webp":
					System.out.println("invoked forwarding of 4.webp");
					request.getRequestDispatcher("/src/img/4.webp").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/7.jpg":
					System.out.println("invoked forwarding of 7.jpg");
					request.getRequestDispatcher("/src/img/7.jpg").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/background.webp":
					System.out.println("invoked forwarding of background4.webp");
					request.getRequestDispatcher("/src/img/background.webp").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/77.jpg":
					System.out.println("invoked forwarding of 77.jpg");
					request.getRequestDispatcher("/src/img/77.jpg").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/login2.jpg":
					System.out.println("invoked forwarding of login2.jpg");
					request.getRequestDispatcher("/src/img/login2.jpg").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				case "/img/logo.png":
					System.out.println("invoked forwarding of logo.png");
					request.getRequestDispatcher("/src/img/logo.png").include(request, response);
					response.setContentType("image/jpeg");
					response.setStatus(200);
					break;
				
				default:
					response.setStatus(404);
			}
			
			
				
		} else {
			response.setStatus(404);
		}
	}
}
