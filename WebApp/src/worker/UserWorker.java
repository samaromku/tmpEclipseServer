package worker;

import org.apache.log4j.Logger;

import database.DBWorker;
import entities.User;
import entities.UserRole;
import managers.TokenManager;
import managers.UsersManager;
import network.Response;
import network.UserEnum;
import storage.Token;

public class UserWorker {
	DBWorker dbWorker;
	User creator;
	User createdUser;
	private UsersManager usersManager = UsersManager.INSTANCE;
	private TokenManager tokenManager = TokenManager.instance;
	private Logger log = Logger.getLogger(TaskWorker.class.getName());

	public User getCreatedUser() {
		return createdUser;
	}

	public UserWorker(User creator){
		this.creator = creator;
		this.dbWorker = new DBWorker();
	}

	public boolean create(User newUser) {
		createdUser = createUser(newUser);
		if(dbWorker.addNewUserAndUserRole(createdUser)) {
			usersManager.addUser(createdUser);
			log.info("Пользователь " + creator.getLogin() + " создал пользователя: " + createdUser.toString());
            return true;
        }else {
        	createdUser = null;
        	return false;
        }
	}

	private User createUser(User user){
		int userId = (int) (Math.random()*500);
        user.setId(userId);
		 UserRole userRole = new UserRole(user.getId(), false, false, false, false, false, false, false, false, false, false, false, user.getId());;
	        switch (user.getRole()) {
	            case UserEnum.ADMIN_ROLE:
	                userRole = new UserRole(user.getId(), true, true, true, true, true, true, true, true, true, true, true, user.getId());
	                break;
	            case UserEnum.USER_ROLE:
	                userRole = new UserRole(user.getId(), false, false, false, false, true, true, false, false, true, true, false, user.getId());
	                break;
	            case UserEnum.MANAGER_ROLE:
	                userRole = new UserRole(user.getId(), false, true, true, false, true, true, true, true, true, true, false, user.getId());
	                break;
	        }
	        user.setUserRole(userRole);
	        return user;
	 }
	
	public boolean remove(User removeUser) {
		if(dbWorker.removeUser(removeUser)){
				log.info("Пользователь " + creator.getLogin() + " удалил пользователя: " + removeUser.toString());
				usersManager.removeUser(removeUser);
				return true;	
        }else 
        	{
        	return false;
    	}
	}

	public String edit(User editUser) {
		if(dbWorker.editUser(editUser)){
			log.info("Пользователь " + creator.getLogin() + " изменил пользователя: " + editUser.toString());
			usersManager.updateUser(editUser);
			return Response.SUCCESS_EDIT_USER;
		}else {
			return Response.NOT_SUCCESS;
		}
	}
	
	public boolean editUserRole(UserRole userRole ){
		if(dbWorker.updateUserRole(userRole)){
			log.info("Пользователь " + creator.getLogin() + " изменил права пользователя " + userRole.getUserId());
			return true;
		}else {
			return false;
		}
	}
	
	public boolean logout(Token token){
    	try{
    		tokenManager.removeTokenMap(token);
    		log.info("Пользователь " + creator.getLogin() + " вышел из системы");
    		return true;
    	}catch (Exception e) {
    		return false;
		}
	}
	
}
