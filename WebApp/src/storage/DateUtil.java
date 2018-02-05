package storage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Task;

public class DateUtil {
	DateFormat partOne = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat partTwo = new SimpleDateFormat("HH:mm:ss");
	DateFormat simpleFroCreateYY = new SimpleDateFormat("yy-MM-dd HH:mm");
	DateFormat simple = new SimpleDateFormat("dd-MM-yy HH:mm");
	DateFormat simpleFroCreate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	DateFormat bigDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
	DateFormat bigDateNoMs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat fromClient = new SimpleDateFormat("dd-MM-yy HH:mm");
	DateFormat fromClientWithSlash = new SimpleDateFormat("yy/MM/dd HH:mm");	
	
	public String parseYYfromDD(String date){
		try {
			Date date1 = simpleFroCreateYY.parse(date);
			System.out.println(fromClient.format(date1));
			return fromClient.format(date1);
		} catch (ParseException e) {
			return date;
		}
	}
	
	public Date parseDateFromString(String date){
		try {
			Date parseDate = simple.parse(date);
			return parseDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String getCurrentDateForDoneTime(){
		return partOne.format(new Date())+"T"+partTwo.format(new Date());
	}
	
	public String parseToCreatedTaskDoneTime(String date){
		String fullDate = date.replace('T', ' ');
		try {
			Date date1 = simpleFroCreate.parse(fullDate);
			return fromClient.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}
	
	public String parseToCreatedTask(String date){
		try {
			Date date1 = simpleFroCreate.parse(date);
			return fromClient.format(date1);
		} catch (ParseException e) {			
			e.printStackTrace();
			return date;
		}
	}
	

	
	
	public String parseToServerDate(String date){
		try {
			Date date1 = simple.parse(date);
			return simpleFroCreate.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
		
	}
	
	public String getCurrentDateForDoneTime(String date){
		try {
			Date date1 = simple.parse(date);
			return partOne.format(date1)+"T"+partTwo.format(date1);
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
	}
	
	public String parseCreateDateSiteApp(String date){
		try {
			Date date1 = fromClientWithSlash.parse(date);
			return simpleFroCreate.format(date1);
		} catch (ParseException e) {
			return date;
		}
	}
	
	public String parseDoneDateSiteApp(String date){
		try {
			Date date1 = fromClient.parse(date);
			return simpleFroCreate.format(date1);
		} catch (ParseException e) {
		try {
			String fullDate = date.replace('T', ' ');
			Date date1 = simpleFroCreate.parse(fullDate);
			return fromClient.format(date1);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return date;
		}
	}
}
	
	public String parseFromClientToServerDate(String date){
		try {
			Date date1 = fromClient.parse(date);
			return simpleFroCreate.format(date1);
		} catch (ParseException e) {
			try {
				Date date2 = fromClientWithSlash.parse(date);
				return simpleFroCreate.format(date2);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return date;
		}
	}
	
	
	public String getCurrentDateForCreateOrUpdateTask(){
		return simpleFroCreate.format(new Date());
	}
	
	
	public String getCurrentDate(){
		return simple.format(new Date());
	}
	
	public String parseFromBigDate(String dateString){
		Date date; 
		try {
			date = bigDate.parse(dateString);
			return simple.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return dateString;
		}
	}
	
	public void changeDateTask (Task task){
		 task.setCreated(parseFromClientToServerDate(task.getCreated()));
   	 task.setDoneTime(parseFromClientToServerDate(task.getDoneTime()));
	 }
}
