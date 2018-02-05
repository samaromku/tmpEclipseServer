package usersServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import managers.UsersManager;
import utils.CreatorPicker;
import worker.UserWorker;


@WebServlet("/tasks/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsersManager usersManager = UsersManager.INSTANCE;
	UserWorker userWorker;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id");
		User removeUser = usersManager.getUserById(Integer.parseInt(userId));
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		userWorker = new UserWorker(CreatorPicker.pickCreator(request));
		if(userWorker.remove(removeUser)){
			response.sendRedirect(request.getContextPath()+"/tasks/users");
		}else {
			response.sendRedirect(request.getContextPath()+"/tasks/users?error=cannot-delete-user");
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
