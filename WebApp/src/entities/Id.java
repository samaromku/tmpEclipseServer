package entities;

import java.util.UUID;

public class Id {
	private UUID id;
	
	public Id(){
		id = UUID.randomUUID();
	}
	
	public UUID getId(){
		return id;
	}
}
