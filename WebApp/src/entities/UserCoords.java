package entities;


public class UserCoords {
    private int id;
    private int userId;
    private double lat;
    private double log;
    private String ts;
    private String address;

    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserCoords(double lat, double log) {
        this.lat = lat;
        this.log = log;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
    
    public UserCoords(int id, int userId, double lat, double log, String ts, String address) {
        this.id = id;
        this.userId = userId;
        this.lat = lat;
        this.log = log;
        this.ts = ts;
        this.address = address;
    }
}


