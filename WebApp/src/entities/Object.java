package entities;

public class Object {
    private int id;
    private String orgName;
    private String address;
    private String coords_lat;
    private String coords_lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoords_lat() {
        return coords_lat;
    }

    public void setCoords_lat(String coords_lat) {
        this.coords_lat = coords_lat;
    }

    public String getCoords_lon() {
        return coords_lon;
    }

    public void setCoords_lon(String coords_lon) {
        this.coords_lon = coords_lon;
    }

    public Object(int id, String orgName, String address) {
        this.id = id;
        this.orgName = orgName;
        this.address = address;
    }
}
