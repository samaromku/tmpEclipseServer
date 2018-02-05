package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBWorker;
import entities.Address;
import entities.ContactOnAddress;
import managers.AddressManager;
import managers.ContactsManager;

@WebServlet("/tasks/getContacts")
public class ContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ContactsManager contactsManager = ContactsManager.Instance;
	AddressManager addressManager = AddressManager.INSTANCE;
	DBWorker dbWorker = new DBWorker();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		contactsManager.insertTasksIntoDb();
		PrintWriter writer = response.getWriter();
		for(ContactOnAddress contact:contactsManager.getContactsList()){
			for(Address address:addressManager.getAddresses()){
				if(contact.getAddress().trim().equals(address.getAddress())){
					contact.setAddressId(address.getId());
					writer.write(insertContactInDB(contact));
					writer.flush();
				}
			}
		}
		response.sendRedirect(request.getContextPath()+"/tasks/addresses");
	}
	
	private String insertContactInDB(ContactOnAddress contact){
		if(dbWorker.insertContactOnAddress(contact)){
			return "contact added";
		}
		return null;
	}
}
