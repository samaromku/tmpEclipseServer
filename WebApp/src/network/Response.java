package network;

import entities.*;
import storage.Token;

import java.util.List;

public class Response {
    private String response;
    private User user;
    private Task task;
    private List<Task> taskList;
    private List<User> userList;
    private List<Comment> comments;
    private List<Address> addresses;
    private List<ContactOnAddress>contacts;
    private List<UserCoords>userCoordsList;
    private UserRole userRole;
    private Token token;
    public static final String ADD_TASKS_TO_USER = "addTasksToUser";
    public static final String GET_AWAY_GUEST = "getAwayGuest";
    public static final String ADD_ACTION_ADMIN = "addActionAdmin";
    public static final String ADD_TASK_SUCCESS = "add_task_success";
    public static final String UPDATE_USER_ROLE_SUCCESS = "update_user_role_success";
    public static final String INSERT_USER_ROLE_SUCCESS = "insert_user_role_success";
    public static final String ADD_COMMENTS = "add_comments";
    public static final String ADD_COMMENT_SUCCESS = "add_comment_success";
    public static final String ADD_ADDRESSES_TO_USER = "add_addresses_to_user";
    public static final String ADD_LATEST_USER_COORDS = "add_latest_user_coords";
    public static final String ADD_COORDES_PER_DAY = "add_coordes_per_day";
    public static final String SUCCESS_ADD_USER = "success_add_user";
    public static final String SUCCESS_ADD_COORDS = "success_add_coords";
    public static final String SUCCESS_UPDATE_TASK = "success_update_task";
    public static final String NOT_SUCCESS = "not_success";
    public static final String NOT_SUCCESS_AUTH = "not_success_auth";
    public static final String SUCCESS_LOGOUT = "success_logout";
    public static final String SUCCESS_REMOVE_USER = "success_remove_user";
    public static final String SUCCESS_EDIT_USER = "success_edit_user";
    public static final String SUCCESS_REMOVE_TASK = "success_remove_task";
    public static final String SUCCESS_UPDATE_TASKS = "success_update_tasks";
    public static final String SUCCESS_FIRE_ADD = "success_fire_add";
    public static final String NOT_DISTRIBUTED = "Не назначена";

    private Response(){}
    
    private static final Response instance = new Response();
    
    public static Response getInstance(){
    	return instance;
    }
    
    public static Response sucOrNot(String response){
    	getInstance().response = response;
    	return getInstance();
    }
 
    public String getResponse() {
        return response;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }
    
    public static class Builder{
    	private String response;
        private User user;
        private Task task;
        private UserRole userRole;
        private Token token;
        private List<Task> taskList;
        private List<User> userList;
        private List<Comment> comments;
        private List<Address> addresses;
        private List<UserCoords>userCoordsList;
        private List<ContactOnAddress>contacts;

    	
        public Builder response(String val){
        	response = val;
        	return this;
        }
        
        public Builder user(User val){
        	user = val;
        	return this;
        }
        
        
        public Builder task(Task val){
        	task = val;
        	return this;
        }
        
        public Builder userRole(UserRole val){
        	userRole = val;
        	return this;
        }
        
        public Builder token(Token val){
        	token = val;
        	return this;
        }
        
        public Builder taskList(List<Task> val){
        	taskList = val;
        	return this;
        }
        
        public Builder userList(List<User> val){
        	userList = val;
        	return this;
        }
        
        public Builder comments(List<Comment> val){
        	comments = val;
        	return this;
        }
        
        public Builder addresses(List<Address> val){
        	addresses = val;
        	return this;
        }
        
        public Builder userCoordsList(List<UserCoords> val){
        	userCoordsList = val;
        	return this;
        }
        
        public Builder contacts(List<ContactOnAddress> val){
        	contacts = val;
        	return this;
        }
        
        public Response build(){
	    	return new Response(this);
	    }	    
    }
    
    private Response(Builder builder){
    	this.response = builder.response;
    	this.user = builder.user;
    	this.task = builder.task;
    	this.userRole = builder.userRole;
    	this.token = builder.token;
    	this.taskList = builder.taskList;
    	this.userList = builder.userList;
    	this.comments = builder.comments;
    	this.addresses = builder.addresses;
    	this.userCoordsList = builder.userCoordsList;
    	this.contacts = builder.contacts;
    }
}
