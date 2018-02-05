package contactServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Address;
import managers.AddressManager;
import worker.AddressWorker;

@WebServlet("/tasks/editAddress")
public class EditAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AddressManager addressManager = AddressManager.INSTANCE;
    AddressWorker addressWorker = new AddressWorker();
	Address address;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		address = addressManager.getAddressById(id);
		
		request.setAttribute("address", address);
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/edit/edit_address.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String addressStr = request.getParameter("address");
		String name = request.getParameter("name");
		Address addressFromPost = new Address(address.getId(), name, addressStr);
		if(addressWorker.UpdateAddress(addressFromPost)){
			System.out.println("Изменен адрес: " + addressFromPost);
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
		
	}

}
