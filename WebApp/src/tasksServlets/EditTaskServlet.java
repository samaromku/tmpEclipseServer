package tasksServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Task;
import entities.User;
import managers.AddressManager;
import managers.TasksManager;
import managers.UsersManager;
import network.TaskEnum;
import storage.DateUtil;
import utils.CreatorPicker;
import worker.TaskWorker;

@WebServlet("/tasks/editTask")
public class EditTaskServlet extends HttpServlet {
	TasksManager tasksManager = TasksManager.INSTANCE;
	UsersManager usersManager = UsersManager.INSTANCE;
	AddressManager addressManager = AddressManager.INSTANCE;
	private static final long serialVersionUID = 1L;
	DateUtil dateUtil = new DateUtil();
	Task task;
	TaskWorker taskWorker;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		try{
		task = tasksManager.getById(Integer.parseInt(id));
		} catch(NumberFormatException e){
			System.out.println("id not equals number");
		}
		request.setAttribute("title", "Редактировать заявку");
		request.setAttribute("task", task);
		request.setAttribute("importances", TaskEnum.getImportances());
		request.setAttribute("statuses", TaskEnum.getStatuses());
		request.setAttribute("types", TaskEnum.getTypes());
		request.setAttribute("users", usersManager.getUsers());
		request.setAttribute("doneTime", dateUtil.getCurrentDateForDoneTime(task.getDoneTime()));
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/editTask.jsp");
    	dispatcher.forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//берем отредактированную заявку и заносим ее в базу данных
		request.setCharacterEncoding("UTF-8");
		int id = task.getId();
		String created = dateUtil.parseToServerDate(task.getCreated());
		String importance = request.getParameter("importance");
		String body = request.getParameter("body");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		String doneTime = request.getParameter("doneTime");
		String address = request.getParameter("address");
		String userName = request.getParameter("userName");
		int userId = usersManager.getUserByUserName(userName).getId();
		int addressId = Integer.parseInt(address);
		
		Task createdTask = new Task.Builder()
				.id(id)
				.created(created)
				.importance(importance)
				.body(body)
				.status(status)
				.type(type)
				.doneTime(doneTime)
				.userId(userId)
				.addressId(addressId)
				.build();
		taskWorker = new TaskWorker(CreatorPicker.pickCreator(request));
		if(taskWorker.edit(createdTask)){
		response.sendRedirect("/WebApp/tasks/home");
		}
	}
}
