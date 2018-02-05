package notify;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MessageSender {
	public static final String SERVER_KEY = "AAAAZWJSHVU:APA91bEMIwRiQrSPE9C1HEfUlsjcljmEChQafZAR5_L80CTlezLJFTanQ_iTYfBgnt-HdiBVu-CFKHhGZpi8XfWv8yRoWDLVHydBryx0eqDGo11kOkPWwhSjt-qLDfWMvx3bVeBETsTQ";
	public static final String CONTENT_TYPE = "application/json";
	public static final String URL = "https://fcm.googleapis.com/fcm/send";
	String title = "test title";
	String text = "simple text";
	Gson gson = new Gson();
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	OkHttpClient client = new OkHttpClient();
	
	
	private String allJson(String token, String taskBody, String creator){
		JsonObject allObjects = new JsonObject();
		allObjects.addProperty("to", token);
		JsonObject data = new JsonObject();
		data.addProperty("task_body", taskBody);
		allObjects.add("data", data);
		return gson.toJson(allObjects);
	}
	
	public String post(String token, String taskBody, String creator) {
		String json =  allJson(token, taskBody, creator);
		  RequestBody body = RequestBody.create(JSON, json);
		  System.out.println(json);
		  Request request = new Request.Builder()
		      .url(URL)
		      .addHeader("Content-Type", CONTENT_TYPE)
		      .addHeader("Authorization", "key="+SERVER_KEY)
		      .post(body)
		      .build();
		  try{
			  Response response = client.newCall(request).execute();
			  System.out.println(response.body().string());
			  return response.body().string();
		  }catch(IOException e){
			  e.printStackTrace();
		  }
		  return null;
		}
}
