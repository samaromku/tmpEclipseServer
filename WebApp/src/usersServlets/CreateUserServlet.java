package usersServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBWorker;
import entities.User;
import managers.UsersManager;
import network.UserEnum;
import storage.DataWork;
import utils.CreatorPicker;
import worker.UserWorker;


@WebServlet("/tasks/createUser")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	User user = new User();
	DataWork dataWork = new DataWork();
	DBWorker dbWorker = new DBWorker();
	UserWorker userWorker;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("userRoles", UserEnum.getRoles());
		request.setAttribute("title", "Создать пользователя");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/createUser.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");
		String fio = request.getParameter("fio");
		
		User createUser = new User.Builder()
				.login(login)
				.password(pwd)
				.fio(fio)
				.role(role)
				.phone(phone)
				.email(email)
				.build();
		userWorker  = new UserWorker(CreatorPicker.pickCreator(request));
		if(userWorker.create(createUser)){
			response.sendRedirect(request.getContextPath()+"/tasks/users");
		}

	}

}
