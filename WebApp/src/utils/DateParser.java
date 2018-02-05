package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	DateFormat simpleFroCreate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	DateFormat simpleFroCreateYY = new SimpleDateFormat("yy-MM-dd HH:mm");
	DateFormat fromClient = new SimpleDateFormat("dd-MM-yy HH:mm");
	DateFormat fromClientWithSlash = new SimpleDateFormat("yy/MM/dd HH:mm");
	
	public String parseCreatedYYYYfromDD(String date){
		try {
			Date date1 = fromClient.parse(date);
			return simpleFroCreate.format(date1);
		} catch (ParseException e) {
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
			return simpleFroCreate.format(date1);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return date;
		}
	}
}
	
}
