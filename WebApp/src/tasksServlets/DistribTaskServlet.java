package tasksServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Task;
import entities.User;
import managers.TasksManager;
import managers.UsersManager;
import utils.CreatorPicker;
import worker.TaskWorker;

@WebServlet("/tasks/distribTask")
public class DistribTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TasksManager tasksManager = TasksManager.INSTANCE;
	UsersManager usersManager = UsersManager.INSTANCE;
	TaskWorker taskWorker;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		String status = request.getParameter("status");
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		taskWorker = new TaskWorker(CreatorPicker.pickCreator(request));
		Task changeTask = tasksManager.getById(taskId);
		changeTask.setStatus(status);
		changeTask.setUserId(userId);
		if(taskWorker.changeStatusDistrib(changeTask)){
			response.sendRedirect(request.getContextPath()+"/tasks/home");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
