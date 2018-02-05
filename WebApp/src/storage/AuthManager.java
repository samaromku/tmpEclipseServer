package storage;

import database.DBWorker;
import entities.User;
import managers.TasksManager;
import managers.UsersManager;
import network.UserEnum;

public class AuthManager {
DBWorker dbWorker;
 UsersManager usersManager = UsersManager.INSTANCE;
 TasksManager tasksManager = TasksManager.INSTANCE;
 private boolean authManager = false;
 
 public boolean isAuthManger(){
	 return authManager;
 } 
 
public AuthManager(){
	this.dbWorker = new DBWorker();
}

public boolean checkAuth(User user){
	dbWorker.getAllUsersFromDB();
	for(User u:usersManager.getUsers()){
		if(u.getLogin().equals(user.getLogin())&&u.getPassword().equals(user.getPassword())){
			user = u;
		}else
			authManager = false;
	}
	
	if(user.getRole()!=null){
		if(user.getRole().equals(UserEnum.MANAGER_ROLE)){
			authManager = true;
			return true;
		}
		else authManager = false;
		return false;
	}
	return false;
}
}
