package usersServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import managers.UsersManager;
import network.Response;
import storage.SHAHashing;
import utils.CreatorPicker;
import worker.UserWorker;


@WebServlet("/tasks/editUser")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UsersManager usersManager = UsersManager.INSTANCE;
    User editUser;
    SHAHashing hashing = new SHAHashing();
    UserWorker userWorker;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id");
		editUser = usersManager.getUserById(Integer.parseInt(userId));
		request.setAttribute("title", "Редактировать пользователя");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("user", editUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/editUser.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = editUser.getId();
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		String role = request.getParameter("role");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String fio = request.getParameter("fio");
		User editUser = new User.Builder()
    			.id(id)
    			.login(login)
    			.password(hashing.hashPwd(pwd))
    			.fio(fio)
    			.role(role)
    			.phone(phone)
    			.email(email)
    			.userRole(usersManager.getUserById(id).getUserRole())
    			.build();
		userWorker = new UserWorker(CreatorPicker.pickCreator(request));
		if(userWorker.edit(editUser).equals(Response.SUCCESS_EDIT_USER)){
			response.sendRedirect(request.getContextPath()+"/tasks/users");
		}	
	}

}
