package storage;
import java.util.UUID;

public class Token {
	private UUID token;
	
	public UUID getTokenUUID(){
		return token;
	}
	
	public Token(){
		this.token = UUID.randomUUID();
	}
}
