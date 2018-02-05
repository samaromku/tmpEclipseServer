package managers;

import entities.UserRole;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserRolesManager {
    private UserRole userRole;
    private UserRole createNewUserRole;
    private UserRole updateUserRole;
    private List<UserRole> userRoles;
    public static final UserRolesManager INSTANCE = new UserRolesManager();

    public UserRole getUpdateUserRole() {
        return updateUserRole;
    }

    public void setUpdateUserRole(UserRole updateUserRole) {
        this.updateUserRole = updateUserRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void addUserRole(UserRole userRole){
        userRoles.add(userRole);
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getRoleById(int roleId){
        for (UserRole u: userRoles){
            if(u.getId()==roleId)
                return u;
        }
        return null;
    }

    public void updateUserRole(UserRole userRole){
        Iterator<UserRole> iterator = userRoles.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId()==userRole.getId()){
                iterator.remove();
            }
        }
        userRoles.add(userRole);
    }

    public void setCreateNewUserRole(UserRole createNewUserRole) {
        this.createNewUserRole = createNewUserRole;
    }

    public UserRole getCreateNewUserRole() {
        return createNewUserRole;
    }

    public void removeAll(){
        if(userRoles.size()>0){
            userRoles.clear();
        }
    }

    public int getMaxId(){
        int max = 0;
        for(UserRole u:userRoles){
            if(u.getId()>max)
                max = u.getId();
        }
        return max;
    }

    public UserRole getRoleByUserId(int userId){
        for (UserRole u: userRoles){
            if(u.getUserId()==userId)
                return u;
        }
        return null;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    private UserRolesManager(){
        userRoles  = new CopyOnWriteArrayList<>();
    }
}
