package usersServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.TokenManager;
import managers.UsersManager;
import storage.Token;
import utils.CreatorPicker;
import worker.UserWorker;

@WebServlet("/tasks/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;   
	Token token;
	UserWorker userWorker;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		token = (Token)session.getAttribute("token");
		userWorker  = new UserWorker(CreatorPicker.pickCreator(request));
		session.setAttribute("token", null);
		if(userWorker.logout(token)){
			response.sendRedirect(request.getContextPath()+"/auth.jsp");	
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
