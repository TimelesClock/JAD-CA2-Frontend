package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import models.UserDAO;
import util.AppUtil;

/**
 * Servlet implementation class CustomerChangePassword
 */
@WebServlet("/customer/changePassword")
public class CustomerChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/customer/customerChangePassword.jsp").forward(request, response);
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
		
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		int rowsAffected = 0;
		try {
			if (password.length() < 8) {
				response.sendRedirect(request.getContextPath()+"/customer/changePassword?err=Minimum%20Length%20Of%208%20Characters");
				return;
			} else if (!password.equals(passwordCheck)) {
				response.sendRedirect(request.getContextPath()+"/customer/changePassword?err=Passwords%20Entered%20Do%20Not%20Match");
				return;
			} else {
				UserDAO db = new UserDAO();
	            rowsAffected = db.changeUserPasswordById(userid, password);
	            
				if (rowsAffected > 0) {
					response.sendRedirect(request.getContextPath()+"/customer/changePassword?success=Password%20Changed%20Successfully");
					return;
				} else {
					response.sendRedirect(request.getContextPath()+"/customer/changePassword?err=Something%20Went%20Wrong");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/customer/changePassword?err=Something%20Went%20Wrong");
			return;
		}
	}

}
