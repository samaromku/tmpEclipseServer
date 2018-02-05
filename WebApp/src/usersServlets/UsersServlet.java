package usersServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;


@WebServlet("/tasks/users")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsersManager usersManager = UsersManager.INSTANCE;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = request.getParameter("error");
		
		request.setAttribute("title", "Пользователи");
		request.setAttribute("users", usersManager.getUsers());
		request.setAttribute("error", error);
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/users.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
