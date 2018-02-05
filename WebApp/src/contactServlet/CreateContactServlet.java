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
import worker.AddressWorker;

@WebServlet("/tasks/createContact")
public class CreateContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AddressManager addressManager = AddressManager.INSTANCE; 
    AddressWorker addressWorker = new AddressWorker();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Создать контакт");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/create/create_contact.jsp");
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
		ContactOnAddress contact = new ContactOnAddress.Builder()
				.post(post)
				.name(fio)
				.email(email)
				.phone(phone)
				.apartments(appartments)
				.addressId(addressId)
				.build();
		if(addressWorker.addContact(contact)){
			System.out.println("Добавилен контакт: " + contact);
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
		
	}

}
