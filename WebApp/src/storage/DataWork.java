package storage;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;

import database.DBWorker;
import database.ParserJsonApp;
import database.Queries;
import entities.Comment;
import entities.Task;
import entities.User;
import entities.UserCoords;
import entities.UserRole;
import managers.CommentsManager;
import managers.ContactsManager;
import managers.TasksManager;
import managers.TokenManager;
import network.Request;
import network.Response;
import network.UserEnum;
import storage.Token;
import worker.AddressWorker;
import worker.CoordsWorker;
import worker.ParserJson;
import worker.TaskWorker;
import worker.UserWorker;

public class DataWork {
	private JsonParser parser = new JsonParser();
	private DBWorker dbWorker = new DBWorker();
	private String read;
	private String write;
	private TokenManager tokenManager = TokenManager.instance;
	private TasksManager tasksManager = TasksManager.INSTANCE;
	private CommentsManager commentsManager = CommentsManager.INSTANCE;
	private ContactsManager contactsManager = ContactsManager.Instance;
	private User user = new User();
	ParserJsonApp parserJsonApp = new ParserJsonApp();
	ParserJson parserJson = new ParserJson();
	TaskWorker taskWorker;
	AddressWorker addressWorker = new AddressWorker();
	UserWorker userWorker;
	CoordsWorker coordsWorker;
	Task taskFromResponse;
	User userFromResponse;
	Token tokenFromResponse;
	UserCoords coordsFromResponse;
	UserRole userRoleFromResponse;
	Comment commentFromResponse;
	
	public String getWrite(){
		return write;
	}
	
	//метод получает строку в формате Json, и в зависимости от запроса, делает тот или иной метод
	 public void workWithRequest(String read){
	        String request;
	        this.read = read;
	        if(read!=null&&parser.parseFromJson(read).getRequest()!=null) {
	            request = parser.parseFromJson(read).getRequest();
	            UUID token = parser.parseFromJson(read).getToken().getTokenUUID();
	            if(token!=null){
	            user = tokenManager.getUserByToken(token);
	            taskWorker = new TaskWorker(user);
	            userWorker = new UserWorker(user);
	            coordsWorker = new CoordsWorker();
	            coordsFromResponse = parserJson.parseCoordsFromJson(read);
	            userRoleFromResponse = parserJson.parseUserRoleFromJson(read);
	            commentFromResponse = parserJson.parseCommentFromJson(read);
	            taskFromResponse = parserJson.parseTaskFromJson(read);
	            userFromResponse = parserJson.parseUserFromJson(read);
	            tokenFromResponse = parserJson.parseTokenFromJson(read);
	            }
	            switch (request){
	            	case Request.UPDATE_TASKS:
	                	updateTasksForUserOrAdmin();
	                	return;
	            
	                case Queries.CONTROL_TASK:
	                	isResponseSuccess(taskWorker.changeStatusTask(
	                			Queries.CONTROL_TASK, Queries.ADD_COMMENT_CONTROL_TASK, commentFromResponse), 
	                			Response.sucOrNot(Response.ADD_COMMENT_SUCCESS));
	                    return;
	                    
	                case Queries.DOING_TASK:
	                	isResponseSuccess(taskWorker.changeStatusDoing(taskFromResponse), Response.sucOrNot(Response.SUCCESS_UPDATE_TASK));
	                    return;
	                    
	                case Queries.NOTE:
	                	isResponseSuccess(taskWorker.addComment(commentFromResponse, Queries.ADD_COMMENT_TASK), Response.sucOrNot(Response.ADD_COMMENT_SUCCESS));
	                    return;

	                case Queries.NEED_HELP:
	                	isResponseSuccess(taskWorker.changeStatusTask(
	                			Queries.NEED_HELP, Queries.ADD_COMMENT_NEED_HELP_TASK, commentFromResponse), 
	                			Response.sucOrNot(Response.ADD_COMMENT_SUCCESS));
	                    return;
	                case Queries.DISAGREE_TASK:
	                	isResponseSuccess(taskWorker.changeStatusTask(
	                			Queries.DISAGREE_TASK, Queries.ADD_COMMENT_DISAGREE_TASK, commentFromResponse), 
	                			Response.sucOrNot(Response.ADD_COMMENT_SUCCESS));
	                    return;
	                    
	                case Queries.DONE_TASK:
	                	isResponseSuccess(taskWorker.changeStatusDone(taskFromResponse), Response.sucOrNot(Response.SUCCESS_UPDATE_TASK));
	                	return;
	                	
	                case Queries.ADD_FIREBASE_TOKEN:
	                	String firebaseToken = parserJson.parseFirebaseTokenFromJson(read);
	                	tokenManager.addFirebaseToken(firebaseToken, userFromResponse.getId());
	                	isResponseSuccess(dbWorker.addFirebaseTokenToDB(firebaseToken, userFromResponse.getId()), Response.sucOrNot(Response.SUCCESS_FIRE_ADD));
	                	return;
	                	
	                case Queries.DISTRIBUTED_TASK:
	                	isResponseSuccess(taskWorker.changeStatusDistrib(taskFromResponse), Response.sucOrNot(Response.SUCCESS_UPDATE_TASK));
	                	return;
	                	
	                case Request.LOGOUT:
	                	isResponseSuccess(userWorker.logout(tokenFromResponse), Response.sucOrNot(Response.SUCCESS_LOGOUT));
	                    return;
	                    
	                case Request.ADD_TASK_TO_SERVER:
	                	isResponseSuccess(taskWorker.create(taskFromResponse), Response.sucOrNot(Response.ADD_TASK_SUCCESS));
	                	return;
	                    
	                case Request.WANT_SOME_COMMENTS:
	                	boolean isCommentAddSuccess = taskWorker.getCommentsByTaskId(taskFromResponse);
	                	if(isCommentAddSuccess){
	                		Response commentResponse = new Response.Builder()
	                				.comments(commentsManager.getCommentsByTaskId(taskFromResponse.getId()))
	                				.response(Response.ADD_COMMENTS)
	                				.contacts(contactsManager.getContactsByAddressId(taskFromResponse.getAddressId()))
	                				.build();
	                		isResponseSuccess(isCommentAddSuccess, commentResponse);
	                		commentResponse =null;
	                	}
	                    return;
	                case Request.CHANGE_PERMISSION_PLEASE:
	                	isResponseSuccess(userWorker.editUserRole(userRoleFromResponse), Response.sucOrNot(Response.UPDATE_USER_ROLE_SUCCESS));
	                    return;

	                case Request.GIVE_ME_LAST_USERS_COORDS:
	                	boolean isSuccessCoords = coordsWorker.getLatestCoordes();
	                	if(isSuccessCoords){
	                		Response coordesResponse = new Response.Builder()
	                				.userCoordsList(coordsWorker.getCoordes())
	                				.response(Response.ADD_LATEST_USER_COORDS)
	                				.build();
	                		isResponseSuccess(isSuccessCoords, coordesResponse);
	                		coordesResponse = null;
	                	}
	                    return;
	                    
	                case Request.GET_USER_COORDES_PER_DAY:
	                	System.out.println("hello");
	                	Date userCoordesDate = new Gson().fromJson(read, Request.class).getDate();
	                	int userId = new Gson().fromJson(read, Request.class).getUserId();
	                	Response coordesPerDayResponse = new Response.Builder()
	                			.userCoordsList(new DBWorker().getUserCoordesPerDay(userCoordesDate, userId))
	                			.response(Response.ADD_COORDES_PER_DAY)
	                			.build();
	                	isResponseSuccess(true, coordesPerDayResponse);
	                	coordesPerDayResponse = null;
	                	return;

	                case Request.REMOVE_TASK:
	                	isResponseSuccess(taskWorker.remove(taskFromResponse), Response.sucOrNot(Response.SUCCESS_REMOVE_TASK));
	                    return;

	                case Request.REMOVE_USER:
	                	isResponseSuccess(userWorker.remove(userFromResponse), Response.sucOrNot(Response.SUCCESS_REMOVE_USER));
	                    return;

	                case Request.GIVE_ME_ADDRESSES_PLEASE:
	                	boolean addressSuccessOrNot = addressWorker.getAllAddresses();
	                	if(addressSuccessOrNot){
	                		Response addressResponse = new Response.Builder()
	                				.addresses(addressWorker.getAddresses())
	                				.response(Response.ADD_ADDRESSES_TO_USER)
	                				.build();
	                		isResponseSuccess(addressSuccessOrNot, addressResponse);
	                		addressResponse = null;
	                	}
	                    return;

	                case Request.ADD_NEW_ROLE:
	                    addNewRole();
	                    return;

	                case Request.ADD_COORDS:
	                	isResponseSuccess(coordsWorker.addCoords(coordsFromResponse), Response.sucOrNot(Response.SUCCESS_ADD_COORDS));
	                    return;

	                case Request.UPDATE_TASK:
	                	isResponseSuccess(taskWorker.edit(taskFromResponse), Response.sucOrNot(Response.SUCCESS_UPDATE_TASK));
	                    return;

	                case Request.ADD_NEW_USER:
	                	boolean isSuccess = userWorker.create(userFromResponse);
	                	if(isSuccess){
	                		User newUser = userWorker.getCreatedUser();
	                		Response userResponse = new Response.Builder()
	                				.user(newUser)
	                				.response(Response.SUCCESS_ADD_USER)
	                				.build();
	                		isResponseSuccess(isSuccess, userResponse);
	                		newUser = null;
	                	}
	                	
	                    return;

	                    default:
	            }
	        }
	    }
	 
