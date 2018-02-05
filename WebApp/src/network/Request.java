package network;

import java.util.Date;

import entities.*;
import storage.Token;

public class Request {
    private String request;
    private User user;
    private Task task;
    private UserRole userRole;
    private Comment comment;
    private UserCoords userCoords;
    private Token token;
    private String fireBase;
    private int userId;
    private Date dateForUserCoordes;
    public static final String ADD_TASK_TO_SERVER = "add_task_to_server";
    public static final String WANT_SOME_COMMENTS = "give_me_comments_by_task_id";
    public static final String CHANGE_PERMISSION_PLEASE = "change_permission_please";
    public static final String AUTH = "auth";
    public static final String LOGOUT = "logout";
    public static final String GIVE_ME_ADDRESSES_PLEASE = "give_me_addresses_please";
    public static final String ADD_NEW_USER = "add_new_user";
    public static final String ADD_NEW_ROLE = "add_new_role";
    public static final String ADD_COORDS = "add_coords";
    public static final String UPDATE_TASK = "update_task";
    public static final String GIVE_ME_LAST_USERS_COORDS = "give_me_last_users_coords";
    public static final String GET_USER_COORDES_PER_DAY = "get_user_coordes_per_day";
    public static final String REMOVE_USER = "remove_user";
    public static final String REMOVE_TASK = "remove_task";
    public static final String UPDATE_TASKS = "update_tasks";
    
    public int getUserId() {
		return userId;
	}

	public Date getDate() {
		return dateForUserCoordes;
	}

	public UserCoords getUserCoords() {
        return userCoords;
    }
    
    public Token getToken(){
    	return this.token;
    }
    
    public String getFirebaseToken(){
    	return this.fireBase;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public Comment getComment() {
        return comment;
    }
}