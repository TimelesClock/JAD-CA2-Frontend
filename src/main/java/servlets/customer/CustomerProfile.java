package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import models.UserDAO;

/**
 * Servlet implementation class CustomerProfile
 */
@WebServlet("/customer/profile")
public class CustomerProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = (int) session.getAttribute("userid");
		User user = new User();
        try {
            UserDAO db = new UserDAO();
            user = db.getUserById(userid);
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("customer/customerProfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = (int) session.getAttribute("userid");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		int rowsAffected = 0;
		try {
			UserDAO db = new UserDAO();
			rowsAffected = db.editUserById(userid, username, email, phone);
			
			if (rowsAffected > 0) {
				response.sendRedirect(request.getContextPath()+"/customer/profile&success=Password%20Changed%20Successfully");
			} else {
				response.sendRedirect(request.getContextPath()+"/customer/profile&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/customer/profile&err=Something%20Went%20Wrong");
		}
		doGet(request, response);
	}

}
