package utils;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import database.DBWorker;
import entities.Address;

public class CoordinatesGetter {
	Address addressAddToDB = new Address();
	private String url = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode=";
	DBWorker dbWorker = new DBWorker();
	
	public void getAddressCoordinates(List<Address>addresses){
		for(Address a:addresses){
			try {
				addressAddToDB = a;
				parseJson(sendGet(a.getAddress()));
			} catch (IOException e) {
				System.out.println("не вышло");
				e.printStackTrace();
			}
		}
		
	}
	
	private void parseJson(String json){
	    JsonElement jelement = new JsonParser().parse(json);
	    JsonObject jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("response");
	    jobject = jobject.getAsJsonObject("GeoObjectCollection");
	    JsonArray jsonArray = jobject.getAsJsonArray("featureMember");
	    jobject = jsonArray.get(0).getAsJsonObject();
	    jobject = jobject.getAsJsonObject("GeoObject");
	    jobject = jobject.getAsJsonObject("Point");
	    String result = jobject.get("pos").toString();

	    String[] partsWithNoQuotes = result.split("\"");
	    String[] parts = partsWithNoQuotes[1].split(" ");

	    String lat = parts[0];
	    String log = parts[1];
	    
	    addressAddToDB.setCoordsLat(lat);
	    addressAddToDB.setCoordsLon(log);
	    sendCoordinatesToDB(addressAddToDB);
	}
	
	
	private void sendCoordinatesToDB(Address address){
		dbWorker.editAddressCoordinates(address);
	}
	
	private String sendGet(String addressString) throws IOException{
		OkHttpClient client = new OkHttpClient();
		String fullUrl = url + "Санкт-Петербург "+ addressString;
		
		  Request request = new Request.Builder()
		      .url(fullUrl)
		      .build();

		  Response response = client.newCall(request).execute();
		  return response.body().string();
		}
	}

