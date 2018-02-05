package managers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import entities.User;
import storage.Token;

public class TokenManager {
	private Map<Token, User>tokenMap;
	private Map<String, Integer>firebaseTokenMap;
	UsersManager usersManager = UsersManager.INSTANCE;
	
	public static final TokenManager instance = new TokenManager();
	
	private TokenManager(){
		this.firebaseTokenMap = new ConcurrentHashMap<>();
		this.tokenMap = new ConcurrentHashMap<>();
	}
	
		
	public Map<Token, User>getTokens(){
		return tokenMap;
	}
	
	public void addFirebaseToken(String token, int userId){
		firebaseTokenMap.put(token, userId);
	}
	
	public void removeFirebaseToken(String token){
		firebaseTokenMap.remove(token);
	}
	
	public Map<String, Integer>getFirebaseTokens(){
		return firebaseTokenMap;
	}
	
	public void clearFirebaseTokens(){
		firebaseTokenMap.clear();
	}
	
	public User getUserByFirebaseToken(String token){
		for (Map.Entry<String, Integer> e : firebaseTokenMap.entrySet()) {
			if(e.getKey().equals(token)){
				return usersManager.getUserById(e.getValue());
			}
		}
		return null;		
	}
	
	public void addTokenToMap(Token token, User user){
		tokenMap.put(token, user);
	}
	
	public void removeTokenMap(Token token){
		tokenMap.remove(token);
	}
	

	public boolean isTokenInBase(Token token){
		if(token!=null && token.getTokenUUID()!=null){
			for (Map.Entry<Token, User> e : tokenMap.entrySet()) {
				if(e.getKey().getTokenUUID().equals(token.getTokenUUID())){
					return true;
				}
			}
		}
		return false;
	}
	
	public User getUserByToken(UUID token){
		for (Map.Entry<Token, User> e : tokenMap.entrySet()) {
			if(e.getKey().getTokenUUID().equals(token)){
				return e.getValue();
			}
		}
		return null;		
	}
}
