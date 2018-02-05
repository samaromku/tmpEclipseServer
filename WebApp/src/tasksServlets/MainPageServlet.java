package tasksServlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import database.DBWorker;
import entities.Address;
import entities.Task;
import entities.User;
import managers.AddressManager;
import managers.TasksManager;
import managers.TokenManager;
import managers.UsersManager;
import network.TaskEnum;
import storage.AuthManager;
import storage.SHAHashing;
import storage.Token;

@WebServlet("/tasks/home")
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DataSource dataSourse = null;
    AuthManager authManager = new AuthManager();
    TasksManager tasksManager = TasksManager.INSTANCE;
    SHAHashing hashing = new SHAHashing();
    UsersManager usersManager = UsersManager.INSTANCE;   
    Task task = new Task();
    DBWorker dbWorker = new DBWorker();
    AddressManager addressManager = AddressManager.INSTANCE; 
    TokenManager tokenManager = TokenManager.instance;
    int taskId = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
			HttpSession session = request.getSession(false);
			Token token = (Token)session.getAttribute("token");
			User user = tokenManager.getUserByToken(token.getTokenUUID());
//			User user = usersManager.getUser();
			dbWorker.getAllTasksFromDb();
	
			checkTaskId(request);
			request.setAttribute("statuses", TaskEnum.getStatuses());
			request.setAttribute("userLogin", user.getLogin());
			request.setAttribute("title", "Заявки");
	    	request.setAttribute("tasks", tasksManager.getNotDoneTasks());
	    	request.setAttribute("users", usersManager.getUsers());
	    	request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
	    	request.setAttribute("addresses", getAllAddresses(tasksManager.getNotDoneTasks()));
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/tasks.jsp");
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
	
	private List<Address> getAllAddresses(List<Task> tasks){
		List<Address>addresses = new ArrayList<>();
		for(Task t:tasks){
			Address address = addressManager.getAddressById(t.getAddressId());
			addresses.add(address);
		}
		return addresses;
	}
}
