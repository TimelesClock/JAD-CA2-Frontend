package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.UserDAO;
import models.AddressDAO;

/**
 * Servlet implementation class AdminAddCustomer
 */
@WebServlet("/admin/customer/add")
public class AdminAddCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/admin/customer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String addressId = request.getParameter("add_address_id");
			System.out.print("address"+addressId);
			try {
				UserDAO db = new UserDAO();
				Integer count = db.userCountByEmail(email);
				if (count != 0) {
					response.sendRedirect(request.getContextPath() + "/admin/customer?err=Email%20Already%20Exists");
					return;
				}
				
				if (addressId.equals("-1")) {
					addressId = null;
				}else if (addressId.equals("0")) {
					String address = request.getParameter("add_address");
					String address2 = request.getParameter("add_address2");
					String district = request.getParameter("add_district");
					String country = request.getParameter("add_country");
					String city = request.getParameter("add_city");
					String postal_code = request.getParameter("add_postal_code");
					String adr_phone = request.getParameter("add_phone");
					AddressDAO adr = new AddressDAO();
					addressId = adr.addAddress(address,address2,district,country,city,postal_code,adr_phone);
				}
				
				String userid = db.addUser(name,email,password,phone,addressId);
				if (userid == null) {
					response.sendRedirect(request.getContextPath() + "/admin/customer?err=Customer%20Was%20Not%20Added");
					return;
				}else {
					response.sendRedirect(request.getContextPath() + "/admin/customer?success=Customer%20Was%20Successfully%20Added");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/admin/customer?err=Database%20Error");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/customer?err=Something%20Went%20Wrong");
			return;
		}
	}

}
