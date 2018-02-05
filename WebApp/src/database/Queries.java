package database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import entities.*;


public class Queries {
    public static final String ID = "id";
    public static final String CREATED = "created";
    public static final String IMPORTANCE = "importance";
    public static final String BODY = "body";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String DONE_TIME = "done_time";
    public static final String OBJECT_ID = "address_name_id_address";
    public static final String USER_ID = "users_id_users";
    public static final String ADDRESS_ID = "address_name_id_address";
    
    public static final String ADD_COMMENT_CREATE_TASK = "Заявку создал(а) ";
    public static final String ADD_COMMENT_DISTRIBUTED_TASK = "Заявку взял(а) ";
    public static final String ADD_COMMENT_CONTROL_TASK = "Заявка выполнил(а) ";
    public static final String ADD_COMMENT_DONE_TASK = "Заявка перевел(а) в выполненные ";
    public static final String ADD_COMMENT_CHANGED_TASK = "Заявка изменил(а) ";
    public static final String ADD_COMMENT_DOING_TASK = "Приступил(а) к выполнению ";
    public static final String ADD_COMMENT_TASK = "Комментарий оставил(а) ";
    
    public static final String ADD_COMMENT_DISTRIBUTED_TASK_FROM_MANAGER = "Исполнителем назначен ";
    public static final String ADD_COMMENT_DISAGREE_TASK = "От заявки отказался ";
    public static final String ADD_COMMENT_NEED_HELP_TASK = "Нужна помощь ";

    public static final String USER_NAME = "login";
    public static final String USER_PWD = "password";
    public static final String USER_FIO = "FIO";
    public static final String USER_ROLE = "role";
    public static final String USER_TLF = "telephone";
    public static final String USER_EMAIL = "email";

    public static final String COMMENT_BODY = "comment_body";
    public static final String USER_ID_USERS = "users_id_users";
    public static final String TASKS_ID_TASKS = "tasks_id_tasks";

    public static final String MAKE_NEW_USER = "make_new_user";
    public static final String MAKE_TASKS = "make_tasks";
    public static final String MAKE_ADDRESS = "make_address";
    public static final String WATCH_ADDRESS = "watch_address";
    public static final String CORRECTION_TASK = "correction_task";
    public static final String CORRECTION_STATUS = "correction_status";
    public static final String MAKE_EXECUTOR = "make_executor";
    public static final String CORRECTION_EXECUTOR = "correction_executor";
    public static final String WATCH_TASKS = "watch_tasks";
    public static final String COMMENT_TASKS = "comment_tasks";
    public static final String CHANGE_PASSWORD = "change_password";
    public static final String ID_USER_ROLE = "id_user_role";

    public static final String ORG_NAME = "org_name";
    public static final String ADDRESS = "address";
    public static final String COORDS_LAT = "coords_lat";
    public static final String COORDS_LON = "coords_lon";
    public static final String TS = "ts";
    public static final String APARTMENTS = "apartments";
    
    public static final String POST = "post";
    public static final String NAME = "name";
    public static final String PHONE = "telephone";
    public static final String ADDRESS_NAME_ID = "address_name_id_address";
    
    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String USER_ID_FIRE = "user_id_fire";   
    public static final String ADD_FIREBASE_TOKEN = "add_firebase_token";

//    public static final String NEW_TASK = "new_task";
//    public static final String DISTRIBUTED_TASK = "distributed";
//    public static final String DOING_TASK = "doing";
//    public static final String DONE_TASK = "done";
//    public static final String DISAGREE_TASK = "disagree";
//    public static final String CONTROL_TASK = "control";
//    public static final String NEED_HELP = "need_help";


    public static final String NEW_TASK = "новое задание";
    public static final String DISTRIBUTED_TASK = "распределено";
    public static final String DOING_TASK = "выполняется";
    public static final String DONE_TASK = "выполнено";
    public static final String DISAGREE_TASK = "отказ";
    public static final String CONTROL_TASK = "контроль";
    public static final String NEED_HELP = "нужна помощь";
    public static final String NOTE = "комментарий";
    

