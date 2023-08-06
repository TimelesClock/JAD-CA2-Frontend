package servlets;

import java.io.IOException;
import models.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Reseller
 */
@WebServlet("/reseller")
public class Reseller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Reseller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userid") == null) {
				response.sendRedirect(request.getContextPath() + "/home");
			}
			UserDAO db = new UserDAO();
			String userId = (String) session.getAttribute("userid");
			String role = db.getRole(userId);
			if (!role.equals("reseller")) {
				response.sendRedirect(request.getContextPath() + "/home");
			}else {
				String token = db.getToken(userId);
				request.setAttribute("token",token);
				request.getRequestDispatcher("/reseller.jsp").forward(request,response);
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
