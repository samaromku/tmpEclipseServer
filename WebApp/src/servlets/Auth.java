package servlets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DBWorker;
import entities.Task;
import entities.User;
import managers.AddressManager;
import managers.TasksManager;
import managers.TokenManager;
import managers.UsersManager;
import network.Request;
import network.Response;
import network.UserEnum;
import storage.DataWork;
import storage.JsonParser;
import storage.Token;

public class Auth {
	private String clientName;
	private String write;
	private JsonParser parser;
	private DBWorker dbWorker;
	private UsersManager usersManager = UsersManager.INSTANCE;
	private TasksManager tasksManager = TasksManager.INSTANCE;
	private TokenManager tokenManager = TokenManager.instance;
	private AddressManager addressManager = AddressManager.INSTANCE;
    private boolean isAuth = false;
    DataWork parserRequest;

    public Auth(){
    	this.dbWorker = new DBWorker();
    	this.parser = new JsonParser();
    	this.parserRequest = new DataWork();
    }
    
    public boolean isAuth() {
        return isAuth;
    }
    
    public String getWrite(){
    	return write;
    }
	

    public void checkAuth(String firstLine){
    	dbWorker.getAllUsersFromDB();
    	System.out.println("Получаем: "+firstLine);
    	//если юзер просит его авторизовать
        if(parser.parseFromJson(firstLine).getRequest().equals(Request.AUTH)){
        	//проверяем кто этот юзер
            doAuth(firstLine);
            String data = write;
            System.out.println("Отправляем: " + clientName + data);
            return;
        }else if(checkToken(parser.parseFromJson(firstLine).getToken())){
        	parserRequest.workWithRequest(firstLine);
        	write = parserRequest.getWrite();
        	System.out.println("Отправляем: "+write);
        	return;
        }else 
        	write = parser.notSuccessAuth();
        System.out.println("Отправляем: "+write);
    }
    
    //метод в котором нужно проверить токен
    private boolean checkToken(Token token){
    	//проверяем токен во всем массиве токенов
    	if(token!=null){
    		for (Map.Entry<Token, User> e : tokenManager.getTokens().entrySet()) {
    		    if(token.getTokenUUID().equals(e.getKey().getTokenUUID())){
    		    	return true;
    		    }
    		}
    	}
    	//если нет false
    	return false;
    }

    private void doAuth(String firstLine){
        Request authRequest = parser.parseFromJson(firstLine);
        String userName = authRequest.getUser().getLogin();
        String pwd = authRequest.getUser().getPassword();
        //проверка юзера
        
        //создаем нового юзера для проверки авторизации
        User tryToAuth = new User();
        //присваиваем ему логин из реквеста
        tryToAuth.setLogin(userName);
        //присваиваем пароль из реквеста
        tryToAuth.setPassword(pwd);
        //создаем массив юзеров для дальнейших действий
        List<User> userList = new ArrayList<>();
        //добавляем в массив юзеров из синглетона
        userList.addAll(usersManager.getUsers());
        //проверяем каждого пользователя на соответствие по логину и паролю
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getLogin().equals(tryToAuth.getLogin()) &&
                userList.get(i).getPassword().equals(tryToAuth.getPassword())){
                tryToAuth = userList.get(i);
            }
        }
        //если юзер проверен, у него есть роль
        if(tryToAuth.getRole()!=null){
        	//если роль соответствует админ или диспетчер, даем все заявки с сервера
        if(tryToAuth.getRole().equals(UserEnum.ADMIN_ROLE)||tryToAuth.getRole().equals(UserEnum.MANAGER_ROLE)){
            //получаем все задания из бд
        	dbWorker.getAllTasksFromDb();
        	//создаем список заданий для дальнейших манипуляций, кладем в него задания из менеджера
            List<Task>tasks = tasksManager.getTasks();
            //создаем новый токен, который нужно занести в список токенов и отдать клиенту
            Token token = new Token();
            //отдаем токен в список
            tokenManager.addTokenToMap(token, tryToAuth);
            //присваиваем переменной, которую отправляем пользователю новое значение, отдаем токен
            write = parser.parseToAdminUsersTask(userList, tasks, Response.ADD_ACTION_ADMIN, token);
            System.out.println("юзер " + userName + " это админ ");
            //присваиваем имя клиенту
            clientName=userName;
            //флаг авторизации
            isAuth=true;
            return;
            
        } 
        //если роль юзер, даем заявки по id
        else if(tryToAuth.getRole().equals(UserEnum.USER_ROLE)){
        	//выполняем запрос заявок в базе данных по id юзера 
        	dbWorker.getTasksFromDbByUserId(String.valueOf(tryToAuth.getId()));
            //создаем новый токен, который нужно занести в список токенов и отдать клиенту
            Token token = new Token();            
            //отдаем токен в список
            tokenManager.addTokenToMap(token, tryToAuth);
//            tokenManager.addToken(token);
//            tasksManager.addToUsersTaskList(tryToAuth);
            //отдаем в запрос пользователю список с его заданиями и список пользователей без паролей
            write = parser.parseToJsonUserTasks(dbWorker.getUsersForSimpleUser(), 
            									tryToAuth,
                    							tasksManager.getTasksByUserId(tryToAuth), 
                    							addressManager.getUsersAddresses(tryToAuth),
                    							Response.ADD_TASKS_TO_USER, 
                    							token);
            System.out.println("юзернейм " + userName + " - это юзер из БД");
            //присваиваем имя клиента
            clientName=userName;
            //авторизован
            isAuth=true;
            return;
        }
        } else {
            write = parser.parseToJsonGuest();
//            log.info("юзер " + userName + " это гость ");
            clientName=("неавторизованный клиент "+userName);
            isAuth=false;
            return;
        }
    }
}
