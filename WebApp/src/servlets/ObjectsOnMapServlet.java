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


@WebServlet("/tasks/objects_on_map")
public class ObjectsOnMapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AddressManager addressManager = AddressManager.INSTANCE;
	ContactsManager contactsManager = ContactsManager.Instance;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setAttribute("contacts", contactsManager.getContactsList());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/tasks/objects_on_map.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
