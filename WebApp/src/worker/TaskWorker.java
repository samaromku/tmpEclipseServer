package worker;

import java.util.Map;

import org.apache.log4j.Logger;


import database.DBWorker;
import database.Queries;
import entities.Address;
import entities.Comment;
import entities.Task;
import entities.User;
import interfaces.OnPutMessage;
import managers.AddressManager;
import managers.MessageHandler;
import managers.TasksManager;
import managers.TokenManager;
import managers.UsersManager;
import network.Response;
import network.TaskEnum;
import network.UserEnum;
import notify.MessageSender;
import storage.DateUtil;
import storage.JsonParser;
import utils.DateParser;

public class TaskWorker {
	private Logger log = Logger.getLogger(TaskWorker.class.getName());
	private User creator;
	private DBWorker dbWorker;
	private DateParser dateParser;
	private TasksManager tasksManager = TasksManager.INSTANCE;
	private DateUtil dateUtil = new DateUtil();
	private AddressManager addressManager = AddressManager.INSTANCE;
	private MessageHandler messageHandler = MessageHandler.instance;
	private UsersManager usersManager = UsersManager.INSTANCE;
	MessageSender messageSender = new MessageSender();
	JsonParser parser = new JsonParser();
	TokenManager tokenManager = TokenManager.instance;
	
	public TaskWorker(User creator) {
		this.creator = creator;
		this.dbWorker = new DBWorker();
		this.dateParser = new DateParser();
	}

	public boolean create(Task task) {
		changeDateTask(task);
        if(dbWorker.insertTask(task)){
        	setAddress(task);
        	setDoneAndCreatedTime(task);
//        	addMessageToQueueResponseMake(creator.getLogin() + " создал заявку: " + task.getId(), task);
        	if(task.getStatus().equals(TaskEnum.NEW_TASK)){
        		parseResponseToJsonAndSend(creator.getLogin() + " создал заявку: " + task.getId(), task, creator);
        	}else {
        		parseResponseToJsonAndSendtoOneUser(creator.getLogin() + " создал заявку: " + task.getId(), task, usersManager.getUserById(task.getUserId()));
        	}
        	tasksManager.addTask(task);
       	 	if(dbWorker.addProgramCommentToTask(creator, task, Queries.ADD_COMMENT_CREATE_TASK)){
       	 		return true;
       	 	}
       	 	else return false;
        }
        else 
        	return false;
	}

	public boolean remove(Task task) {
		if(dbWorker.removeTask(task)){
			tasksManager.removeTask(task);
			addMessageToQueueResponseMake(creator.getLogin() + " удалил заявку: " + task.getId(), task);
			return true;
        }else {
        	return false;
        }
	}

	public boolean edit(Task task) {
		changeDateTask(task);
		if(dbWorker.updateTask(task)){
			setAddress(task);
			setDoneAndCreatedTime(task);
			parseResponseToJsonAndSendtoOneUser(creator.getLogin() + " изменил заявку: " + task.getId(), task, usersManager.getUserById(task.getUserId()));
//			parseResponseToJsonAndSend(creator.getLogin() + " изменил заявку: " + task.getId(), task);
			tasksManager.updateTask(task);
        	dbWorker.addProgramCommentToTask(creator, task, Queries.ADD_COMMENT_CHANGED_TASK);
        	return true;
	}
			return false;
	}
	
	private void setAddress(Task task){
		Address address = addressManager.getAddressById(task.getAddressId());
    	task.setAddress(address.getAddress());
    	task.setOrgName(address.getName());
	}
	
	private void setDoneAndCreatedTime(Task task){
		task.setCreated(dateUtil.parseToCreatedTask(task.getCreated()));
		task.setDoneTime(dateUtil.parseToCreatedTask(task.getDoneTime()));
	}
	
	public boolean changeStatusTask(String taskStatus, String programComment, Comment comment){
		if(dbWorker.updateTaskStatus(taskStatus, comment)){
			comment.setTs(dateUtil.getCurrentDateForCreateOrUpdateTask());
			comment.setBody(programComment+creator.getLogin()+"\nПричина: " + comment.getBody());
		        if(dbWorker.insertComment(comment)){
		        	if(taskStatus.equals(TaskEnum.CONTROL_TASK)){
		        		addMessageToQueueResponseMake(creator.getLogin() + " выполнил заявку " + comment.getTaskId(), tasksManager.getById(comment.getTaskId()));
		        	}else if(taskStatus.equals(TaskEnum.NEED_HELP)){
		        		parseResponseToJsonAndSend(creator.getLogin() + " нужна помощь с заявкой " + comment.getTaskId(), tasksManager.getById(comment.getTaskId()), creator);
		        	}else if(taskStatus.equals(TaskEnum.DISAGREE_TASK)){
		        		parseResponseToJsonAndSend(creator.getLogin() + " отказывается от заявки " + comment.getTaskId(), tasksManager.getById(comment.getTaskId()), creator);
		        	}else if(taskStatus.equals(TaskEnum.DOING_TASK)){
		        		addMessageToQueueResponseMake(creator.getLogin() + " начал выполнять " + comment.getTaskId(), tasksManager.getById(comment.getTaskId()));
		        	}
		        	
		        	return true;
		        }
		        else{
		        	return false;
		        }	
			}
		else {
			return false;
		}
	}
	
