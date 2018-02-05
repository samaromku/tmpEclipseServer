package tasksServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/tasks/createTask")
public class CreateTaskServlet extends HttpServlet {
	TasksManager tasksManager = TasksManager.INSTANCE;
	UsersManager usersManager = UsersManager.INSTANCE;
	AddressManager addressManager = AddressManager.INSTANCE;
	private static final long serialVersionUID = 1L;
	private DateUtil dateUtil = new DateUtil();
	private TaskWorker taskWorker;

   	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Task task = new Task();
		//получаем адрес с карты
		String addressId = request.getParameter("address");
		//отдаем ид адреса на jsp
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addressId", addressId);
		request.setAttribute("title", "Создать заявку");
		request.setAttribute("importances", TaskEnum.getImportances());
		request.setAttribute("statuses", TaskEnum.getStatuses());
		request.setAttribute("types", TaskEnum.getTypes());
		request.setAttribute("userNames", userNames());
		request.setAttribute("currentDate", dateUtil.getCurrentDateForDoneTime());
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/createTask.jsp");
    	dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = tasksManager.getMaxId()+1;
		String created = dateUtil.getCurrentDateForCreateOrUpdateTask();
		String importance = request.getParameter("importance");
		String body = request.getParameter("body");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		String doneTime = request.getParameter("doneTime");
		String addressIdFromJsp = request.getParameter("addressId");
		String userName = request.getParameter("userName");
		int userId = usersManager.getUserByUserName(userName).getId();
		int addressId = Integer.parseInt(addressIdFromJsp);
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
		if(taskWorker.create(createdTask)){
			response.sendRedirect("/WebApp/tasks/home");
		}else {
			badResponse(response);
		}	
	}
	
	private void badResponse(HttpServletResponse response){
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println("Error create comment...");
		writer.flush();
	}

	private List<String> userNames(){
		List<String>userNames = new ArrayList<String>();
		for(User u:usersManager.getUsers()){
			userNames.add(u.getLogin());
		}
	return userNames;
	}
}