    public String updateUserRoleById(UserRole userRole){
        return "UPDATE `user_role` SET `make_new_user`='"+ tfNumbers(userRole.isMakeNewUser()) +
                "', `make_tasks`='"+ tfNumbers(userRole.isMakeTasks()) + "', `make_address`='" + tfNumbers(userRole.isMakeAddress()) +
                "', `watch_address`='"+ tfNumbers(userRole.isWatchAddress()) +
                "', `correction_task`='"+ tfNumbers(userRole.isCorrectionTask()) +"', `correction_status`='"+ tfNumbers(userRole.isCorrectionStatus()) +
                "', `make_executor`='"+ tfNumbers(userRole.isMakeExecutor()) +
                "', `correction_executor`='" + tfNumbers(userRole.isCorrectionExecutor()) + "', `watch_tasks`='"+ tfNumbers(userRole.isWatchTasks()) +
                "', `comment_tasks`='"+ tfNumbers(userRole.isCommentTasks()) +"', " +
                "`change_password`='"+ tfNumbers(userRole.isChangePassword()) +"' WHERE `id_user_role`='"+ userRole.getId() +"';";
    }

    public String addUserCoords(UserCoords userCoords){
        return "INSERT INTO `mydb`.`user_coords` (`coords_lat`, `coords_lon`, `users_id_users`, `ts`) VALUES ('" +
                userCoords.getLat()+"', '"+userCoords.getLog()+"', '"+userCoords.getUserId()+"', '"+userCoords.getTs()+"');";
    }
    
    public String insertContact(ContactOnAddress contact){
    	return "INSERT INTO `mydb`.`contact_on_address` (`post`, `name`, `telephone`, `email`, `address_name_id_address`, `apartments`) VALUES ('"
    			+ contact.getPost() +"', '"+contact.getName()+
    			"', '"+contact.getPhone()+"', '"+contact.getEmail()+"', '"+contact.getAddressId()+"', '"+contact.getApartments()+"');";
    }
    
    public String insertAddress(Address address){
    	return "INSERT INTO `mydb`.`objects` (`org_name`, `address`) VALUES ('"+address.getName()+"', '"+address.getAddress()+"');";
    }
    
    public String addContactInDB(ContactOnAddress contact){
    	return "INSERT INTO `mydb`.`contact_on_address` (`post`, `name`, `telephone`, `email`, `apartments`, `address_name_id_address`) VALUES ('"
    			+ contact.getPost() +"', '"+contact.getName()+"', '"+contact.getPhone()+"', '"+contact.getEmail()+"', '"+contact.getApartments()+"', '"+contact.getAddressId()+"');";
    }
    
    public String editUser(User user){
    	return "UPDATE `users` SET `login`='"+user.getLogin()+
    			"', `password`='"+user.getPassword()+
    			"', `FIO`='"+user.getFIO()+
    			"', `role`='"+user.getRole()+
    			"', `telephone`='"+user.getTelephone()+
    			"', `email`='"+user.getEmail()+
    			"' WHERE `id`='"+user.getId()+"';";
    }

    public String getLastUserCoords(){
        return "SELECT * FROM user_coords WHERE (users_id_users, ts) IN "+
        		"(SELECT users_id_users, MAX(ts) as ts "+
        				"FROM user_coords "+
        				"GROUP BY users_id_users)";
    }

    public String getAllContactsForAddress(){
    	return "SELECT * FROM contact_on_address;";
    }
    
    public String updateContact(ContactOnAddress contact){
    	return "UPDATE `mydb`.`contact_on_address` SET `post`='"
    			+ contact.getPost() + "', `name`='"+contact.getName()+"', `telephone`='"+contact.getPhone()+"', `apartments`='"+contact.getApartments()+
    			"', `address_name_id_address`='"+contact.getAddressId()+"' WHERE `id`='"+contact.getId()+"';";
    }
    
    public String updateAddress(Address address){
    	return "UPDATE `mydb`.`objects` SET `org_name`='"+address.getName()+"', `address`='"+address.getAddress()+"' WHERE `id`='"+address.getId()+"';";
    }
    
    public String removeContact(ContactOnAddress contact){
    	return "DELETE FROM `contact_on_address` WHERE `id`='"+contact.getId()+"';";
    }
    
    public String getAllDoneComments(){
    	return "SELECT * FROM user_comment WHERE `comment_body` LIKE '%выполнена%';";
    }
    
