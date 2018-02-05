package storage;
import com.google.gson.Gson;
import entities.*;
import network.Request;
import network.Response;
import storage.Token;

import java.util.List;

public class JsonParser {
    Gson gson = new Gson();

    public Request parseFromJson(String json) {
        return gson.fromJson(json, Request.class);
    }

    public String successAddCoords(){
        return new Gson().toJson(Response.sucOrNot(Response.SUCCESS_ADD_COORDS));
    }

    public String successUpdateTask(){
        return new Gson().toJson(Response.sucOrNot(Response.SUCCESS_UPDATE_TASK));
    }
    
    public String parseToJson(Response response){
    	return new Gson().toJson(response);
    }

    public String successUpdateTasks(Response response){
    	return new Gson().toJson(response);
    }
    
    public String success(String response){
        return new Gson().toJson(Response.sucOrNot(response));
    }

//    public String parseAddressesToUser(List<Address> addresses){
//        return new Gson().toJson(new Response(Response.ADD_ADDRESSES_TO_USER, addresses));
//    }

//    public String parseLastUserCoords(List<UserCoords> userCoordsList){
//        return new Gson().toJson(Response.getUserCoords(userCoordsList, Response.ADD_LATEST_USER_COORDS));
//    }

    public String parseSuccessUpdateUserRole(){
        return new Gson().toJson(Response.sucOrNot(Response.UPDATE_USER_ROLE_SUCCESS));
    }

    public String parseSuccessInsertUserRole(){
        return new Gson().toJson(Response.sucOrNot(Response.INSERT_USER_ROLE_SUCCESS));
    }

    public String notSuccess(){
        return new Gson().toJson(Response.sucOrNot(Response.NOT_SUCCESS));
    }


    public String notSuccessAuth(){
        return new Gson().toJson(Response.sucOrNot(Response.NOT_SUCCESS_AUTH));
    }

    public String parseToJsonUserTasks(List<User>users, User user, List<Task> taskList, List<Address>userAddresses, String response, Token token){
        Response retResponse = new Response.Builder()
        		.userList(users)
        		.user(user)
        		.taskList(taskList)
        		.response(response)
        		.token(token)
        		.addresses(userAddresses)
        		.build();
    	return new Gson().toJson(retResponse);
    }

    public String parseToJsonGuest(){
        return new Gson().toJson(Response.sucOrNot(Response.GET_AWAY_GUEST));
    }

    public String parseToAdminUsersTask(List<User> users, List<Task> taskList, String response, Token token){
        Response adminTasksResposne = new Response.Builder()
        		.userList(users)
        		.taskList(taskList)
        		.response(response)
        		.token(token)
        		.build();
    	return new Gson().toJson(adminTasksResposne);
    }

    public String successAddComment(){
        return new Gson().toJson(Response.sucOrNot(Response.ADD_COMMENT_SUCCESS));
    }

    public String successCreateTask(){
        return new Gson().toJson(Response.sucOrNot(Response.ADD_TASK_SUCCESS));
    }



//    public String successAddUser(User user, String response){
//        return new Gson().toJson(new Response(user, response));
//    }

//    public String parseCommentsByTask(List<Comment> comments, String response){
//        return new Gson().toJson(new Response(comments, response));
//    }
}
