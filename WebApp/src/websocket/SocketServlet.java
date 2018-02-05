package websocket;
 
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/serverendpoint")
public class SocketServlet  {
	@OnOpen
	public void handleOpen(){
		System.out.println("connected");
	}
	
	@OnMessage
	public String handleMessage(String message){
		System.out.println("received message: " + message);
		return message;
	}
	
	@OnClose
	public void handleClose(){
		System.out.println("disconnected");
	}
	
	@OnError 
	public void handleError(Throwable t){
		t.printStackTrace();
	}
	
}
