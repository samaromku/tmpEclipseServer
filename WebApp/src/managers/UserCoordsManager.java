package managers;

import entities.UserCoords;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserCoordsManager {
    private UserCoords userCoords;
    private List<UserCoords>userCoordsList;


    public static final UserCoordsManager INSTANCE = new UserCoordsManager();

    private UserCoordsManager(){
        userCoordsList = new CopyOnWriteArrayList<>();
    }

    public List<UserCoords> getUserCoordsList() {
        return userCoordsList;
    }

    public UserCoords getUserCoords() {
        return userCoords;
    }

    public void setUserCoords(UserCoords userCoords) {
        this.userCoords = userCoords;
    }

    public void addUserCoords(UserCoords userCoords){
        userCoordsList.add(userCoords);
    }

    public void addAll(List<UserCoords>userCoordses){
        userCoordsList.addAll(userCoordses);
    }
    
    public void removeAll(){
    	if(userCoordsList.size()>0){
    		userCoordsList.clear();
    	}
    }
}
