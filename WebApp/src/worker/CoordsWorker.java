package worker;

import java.util.ArrayList;
import java.util.List;

import database.DBWorker;
import entities.UserCoords;
import managers.UserCoordsManager;

public class CoordsWorker {
	DBWorker dbWorker;
	private UserCoordsManager coordsManager = UserCoordsManager.INSTANCE;
	private List<UserCoords>coordes;
	
	public List<UserCoords> getCoordes() {
		return coordes;
	}

	public CoordsWorker(){
		coordes = new ArrayList<>();
		dbWorker = new DBWorker();
	}
	
	public boolean addCoords(UserCoords coords){
		if(dbWorker.insertUserCoords(coords)){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean getLatestCoordes(){
		if(dbWorker.getLatestUserCoords()){
			removeOldCoordes();
			coordes.addAll(coordsManager.getUserCoordsList());
			return true;
		}
        else {
        	return false;
        }
	}
	
	private void removeOldCoordes(){
		if(coordes.size()>0){
			coordes.clear();
		}
	}
}
