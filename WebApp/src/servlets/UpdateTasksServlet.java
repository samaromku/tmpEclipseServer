package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.OnRefreshData;
import managers.MessageHandler;
import storage.Token;

@WebServlet(value="/tasks/update_tasks")
public class UpdateTasksServlet extends HttpServlet implements OnRefreshData{
	public static final String DATA = "data: ";
	public static final String NEXT_STR = "\n\n";
	private static final long serialVersionUID = 1L;
	PrintWriter writer;
	private MessageHandler messageHandler = MessageHandler.instance;
	Iterator<String> iterator = null;
	public static int x = 225;
	
	@Override
	public void init() throws ServletException {
		messageHandler.setOnRefreshData(this);
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		writer = response.getWriter();
		while(true){
			x++;
			try {
				Thread.sleep(1000*60*60);
				} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}
	}


		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void refresh(){
		Map<String, Integer>messages = messageHandler.getMessageQueue();
		Iterator<Map.Entry<String, Integer>> iter = messages.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<String,Integer> entry = iter.next();
			if(entry.getValue()==0){			
				try {
					System.out.println("message from updateServlet before " + entry.getValue() + " key " + entry.getKey());
					Thread.sleep(1000);
					sendMessage(entry.getKey());
					entry.setValue(1);
					System.out.println("message from updateServlet after " + entry.getValue() + " key " + entry.getKey());
				} catch (InterruptedException e) {
					System.out.println("sleep bad");
				}
			}
		}
		messageHandler.refresh();
	}

	private void sendMessage(String message){
		writer.print(DATA + message + NEXT_STR);
		writer.flush();
	}


	@Override
	public void onRefreshData(String data) {
		writer.print(DATA + data + NEXT_STR);
		writer.flush();		
	}
}
