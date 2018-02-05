package database;

import entities.*;
import managers.AddressManager;
import managers.CommentsManager;
import managers.ContactsManager;
import managers.TasksManager;
import managers.TokenManager;
import managers.UserCoordsManager;
import managers.UsersManager;
import network.Response;
import storage.DateUtil;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DBWorker{
    private DBStart dbStart;
    private List<User> userList = new ArrayList<User>();
    private List<Comment> comments = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<UserRole> userRoles = new ArrayList<>();
    private List<UserCoords>userCoordsList = new ArrayList<>();
    private Statement statement;
    private Queries queries;
    Logger log = Logger.getLogger(DBWorker.class.getName());
    private UsersManager usersManager = UsersManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    private CommentsManager commentsManager = CommentsManager.INSTANCE;
    private UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;
    private ContactsManager contactsManager = ContactsManager.Instance;
    private DateUtil dateUtil = new DateUtil();
    ResultSet resultSet;
    TokenManager tokenManager = TokenManager.instance;
    
    public DBWorker(){
    	this.dbStart = DBStart.instance;
    	this.queries = new Queries();
    }
    
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<UserCoords> getUserCoordsList() {
        return userCoordsList;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void getTasksFromDbByUserId(String id){
        try {
        	tasksManager.removeUsersTasks();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectAllByUserId(id));
            while (resultSet.next()) {
            	Task task = new Task.Builder()
            			.id( resultSet.getInt(Queries.ID))
        				.created(dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)))
        				.importance(resultSet.getString(Queries.IMPORTANCE))
        				.body(resultSet.getString(Queries.BODY))
        				.status(resultSet.getString(Queries.STATUS))
        				.type(resultSet.getString(Queries.TYPE))
        				.doneTime(dateUtil.parseFromBigDate(resultSet.getString(Queries.DONE_TIME)))
        				.userId(resultSet.getInt(Queries.USER_ID))
        				.addressId(resultSet.getInt(Queries.ADDRESS_ID))
        				.address(resultSet.getString(Queries.ADDRESS))
        				.orgName(resultSet.getString(Queries.ORG_NAME))
        				.build();
                tasksManager.addToUsersTasks(task);
            }
            resultSet = statement.executeQuery(queries.selectDoneTodayForUser(id));
            while (resultSet.next()) {
            	Task task = new Task.Builder()
            			.id( resultSet.getInt(Queries.ID))
        				.created(dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)))
        				.importance(resultSet.getString(Queries.IMPORTANCE))
        				.body(resultSet.getString(Queries.BODY))
        				.status(resultSet.getString(Queries.STATUS))
        				.type(resultSet.getString(Queries.TYPE))
        				.doneTime(dateUtil.parseFromBigDate(resultSet.getString(Queries.DONE_TIME)))
        				.userId(resultSet.getInt(Queries.USER_ID))
        				.addressId(resultSet.getInt(Queries.ADDRESS_ID))
        				.address(resultSet.getString(Queries.ADDRESS))
        				.orgName(resultSet.getString(Queries.ORG_NAME))
        				.build();
            	tasksManager.addToUsersTasks(task);
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }finally{
        	statementClose();
        } 
    }
    
    
    
    public boolean getDoneTasks(){
    	try {
        	tasksManager.removeAllDone();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectAllDoneTasks());
            while (resultSet.next()) {
            	Task task = new Task.Builder()
            			.id( resultSet.getInt(Queries.ID))
        				.created(dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)))
        				.importance(resultSet.getString(Queries.IMPORTANCE))
        				.body(resultSet.getString(Queries.BODY))
        				.status(resultSet.getString(Queries.STATUS))
        				.type(resultSet.getString(Queries.TYPE))
        				.doneTime(dateUtil.parseFromBigDate(resultSet.getString(Queries.DONE_TIME)))
        				.userId(resultSet.getInt(Queries.USER_ID))
        				.addressId(resultSet.getInt(Queries.ADDRESS_ID))
        				.address(resultSet.getString(Queries.ADDRESS))
        				.orgName(resultSet.getString(Queries.ORG_NAME))
        				.build();
                tasksManager.addDoneTask(task);
            }
            return true;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean getTodayDoneTasks(){
    	try {
        	tasksManager.removeAllTodayDone();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectAllDoneTodayTasks());
            while (resultSet.next()) {
            	Task task = new Task.Builder()
            			.id( resultSet.getInt(Queries.ID))
        				.created(dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)))
        				.importance(resultSet.getString(Queries.IMPORTANCE))
        				.body(resultSet.getString(Queries.BODY))
        				.status(resultSet.getString(Queries.STATUS))
        				.type(resultSet.getString(Queries.TYPE))
        				.doneTime(dateUtil.parseFromBigDate(resultSet.getString(Queries.DONE_TIME)))
        				.userId(resultSet.getInt(Queries.USER_ID))
        				.addressId(resultSet.getInt(Queries.ADDRESS_ID))
        				.address(resultSet.getString(Queries.ADDRESS))
        				.orgName(resultSet.getString(Queries.ORG_NAME))
        				.build();
                tasksManager.addUniqueDoneTodayTask(task);
            }
            return true;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    

    public boolean getFirebaseTokens(){
    	try {
        	tokenManager.clearFirebaseTokens();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getAllFirebaseTokens());
            while (resultSet.next()) {
            	String token = resultSet.getString(Queries.FIREBASE_TOKEN);
            	int userId = resultSet.getInt(Queries.USER_ID_FIRE);
                tokenManager.addFirebaseToken(token, userId);
            }
            return true;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    
    public List<User> getUsersForSimpleUser(){
        List<User> userList1 = new ArrayList<>();
        try {
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectUsersForSimpleUser());
            while (resultSet.next()){
            	User user = new User.Builder()
            			.id(resultSet.getInt(Queries.ID))
            			.login(resultSet.getString(Queries.USER_NAME))
            			.fio(resultSet.getString(Queries.USER_FIO))
            			.phone(resultSet.getString(Queries.USER_TLF))
            			.email(resultSet.getString(Queries.USER_EMAIL))
            			.build();
                userList1.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList1;
    }

    public boolean getAllAddresses(){
    	addressManager.removeAll();
        try {
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getAddresses());
            while (resultSet.next()){
                addressManager.addAddress(new Address(
                        resultSet.getInt(Queries.ID),
                        resultSet.getString(Queries.ORG_NAME),
                        resultSet.getString(Queries.ADDRESS),
                        resultSet.getString(Queries.COORDS_LAT),
                        resultSet.getString(Queries.COORDS_LON)
                ));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    	}finally{
        	statementClose();
        }
    }

    public void getAllTasksFromDb(){
        try {
        	tasksManager.removeAll();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectAllTasksObjects());
            while (resultSet.next()) {
            	Task task = new Task.Builder()
            			.id( resultSet.getInt(Queries.ID))
        				.created(dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)))
        				.importance(resultSet.getString(Queries.IMPORTANCE))
        				.body(resultSet.getString(Queries.BODY))
        				.status(resultSet.getString(Queries.STATUS))
        				.type(resultSet.getString(Queries.TYPE))
        				.doneTime(dateUtil.parseFromBigDate(resultSet.getString(Queries.DONE_TIME)))
        				.userId(resultSet.getInt(Queries.USER_ID))
        				.addressId(resultSet.getInt(Queries.ADDRESS_ID))
        				.address(resultSet.getString(Queries.ADDRESS))
        				.orgName(resultSet.getString(Queries.ORG_NAME))
        				.build();
                tasksManager.addTask(task);
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }finally{
        	statementClose();
        }
    }
    
    public boolean updateContact(ContactOnAddress contact){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateContact(contact));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean updateAddress(Address address){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateAddress(address));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean removeTask(Task task){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.removeCommentsByTaskId(task));
            statement.execute(queries.removeTask(task));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean removeContact(ContactOnAddress contact){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.removeContact(contact));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean removeAddress(Address address){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.removeContactsByAddressId(address));
            statement.execute(queries.removeAddress(address));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean addFirebaseTokenToDB(String token, int userId){
    	for (Map.Entry<String, Integer> entry : tokenManager.getFirebaseTokens().entrySet()) {
    		if(entry.getValue()==userId){
    			try {
    	            statement = dbStart.getConnection().createStatement();
    	            statement.execute(queries.updateFirebaseToken(token, userId));
    	            return true;
    	        } catch (SQLException e) {
    	            log.error("try to add dublicate firebaseToken");
    	            return false;
    	        }finally{
    	        	statementClose();
    	        }
    		}else {
    			try {
    	            statement = dbStart.getConnection().createStatement();
    	            statement.execute(queries.addFirebaseToken(token, userId));
    	            return true;
    	        } catch (SQLException e) {
    	            log.error("try to add dublicate firebaseToken");
    	            return false;
    	        }finally{
    	        	statementClose();
    	        }
    		}
    	}
        return false;
    }
    

    public boolean addContactInDB(ContactOnAddress contact){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.addContactInDB(contact));
            return true;
        } catch (SQLException e) {
            log.error("error add contact in db");
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean addAddressInDB(Address address){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertAddress(address));
            return true;
        } catch (SQLException e) {
            log.error("error add address in db");
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean removeUser(User user){
    	if(!user.getLogin().equals(Response.NOT_DISTRIBUTED)){
    		try {
                statement = dbStart.getConnection().createStatement();
                statement.execute(queries.removeCommentsByUserId(user));
                statement.execute(queries.removeCoordesByUserId(user));
                statement.execute(queries.removeUserRoleByUserId(user));
                statement.execute(queries.removeUser(user));
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally{
            	statementClose();
            }
    	}else {
    		return false;
    	}
        
    }

    public boolean getLatestUserCoords(){
        try {
        	userCoordsManager.removeAll();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getLastUserCoords());
            while (resultSet.next()){
                UserCoords coords = new UserCoords(
                        resultSet.getInt(Queries.ID),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getDouble(Queries.COORDS_LAT),
                        resultSet.getDouble(Queries.COORDS_LON),
                        dateUtil.parseFromBigDate(resultSet.getString(Queries.TS)),
                        resultSet.getString(Queries.ADDRESS)
                );
                userCoordsManager.addUserCoords(coords);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public List<UserCoords> getUserCoordesPerDay(Date userCoordesDate, int userId){
        try {
        	List<UserCoords>coordesList = new ArrayList<>();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getUserCoordesPerDay(userCoordesDate, userId));
            while (resultSet.next()){
                UserCoords coords = new UserCoords(
                        resultSet.getInt(Queries.ID),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getDouble(Queries.COORDS_LAT),
                        resultSet.getDouble(Queries.COORDS_LON),
                        dateUtil.parseFromBigDate(resultSet.getString(Queries.TS)),
                        resultSet.getString(Queries.ADDRESS)
                );
                coordesList.add(coords);
            }
            return coordesList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }finally{
        	statementClose();
        }
    }

    public boolean updateUserRole(UserRole userRole){
        try {

            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateUserRoleById(userRole));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean editUser(User user){
    	try {
			statement = dbStart.getConnection().createStatement();
			statement.execute(queries.editUser(user));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public boolean insertContactOnAddress(ContactOnAddress contact){
    	try {
			statement = dbStart.getConnection().createStatement();
			statement.execute(queries.insertContact(contact));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean getContactsFromDB(){
    	try {
        	contactsManager.removeAll();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getAllContactsForAddress());
            while (resultSet.next()){
            	ContactOnAddress contact = new ContactOnAddress.Builder()
            			.id(resultSet.getInt(Queries.ID))
            			.name(resultSet.getString(Queries.NAME))
            			.post(resultSet.getString(Queries.POST))
            			.phone(resultSet.getString(Queries.PHONE))
            			.email(resultSet.getString(Queries.USER_EMAIL))
            			.apartments(resultSet.getString(Queries.APARTMENTS))
            			.addressId(resultSet.getInt(Queries.ADDRESS_NAME_ID))
            			.build();
                contact.addEmail(contact.getEmail());
                contact.addPhone(contact.getPhone());
                contactsManager.addContact(contact);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean insertUserRole(UserRole userRole){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertUserRole(userRole));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean insertUserCoords(UserCoords userCoords){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.addUserCoords(userCoords));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean updateTask(Task task){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateTask(task));
            statement.execute(queries.deleteLineBreaks(task.getId()));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean editAddressCoordinates(Address address){
    	try {
			statement = dbStart.getConnection().createStatement();
			statement.execute(queries.editAddressCoordinates(address));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			statementClose();
		}
    }

    public boolean insertTask(Task task){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertTask(task));
            statement.execute(queries.deleteLineBreaks(task.getId()));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public void getAllUsersFromDB(){
        try {
        	usersManager.removeAll();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectAllUsers());
            while (resultSet.next()){
            	UserRole userRole = new UserRole(
                    resultSet.getInt(Queries.ID_USER_ROLE),
                        resultSet.getBoolean(Queries.MAKE_NEW_USER),
                        resultSet.getBoolean(Queries.MAKE_TASKS),
                        resultSet.getBoolean(Queries.CORRECTION_TASK),
                        resultSet.getBoolean(Queries.MAKE_ADDRESS),
                        resultSet.getBoolean(Queries.WATCH_ADDRESS),
                        resultSet.getBoolean(Queries.CORRECTION_STATUS),
                        resultSet.getBoolean(Queries.MAKE_EXECUTOR),
                        resultSet.getBoolean(Queries.CORRECTION_EXECUTOR),
                        resultSet.getBoolean(Queries.WATCH_TASKS),
                        resultSet.getBoolean(Queries.COMMENT_TASKS),
                        resultSet.getBoolean(Queries.CHANGE_PASSWORD),
                        resultSet.getInt(Queries.ID));
            	
            	User user = new User.Builder()
            			.id(resultSet.getInt(Queries.ID))
            			.login(resultSet.getString(Queries.USER_NAME))
            			.password(resultSet.getString(Queries.USER_PWD))
            			.fio(resultSet.getString(Queries.USER_FIO))
            			.role(resultSet.getString(Queries.USER_ROLE))
            			.phone(resultSet.getString(Queries.USER_TLF))
            			.email(resultSet.getString(Queries.USER_EMAIL))
            			.userRole(userRole)
            			.build();
            	usersManager.addUser(user);
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        }finally{
        	statementClose();
        }
    }

    public boolean addNewUserAndUserRole(User user){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.addNewUser(user));
            statement.execute(queries.insertUserRole(user.getUserRole()));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean getCommentsForDoneTasks(){
        try {
        	commentsManager.removeDoneComments();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.getAllDoneComments());
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getInt(Queries.ID),
                        dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)),
                        resultSet.getString(Queries.COMMENT_BODY),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getInt(Queries.TASKS_ID_TASKS)
                );
                commentsManager.addDoneComment(comment);;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }
    
    public boolean getCommentsById(int taskId){
        try {
        	commentsManager.removeAll();
            statement = dbStart.getConnection().createStatement();
            resultSet = statement.executeQuery(queries.selectCommentsByTask(taskId));
            while (resultSet.next()){
                Comment comment = new Comment(
                        resultSet.getInt(Queries.ID),
                        dateUtil.parseFromBigDate(resultSet.getString(Queries.CREATED)),
                        resultSet.getString(Queries.COMMENT_BODY),
                        resultSet.getInt(Queries.USER_ID_USERS),
                        resultSet.getInt(Queries.TASKS_ID_TASKS)
                );
                commentsManager.addComment(comment);
                comments.add(comment);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean updateTaskStatus(String taskStatus, Comment comment){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.updateTaskStatus(taskStatus, comment.getTaskId()));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    public boolean insertComment(Comment comment){
        try {
            statement = dbStart.getConnection().createStatement();
            statement.execute(queries.insertComment(comment));
            return true;
        } catch (SQLException e) {
        	System.out.println("cannot add comment " +comment);
            e.printStackTrace();
            return false;
        }finally{
        	statementClose();
        }
    }

    private void statementClose(){
    	try {
    		if(resultSet!=null){
    			resultSet.close();
    		}
    		if(statement!=null){
    			statement.close();
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public boolean addProgramCommentToTask(User creator, Task task, String commentBody){
   	 Comment comment = new Comment(
   			 dateUtil.getCurrentDateForCreateOrUpdateTask(),
   			 commentBody + creator.getLogin(), 
   			 creator.getId(),
   			 task.getId()
   			 );
   	 if(insertComment(comment)){
   		 return true;
   	 }else {
   		 return false;
   	 }
    }
    
    public boolean addProgramCommentToTaskFromManager(User creator, Task task, String commentBody){
      	User executor = usersManager.getUserById(task.getUserId()); 
    	Comment comment = new Comment(
      			 dateUtil.getCurrentDateForCreateOrUpdateTask(),
      			 commentBody + executor.getLogin(), 
      			 creator.getId(),
      			 task.getId()
      			 );
    	if(insertComment(comment)){
      		 return true;
      	 }else {
      		 return false;
      	 }
       }
       
    public boolean addProgramCommentToTaskWithoutCreator(Task task, String commentBody){
      	 Comment comment = new Comment(
      			 dateUtil.getCurrentDateForCreateOrUpdateTask(),
      			 commentBody + usersManager.getUserById(task.getUserId()).getLogin(), 
      			 task.getUserId(),
      			 task.getId()
      			 );
      	if(insertComment(comment)){
      		 return true;
      	 }else {
      		 return false;
      	 }
       }
    
	public void changeDateTask (Task task){
			 task.setCreated(dateUtil.parseFromClientToServerDate(task.getCreated()));
	   	 task.setDoneTime(dateUtil.parseFromClientToServerDate(task.getDoneTime()));
	}	
}
