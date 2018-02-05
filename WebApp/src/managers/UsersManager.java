package managers;

import entities.User;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UsersManager {
    private User user;
    private List<User> users;
    private Map<String, User>usersMap;
    public static final UsersManager INSTANCE = new UsersManager();
    private User removeUser;
    private User creator;

    public User getCreator() {
		return creator;
	}

    public void setCreator(User creator) {
		this.creator = creator;
	}

	public Map<String, User> getUsersMap() {
		return usersMap;
	}

	public void addUserToken(String token, User user){
		usersMap.put(token, user);
	}
	
	public User getRemoveUser() {
        return removeUser;
    }

    public void setRemoveUser(User removeUser) {
        this.removeUser = removeUser;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }
    
    public void updateUser(User user){
    	Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId()==user.getId()){
                iterator.remove();
            }
        }
        users.add(user);
    }
    
//    public User getUser() {
//        return user;
//    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void addAll(List<User> userList) {
        users.addAll(userList);
    }

    public void removeAll(){
        if(users.size()>0){
            users.clear();
        }
    }

    public void removeUser(User user){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            if(iterator.next().equals(user)){
                iterator.remove();
            }
        }
    }

    public User getUserByUserName(String userName){
        for(User u:users){
            if(u.getLogin().equals(userName))
                return u;
        }
        return null;
    }


    public User getUserById(int id){
        for(User u:users){
            if(u.getId()==id){
                return u;
            }
        }
        return null;
    }

    private UsersManager(){
    	usersMap = new ConcurrentHashMap<>();
        users = new CopyOnWriteArrayList<>();
    }

    public int maxId(){
        int max=0;
        for(User u:users){
            if(u.getId()>max)
                max = u.getId();
        }
        return max;
    }

}
