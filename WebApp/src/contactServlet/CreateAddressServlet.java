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

@WebServlet("/tasks/createAddress")
public class CreateAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AddressManager addressManager = AddressManager.INSTANCE; 
    AddressWorker addressWorker = new AddressWorker();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("title", "Создать адрес");
		request.setAttribute("path", request.getContextPath()+"/tasks/update_tasks");
		request.setAttribute("addresses", addressManager.getAddresses());
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/create/create_address.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String orgName = request.getParameter("org_name");
		String address = request.getParameter("address");
		
		Address adr = new Address(orgName, address);
		if(addressWorker.addAddress(adr)){
			response.sendRedirect(request.getContextPath() + "/tasks/addresses");
		}
	}

}
