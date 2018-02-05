package contactServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.ContactOnAddress;
import managers.AddressManager;
import managers.ContactsManager;
import worker.AddressWorker;

@WebServlet("/tasks/editContact")
public class EditContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ContactsManager contactsManager = ContactsManager.Instance;   
    private AddressManager addressManager = AddressManager.INSTANCE; 
    AddressWorker addressWorker = new AddressWorker();
    ContactOnAddress contact;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int contactId = Integer.parseInt(request.getParameter("id"));
		contact = contactsManager.getCOntactById(contactId);
		request.setAttribute("contact", contact);
		request.setAttribute("title", "Изменить контакт");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setCharacterEncoding("UTF-8");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit/edit_contact.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String post = request.getParameter("post");
		String fio = request.getParameter("fio");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String appartments = request.getParameter("appartments");
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		ContactOnAddress editCcontact = new ContactOnAddress.Builder()
				.id(contact.getId())
				.post(post)
				.name(fio)
				.email(email)
				.phone(phone)
				.apartments(appartments)
				.addressId(addressId)
				.build();
		if(addressWorker.UpdateContact(editCcontact)){
			System.out.println("Изменен контакт: " + editCcontact);
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
	}

}
