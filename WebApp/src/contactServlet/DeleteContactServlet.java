package contactServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.ContactOnAddress;
import managers.ContactsManager;
import worker.AddressWorker;

@WebServlet("/tasks/removeContact")
public class DeleteContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AddressWorker addressWorker = new AddressWorker();
	ContactsManager contactsManager = ContactsManager.Instance;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ContactOnAddress contact = contactsManager.getCOntactById(id);
		if(addressWorker.removeContact(contact)){
			System.out.println("Удален контакт: " + contact);
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
