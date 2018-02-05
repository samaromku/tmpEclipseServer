package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import managers.AddressManager;
import managers.ContactsManager;
import utils.CoordinatesGetter;

@WebServlet("/tasks/addresses")
public class AddressServlet extends HttpServlet {
	ContactsManager contactsManager = ContactsManager.Instance;
	private static final long serialVersionUID = 1L;
    AddressManager addressManager = AddressManager.INSTANCE;  
    CoordinatesGetter coordinatesGetter = new CoordinatesGetter();

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contactsManager.setContactsAddress();
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setAttribute("contacts", contactsManager.getContactsList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/addresses.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		coordinatesGetter.getAddressCoordinates(addressManager.getAddresses());
		response.sendRedirect("/WebApp/tasks/addresses");
	}

}
