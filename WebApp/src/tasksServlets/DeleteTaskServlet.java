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

@WebServlet("/tasks/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TasksManager tasksManager = TasksManager.INSTANCE;
	private User creator;
	private TaskWorker taskWorker;

       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter("id");
		Task task = tasksManager.getById(Integer.parseInt(taskId));
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		taskWorker = new TaskWorker(CreatorPicker.pickCreator(request));
		taskWorker.remove(task);
		response.sendRedirect(request.getContextPath() + "/tasks/home");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
