package worker;

import java.util.Date;

import com.google.gson.Gson;

import entities.Comment;
import entities.Task;
import entities.User;
import entities.UserCoords;
import entities.UserRole;
import network.Request;
import network.Response;
import storage.Token;

public class ParserJson {
	Gson gson = new Gson();

	public String parseToJson(String data) {
		return gson.toJson(Response.sucOrNot(data));
	}

	public Task parseTaskFromJson(String json) {
		return gson.fromJson(json, Request.class).getTask();
	}
	
	
	public User parseUserFromJson(String json) {
		return gson.fromJson(json, Request.class).getUser();
	}
	
	public Token parseTokenFromJson(String json) {
		return gson.fromJson(json, Request.class).getToken();
	}
	
	public String parseFirebaseTokenFromJson(String json) {
		return gson.fromJson(json, Request.class).getFirebaseToken();
	}
	
	public Comment parseCommentFromJson(String json) {
		return gson.fromJson(json, Request.class).getComment();
	}
	
	public UserRole parseUserRoleFromJson(String json) {
		return gson.fromJson(json, Request.class).getUserRole();
	}
	
	public UserCoords parseCoordsFromJson(String json) {
		return gson.fromJson(json, Request.class).getUserCoords();
	}
	
	public String parseToJson(Response response) {
		return gson.toJson(response);
	}
}
