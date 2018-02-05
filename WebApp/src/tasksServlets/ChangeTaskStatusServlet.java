package tasksServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import database.DBWorker;
import entities.Task;
import entities.User;
import managers.TasksManager;
import managers.TokenManager;
import managers.UsersManager;
import storage.Token;
import utils.CreatorPicker;
import worker.TaskWorker;

@WebServlet("/tasks/changeTaskStatus")
public class ChangeTaskStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBWorker dbWorker = new DBWorker();
	private TasksManager tasksManager = TasksManager.INSTANCE;
	Task changeStatusTask;
	private TaskWorker taskWorker;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		String status = request.getParameter("status");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		
		changeStatusTask = tasksManager.getById(taskId);
		changeStatusTask.setStatus(status);
		
		taskWorker = new TaskWorker(CreatorPicker.pickCreator(request));
		if(taskWorker.changeStatusDone(changeStatusTask)){
			response.sendRedirect(request.getContextPath() + "/tasks/home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