    public String removeAddress(Address address){
    	return "DELETE FROM `objects` WHERE `id`='"+address.getId()+"';";
    }
    
    public String removeContactsByAddressId(Address address){
    	return "DELETE FROM `contact_on_address` WHERE `address_name_id_address`='"+address.getId()+"';";
    }
    
    public String removeCommentsByTaskId(Task task){
        return "DELETE FROM `user_comment` WHERE `tasks_id_tasks`='"+task.getId()+"';";
    }
    
    public String addFirebaseToken(String token, int userId){
    	return "INSERT INTO `mydb`.`firebase` (`firebase_token`, `user_id_fire`) VALUES ('"+token+"', '"+userId+"');";
    }
    
    public String updateFirebaseToken(String token, int userId){
    	return "UPDATE `mydb`.`firebase` SET `firebase_token`='"+token+"' WHERE `user_id_fire`='"+userId+"';";
    }

    public String removeTask(Task task){
        return "DELETE FROM `tasks` WHERE `id`='"+task.getId()+"';";
    }

    public String removeCommentsByUserId(User user){
        return "DELETE FROM `user_comment` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeUserRoleByUserId(User user){
        return "DELETE FROM `user_role` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeCoordesByUserId(User user){
        return "DELETE FROM `user_coords` WHERE `users_id_users`='"+user.getId()+"';";
    }

    public String removeUser(User user){
        return "DELETE FROM `users` WHERE `id`='"+user.getId()+"';";
    }

    public String addNewUser(User user){
        return "INSERT INTO `users` (`id`, `login`, `password`, `FIO`, `role`, `telephone`, `email`) VALUES " +
                "('"+ user.getId() +"', '" + user.getLogin() +"', '"+ user.getPassword() +
                "', '"+ user.getFIO() +"', '"+ user.getRole() +"', '"+ user.getTelephone() +
                "', '" + user.getEmail() + "');";
    }

    public String selectAllByUserId(String id){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id "+
		"where not status='выполнено' and users_id_users='"+id+"' or status='"+NEED_HELP+"' or status='"+NEW_TASK+ 
		"' or status='"+DISAGREE_TASK+
		"' order by "+IMPORTANCE+" , done_time";
    }
    
    public String selectDoneTodayForUser(String userId){
    	return "select * FROM tasks left join objects on tasks.address_name_id_address = objects.id "+
    			"where status='выполнено' and users_id_users='"+userId+"' and done_time>=CURDATE()";
    }

    public String selectUsersForSimpleUser(){
        return "SELECT id, login, FIO, telephone, email FROM users;";
    }

    public String selectAllUsers(){
        return "SELECT * FROM users left join user_role on users.id=user_role.users_id_users order by id";
    }

    public String selectAllTasksObjects(){
        return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id order by status, done_time";
    }
    
    public String selectAllDoneTasks(){
    	return "SELECT * FROM tasks left join objects on tasks.address_name_id_address = objects.id where status='выполнено' order by done_time desc";
    }
    
    public String selectAllDoneTodayTasks(){
    	return "SELECT distinct tasks.id, tasks.created, tasks.importance, tasks.status, "+
		"tasks.body, tasks.address_name_id_address, tasks.users_id_users, tasks.type, "+
		"objects.org_name, objects.address, user_comment.created as done_time "+
		"FROM tasks left join objects on tasks.address_name_id_address = objects.id " +
		"left join user_comment on tasks.id = user_comment.tasks_id_tasks "+
		"where status='выполнено' and comment_body LIKE '%переведена в выполненные%' " +
		"and date(user_comment.created)>=(curdate()-1) "+
		"order by done_time desc;";
    }
    
    public String getAllFirebaseTokens(){
    	return "SELECT * FROM firebase";
    }

    public String insertTask(Task task){
        return "INSERT INTO `mydb`.`tasks` (`created`, `importance`, `status`, `body`, `done_time`, " +
                "`address_name_id_address`, `users_id_users`, `type`) VALUES ('" +
                task.getCreated()+"', '"+task.getImportance()+"', '"+task.getStatus()+
                "', '"+task.getBody()+"', '"+task.getDoneTime()+"', '"+task.getAddressId()+"', '"+task.getUserId()+
                "', '"+task.getType()+"');";
    }
    
