package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Address;
import entities.Task;
import entities.UserCoords;
import managers.AddressManager;
import managers.TasksManager;
import managers.UsersManager;
import worker.CoordsWorker;

@WebServlet("/tasks/map")
public class MapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersManager usersManager = UsersManager.INSTANCE;
	private AddressManager addressManager = AddressManager.INSTANCE;
	private TasksManager tasksManager = TasksManager.INSTANCE;
	private List<String>userNamesForTask;
	CoordsWorker coordsWorker = new CoordsWorker();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(coordsWorker.getLatestCoordes()){
			request.setAttribute("userCoords", coordsWorker.getCoordes());
		}
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressesOfTasks());
		request.setAttribute("userNames", userNames());
		request.setAttribute("tasksNotDone", tasksManager.tasksNotDone());
		
		request.setAttribute("users", usersManager.getUsers());
		request.setAttribute("userNamesForTask", userNamesForTask);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/map.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private List<String>userNames(){
		List<String>names = new ArrayList<String>();
		for(UserCoords coords:coordsWorker.getCoordes()){
			names.add(usersManager.getUserById(coords.getUserId()).getLogin());
		}
		return names;
	}
	
	private List<Address>addressesOfTasks(){
		//нужно взять все невыполненные задания и получить по ним адрес айди
		List<Address>addresses = new ArrayList<>();
		userNamesForTask = new ArrayList<>();
		for(Task task:tasksManager.tasksNotDone()){
			//получив адрес айди нужно положить адреса в список
			if(usersManager.getUserById(task.getUserId())!=null){
			userNamesForTask.add(usersManager.getUserById(task.getUserId()).getLogin());
			}else {
				userNamesForTask.add("Пользователь удален");
			}
			addresses.add(addressManager.getAddressById(task.getAddressId()));
		}
		//и вернуть этот список
		return addresses;
	}
	
}