	public boolean addComment(Comment comment, String programComment){
		if(dbWorker.insertComment(comment)){
			addMessageToQueueResponseMake(creator.getLogin() + " добавил комментарий " + comment.getTaskId(), tasksManager.getById(comment.getTaskId()));
			return true;
		}else 
			return false;
	}
	
	public boolean changeStatusDone(Task task){
		changeStatusDate(task);
    	if (dbWorker.updateTask(task)){
    		parseResponseToJsonAndSendtoOneUser(creator.getLogin() + " поменял статус заявки: " + task.getId() + ", новый статус: " + task.getStatus(), task, usersManager.getUserById(task.getUserId()));
//    		parseResponseToJsonAndSend(creator.getLogin() + " поменял статус заявки: " + task.getId() + ", новый статус: " + task.getStatus(), task);
    		dbWorker.addProgramCommentToTask(creator, task, Queries.ADD_COMMENT_DONE_TASK);
    		tasksManager.updateTask(task);
    		return true;
    	}else {
    		return false;
    	}
	}
	
	public boolean getCommentsByTaskId(Task task){
		if(dbWorker.getCommentsById(task.getId())){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean changeStatusDistrib(Task task){
		changeStatusDate(task);
    	if (dbWorker.updateTask(task)){
    		if(creator.getId()==task.getUserId()){
    			dbWorker.addProgramCommentToTaskWithoutCreator(task, Queries.ADD_COMMENT_DISTRIBUTED_TASK);
    			addMessageToQueueResponseMake(usersManager.getUserById(task.getUserId()).getLogin() + ", взял(а) себе заявку", task);
    		} else {
    			dbWorker.addProgramCommentToTaskFromManager(creator, task, Queries.ADD_COMMENT_DISTRIBUTED_TASK_FROM_MANAGER);
    			parseResponseToJsonAndSendtoOneUser("Исполнителем назначен: " + usersManager.getUserById(task.getUserId()).getLogin() + ", назначил: " + creator.getLogin(), task, usersManager.getUserById(task.getUserId()));
    		}
    		return true;
    	}else {
    		return false;
    	}
	}
	
	public boolean changeStatusDoing(Task task){
		changeStatusDate(task);
    	if (dbWorker.updateTask(task)){
			dbWorker.addProgramCommentToTaskWithoutCreator(task, Queries.ADD_COMMENT_DOING_TASK);
			addMessageToQueueResponseMake(usersManager.getUserById(task.getUserId()).getLogin() + ", приступил(а) к выполнению", task);
    		return true;
    	}else {
    		return false;
    	}
	}
	
	private void changeStatusDate (Task task){
		 task.setCreated(dateParser.parseCreatedYYYYfromDD(task.getCreated()));
		 task.setDoneTime(dateParser.parseDoneDateSiteApp(task.getDoneTime()));
	}
	
	
	private void changeDateTask (Task task){
		 task.setCreated(dateParser.parseCreateDateSiteApp(task.getCreated()));
		 task.setDoneTime(dateParser.parseDoneDateSiteApp(task.getDoneTime()));
	}
	
	private void sendMessageToLogAndHandler(String message, User creator){
		for (Map.Entry<String, Integer> e : tokenManager.getFirebaseTokens().entrySet()) {
			if(creator.getId()!=e.getValue()){
				messageSender.post(e.getKey(), message, creator.getLogin());
			}
		}
		if(!creator.getRole().equals(UserEnum.MANAGER_ROLE)){
			messageHandler.addMessageToQueue(message);	
		}
	}
	
	
	
	private void addMessageToQueueResponseMake(String message, Task task){
		Response response = new Response.Builder()
				.response(message)
				.task(task)
				.build();
		User user = usersManager.getUserById(task.getUserId());
		if(creator!=null && user!=null){
//			if(!creator.getLogin().equals(user.getLogin()) && !creator.getRole().equals(UserEnum.MANAGER_ROLE)){
				if(!creator.getRole().equals(UserEnum.MANAGER_ROLE)){
				messageHandler.addMessageToQueue(parser.parseToJson(response));
			}
		}
	}

	private void parseResponseToJsonAndSend(String str, Task task, User creator){
		Response response = new Response.Builder()
				.response(str)
				.task(task)
				.build();
		String message = parser.parseToJson(response);
		log.info("parseResponseToJsonAndSend " + message);
		sendMessageToLogAndHandler(message, creator);
		response = null;
	}
	
	private void parseResponseToJsonAndSendtoOneUser(String str, Task task, User user){
		Response response = new Response.Builder()
				.response(str)
				.task(task)
				.build();
		String message = parser.parseToJson(response);
		log.info("parseResponseToJsonAndSendtoOneUser " + message);
		for (Map.Entry<String, Integer> e : tokenManager.getFirebaseTokens().entrySet()) {
			if(e.getValue()==user.getId()){
				if(creator.getId()!=user.getId()){
					log.info("parseResponseToJsonAndSendtoOneUser iterator " + message );
					messageSender.post(e.getKey(), message, creator.getLogin());
				}
			}
		}
		addMessageToQueueResponseMake(str, task);
		response = null;
	}
}
