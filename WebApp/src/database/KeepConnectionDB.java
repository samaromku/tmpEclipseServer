package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KeepConnectionDB {
	private Statement statement;
	private DBStart dbStart = DBStart.instance;
	ResultSet resultSet;
	Queries queries = new Queries();
	public static final KeepConnectionDB instance = new KeepConnectionDB();
	
	private KeepConnectionDB(){}

	public void getConnection() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			while(true){
				try {
					statement = dbStart.getConnection().createStatement();
					resultSet = statement.executeQuery(queries.getAllFirebaseTokens());	
					System.out.println("make simple select");
					Thread.sleep(1000*60*60);
				} catch (SQLException | InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}).start();
	}
}
