package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import database.DBWorker;
import database.KeepConnectionDB;
import entities.Task;
import entities.User;
import interfaces.Parse;
import managers.TasksManager;
import managers.TokenManager;
import managers.UsersManager;
import storage.AuthManager;
import storage.SHAHashing;
import storage.Token;
import worker.AddressWorker;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet implements Parse{
	private static final long serialVersionUID = 1L;
    private Auth auth = new Auth(); 
    DataSource dataSourse = null;
    AuthManager authManager = new AuthManager();
    DBWorker dbWorker = new DBWorker();
    TasksManager tasksManager = TasksManager.INSTANCE;
    SHAHashing hashing = new SHAHashing();
    UsersManager usersManager = UsersManager.INSTANCE;
    TokenManager tokenManager = TokenManager.instance;
    AddressWorker addressWorker = new AddressWorker();
   
    @Override
    public void init() throws ServletException {
    	super.init();
		dbWorker.getContactsFromDB();
		System.out.println("get contacts from db");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userLogin = request.getParameter("login");
		HttpSession session = request.getSession();
		//шифруем пароль
    	String userPwd = hashing.hashPwd(request.getParameter("password"));
    	//проверяем авторизацию юзера
    	User user = new User();
    	user.setLogin(userLogin);
    	user.setPassword(userPwd);
    	if(authManager.checkAuth(user)){

    		addressWorker.getAllAddresses();
    		dbWorker.getAllTasksFromDb();
    		Token token = new Token();
            //отдаем токен в список
            tokenManager.addTokenToMap(token, usersManager.getUserByUserName(user.getLogin()));
    		session.setAttribute("token", token);
    		session.setMaxInactiveInterval(3600);
//    		usersManager.addUserToken(token.getTokenUUID(), user);
//    		usersManager.setUser(user);		
			dbWorker.getFirebaseTokens();
			response.sendRedirect(request.getContextPath() + "/tasks/home");
    		}
    	}
    	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String read = parseRequest(req);
		//если запрос на авторизацию получен, вызываем метод, который проверяет, есть ли данный юзер в базе и ставит флаг авторизации
		if(read!=null){
			auth.checkAuth(read);
		}
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter writer = resp.getWriter();
		//метод получает строку в формате Json  и отдает ее на запрос
		String getResponse = auth.getWrite();
		//Отдает строку юзеру в зависимости от запроса
		if(getResponse!=null){
		writer.write(getResponse);
		writer.flush();
		}
	}

	@Override
	public String parseRequest(HttpServletRequest request) {
		String parse = "";
		try {
			parse = request.getReader().readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Возвращает строку json
		return parse;
	}
	
	public List<String> getUserNames(){
		List<String>userNames = new ArrayList<>();
    	for(Task t:tasksManager.getTasks()){
    		User user = usersManager.getUserById(t.getUserId());
    		userNames.add(user.getLogin());
    	}
    	return userNames;
	}


}
