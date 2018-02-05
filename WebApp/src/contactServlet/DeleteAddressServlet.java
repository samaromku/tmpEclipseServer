package contactServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Address;
import entities.ContactOnAddress;
import managers.AddressManager;
import managers.ContactsManager;
import worker.AddressWorker;

@WebServlet("/tasks/removeAddress")
public class DeleteAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AddressWorker addressWorker = new AddressWorker();
	AddressManager addressManager = AddressManager.INSTANCE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Address address = addressManager.getAddressById(id);
		if(addressWorker.removeAddress(address)){
			System.out.println("Удален адрес: " + address);
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
