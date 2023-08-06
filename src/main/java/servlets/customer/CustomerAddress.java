package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Address;
import models.AddressDAO;
import models.User;
import models.UserDAO;

/**
 * Servlet implementation class CustomerAddress
 */
@WebServlet("/customer/address")
public class CustomerAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerAddress() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
		
		Address address = new Address();
        try {
            AddressDAO db = new AddressDAO();
            address = db.getAddress(userid);
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.setAttribute("address", address);
        request.getRequestDispatcher("/customer/customerAddress.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
		
		String address = request.getParameter("add_address");
		String address2 = request.getParameter("add_address2");
		String district = request.getParameter("add_district");
		String country = request.getParameter("add_country");
		String city = request.getParameter("add_city");
		String postal_code = request.getParameter("add_postal_code");
		String adr_phone = request.getParameter("add_phone");
		String addressId = null;
		int rowsAffected = 0;
		try {
			AddressDAO db1 = new AddressDAO();
			try {
				addressId = db1.addAddress(address,address2,district,country,city,postal_code,adr_phone);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.sendRedirect(request.getContextPath()+"/customer/address?err=Something%20Went%20Wrong");
				return;
			}
			
			if (addressId == null) {
				response.sendRedirect(request.getContextPath()+"/customer/address?err=Something%20Went%20Wrong");
				return;
			} else {
				UserDAO db2 = new UserDAO();
				rowsAffected = db2.changeUserAddressById(userid, addressId);
				
				if (rowsAffected > 0) {
					response.sendRedirect(request.getContextPath()+"/customer/address?success=Address%20Changed%20Successfully");
					return;
				} else {
					response.sendRedirect(request.getContextPath()+"/customer/address?err=Something%20Went%20Wrong");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/customer/address?err=Something%20Went%20Wrong");
			return;
		}
	
		// doGet(request, response);
	}

}
