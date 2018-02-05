package tasksServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBWorker;
import managers.CommentsManager;
import managers.TasksManager;
import managers.UsersManager;

@WebServlet("/tasks/done_tasks")
public class DoneTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBWorker dbWorker = new DBWorker();
	TasksManager tasksManager = TasksManager.INSTANCE;
	UsersManager usersManager = UsersManager.INSTANCE;   
	CommentsManager commentsManager = CommentsManager.INSTANCE;
    int taskId = 0;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//if(dbWorker.getDoneTasks()){
		checkTaskId(request);
			if(dbWorker.getCommentsForDoneTasks()){
				request.setAttribute("doneComments", commentsManager.getDoneComments());
			}			
			request.setAttribute("tasks", tasksManager.getDoneTasks());
			request.setAttribute("users", usersManager.getUsers());
			request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		//}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/done_tasks.jsp");
		dispatcher.forward(request, response);
	}
	
	private void checkTaskId(HttpServletRequest request) {
		String requestTaskId = request.getParameter("id");
		if(requestTaskId!=null && !requestTaskId.isEmpty()){
			taskId = Integer.parseInt(requestTaskId);	
		}else {
			taskId = 0;
		}
		
		if(taskId!=0){
			request.setAttribute("taskId", String.valueOf(taskId));
		}else {
			request.setAttribute("taskId", "0");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