	 private void isResponseSuccess(boolean isSuccess, Response response){
		 if(isSuccess){
			 write = parserJson.parseToJson(response);
		 }else 
			 write = parserJson.parseToJson(Response.sucOrNot(Response.NOT_SUCCESS));
	 }

     private void updateTasksForUserOrAdmin(){
    	 User user = parser.parseFromJson(read).getUser();
    	 //отдаем админу или диспетчеру все задания повторно
    	 if(user.getRole().equals(UserEnum.ADMIN_ROLE)||user.getRole().equals(UserEnum.MANAGER_ROLE)){
    		 //берем заявки из бд
    		 dbWorker.getAllTasksFromDb();
    		 Response adminTasks = new Response.Builder()
    				 .taskList(tasksManager.getTasks())
    				 .response(Response.SUCCESS_UPDATE_TASKS)
    				 .build();
    		 //отдаем админу спарсенные задания
    		 write = parser.successUpdateTasks(adminTasks); 
    		 adminTasks = null;
    	 }
    	//если юзерроль - юзер, отдаем ему его заявки
    	 else if(user.getRole().equals(UserEnum.USER_ROLE)){
    		 //берем заявки из бд по id
    		 dbWorker.getTasksFromDbByUserId(String.valueOf(user.getId()));
    		 Response usersTasks = new Response.Builder()
    				 .taskList(tasksManager.getTasksByUserId(user))
    				 .response(Response.SUCCESS_UPDATE_TASKS)
    				 .build();
    		 //отдаем юзеру все спарсенные задания по его айди
    		 write = parser.successUpdateTasks(usersTasks);
    		 usersTasks = null;
    	 }
     }
	    
	     private void addNewRole(){
	         if(dbWorker.insertUserRole(parser.parseFromJson(read).getUserRole()))
	         write = parser.parseSuccessInsertUserRole();
	         else write = parser.notSuccess();
	     }
}