    public String deleteLineBreaks(int taskId){
    	return "update tasks SET body = TRIM(REPLACE(REPLACE(REPLACE(body, '\n', ' '), '\r', ' '), '\t', ' ')) where id='"+taskId+"'";
    }

    public String updateTask(Task task){
        return "UPDATE `tasks` SET `importance`='"+task.getImportance()
                +"', `status`='"+task.getStatus()+"', `body`='"+task.getBody()
                +"', `done_time`='"+task.getDoneTime()+"', `address_name_id_address`='"+task.getAddressId()
                +"', `users_id_users`='"+task.getUserId()+"', `type`='"+task.getType()+"' WHERE `id`='"+task.getId()+"';";
    }
    
    public String editAddressCoordinates(Address address){
    	return "UPDATE `objects` SET `coords_lat`='"+address.getCoordsLat()
    	+"', `coords_lon`='"+address.getCoordsLon()+"' WHERE `id`='"
    			+address.getId()+"';";
    }

    public String selectCommentsByTask(int taskId){
        return "select * from user_comment where tasks_id_tasks="+taskId + " order by created desc";
    }

    public String insertComment(Comment comment){
         return "INSERT INTO `user_comment` (`comment_body`, `created`, `users_id_users`, `tasks_id_tasks`) VALUES ('"+
                 comment.getBody() +"', '"+ comment.getTs() +"', '"+ comment.getUserId() +"', '"+ comment.getTaskId() +"');";
    }

    public String updateTaskStatus(String taskStatus, int id){
        return "UPDATE `tasks` SET `status`='"+ taskStatus +"' WHERE `id`='"+ id +"';";
    }

    public String getAddresses(){
        return "select * from objects";
    }

    public String insertUserRole(UserRole userRole){
        return "INSERT INTO `mydb`.`user_role` (`id_user_role`, `make_new_user`, `make_tasks`, `make_address`, `watch_address`, `correction_task`, `correction_status`, " +
                "`make_executor`, `correction_executor`, `watch_tasks`, `comment_tasks`, `change_password`, `users_id_users`) VALUES ('" + userRole.getId()+"', '"+
                tfNumbers(userRole.isMakeNewUser())  +"', '"+ tfNumbers(userRole.isMakeTasks()) + "', '" + tfNumbers(userRole.isMakeAddress()) +
            "', '"+ tfNumbers(userRole.isWatchAddress()) +
                "', '"+tfNumbers(userRole.isCorrectionTask())+"', '"+tfNumbers(userRole.isCorrectionStatus())+"', '" +
                tfNumbers(userRole.isMakeExecutor())+"', '"+tfNumbers(userRole.isCorrectionExecutor())+"', '"+tfNumbers(userRole.isWatchTasks())+"', '"+tfNumbers(userRole.isCommentTasks())+"', '" +
                tfNumbers(userRole.isChangePassword())+"', '"+userRole.getUserId()+"');";
    }
    
    @SuppressWarnings("deprecation")
	public String getUserCoordesPerDay(Date date, int userId){
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.YEAR, date.getYear());
    	cal.set(Calendar.MONTH, date.getMonth());
    	cal.set(Calendar.DAY_OF_MONTH, date.getDay());
    	cal.set(Calendar.HOUR_OF_DAY,0);
    	cal.set(Calendar.MINUTE,0);

    	Date firstDate = cal.getTime();
    	
    	Calendar bigCal = Calendar.getInstance();
    	bigCal.set(Calendar.YEAR, date.getYear());
    	bigCal.set(Calendar.MONTH, date.getMonth());
    	bigCal.set(Calendar.DAY_OF_MONTH, date.getDay());
    	bigCal.set(Calendar.HOUR_OF_DAY,23);
    	bigCal.set(Calendar.MINUTE,59);

    	Date lastDate = bigCal.getTime();
    	return "SELECT * FROM mydb.user_coords  "
    			+ "WHERE ts BETWEEN '" + getDateStringFromDate(firstDate) + "' AND '" + getDateStringFromDate(lastDate) + "' AND users_id_users=186";
    }
    
    private String getDateStringFromDate(Date date){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    private int tfNumbers(boolean b){
        if(b)
            return 1;
        else return 0;
    }
}
