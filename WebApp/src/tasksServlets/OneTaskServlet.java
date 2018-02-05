package tasksServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBWorker;
import entities.Task;
import entities.User;
import managers.CommentsManager;
import managers.ContactsManager;
import managers.TasksManager;
import managers.UsersManager;
import utils.CreatorPicker;
import worker.TaskWorker;

@WebServlet("/tasks/task")
public class OneTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    TasksManager tasksManager = TasksManager.INSTANCE; 
	UsersManager usersManager = UsersManager.INSTANCE;
	DBWorker dbWorker = new DBWorker();
	CommentsManager commentsManager = CommentsManager.INSTANCE;
	private TaskWorker taskWorker;
	ContactsManager contactsManager = ContactsManager.Instance;
			
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("title", "Задание");
        
		Task task = tasksManager.getById(Integer.parseInt(id));
		taskWorker = new TaskWorker(CreatorPicker.pickCreator(request));
		if(taskWorker.getCommentsByTaskId(task)){
			request.setAttribute("task", task);
			request.setAttribute("contacts", contactsManager.getContactsByAddressId(task.getAddressId()));
			request.setAttribute("comments", commentsManager.getCommentsByTaskId(task.getId()));
			request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
			if(usersManager.getUserById(task.getUserId())!=null){
				request.setAttribute("userName", usersManager.getUserById(task.getUserId()).getLogin());
			}else {
				request.setAttribute("userName", "Пользователь удален");
			}
			request.setCharacterEncoding("UTF-8");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/task.jsp");
	    	dispatcher.forward(request, response);
	    	commentsManager.removeAll();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
