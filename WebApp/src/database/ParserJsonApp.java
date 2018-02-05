package database;

import com.google.gson.Gson;

import network.Request;
import network.Response;

public class ParserJsonApp {
	Gson gson = new Gson();
	//метод, который берет json и возвращает из него request
	public Request getRequestFromJson(String jsonString){
		return gson.fromJson(jsonString, Request.class);
	}
	
	
	//метод который берет строку из метода, делает из нее response и конвертирует в json формат
	//возвращает строку json
	public String getJsonResponse(String simpleData){
		Response response = Response.sucOrNot(simpleData);
		return gson.toJson(response);
	}
}
