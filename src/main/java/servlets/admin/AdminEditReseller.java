package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.AddressDAO;
import models.UserDAO;

/**
 * Servlet implementation class AdminAddCustomer
 */
@WebServlet("/admin/reseller/edit")
public class AdminEditReseller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditReseller() {
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
			Integer userId = Integer.parseInt(request.getParameter("user_id"));
			String addressId = request.getParameter("edit_address_id");
			try {
				UserDAO db = new UserDAO();
				
				if (addressId.equals("-1")) {
					addressId = null;
				}else if (addressId.equals("0")) {
					String address = request.getParameter("edit_address");
					String address2 = request.getParameter("edit_address2");
					String district = request.getParameter("edit_district");
					String country = request.getParameter("edit_country");
					String city = request.getParameter("edit_city");
					String postal_code = request.getParameter("edit_postal_code");
					String adr_phone = request.getParameter("edit_phone_address");
					AddressDAO adr = new AddressDAO();
					addressId = adr.addAddress(address,address2,district,country,city,postal_code,adr_phone);
				}
				
				Integer rowsAffected = db.editUserById(userId,name,email,phone,addressId);
				if (password != null) {
					db.changeUserPasswordById(userId,password);
				}
				if (rowsAffected == 0) {
					response.sendRedirect(request.getContextPath() + "/admin/reseller?err=Reseller%20Details%20Was%20Not%20Edited");
					return;
				}else {
					response.sendRedirect(request.getContextPath() + "/admin/reseller?success=Reseller%20Details%20Was%20Successfully%20Edited");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/admin/reseller?err=Database%20Error");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/reseller?err=Something%20Went%20Wrong");
			return;
		}
	}

}
