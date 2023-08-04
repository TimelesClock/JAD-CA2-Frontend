package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.*;


/**
 * Servlet implementation class AdminAddBook
 */
@WebServlet("/admin/customer/delete")
public class AdminDeleteCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminDeleteCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath() + "/admin/customer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("user_id");

		
		try {
			UserDAO db = new UserDAO();
			Boolean deleted = db.deleteUser(userId);
			if(deleted) {
				response.sendRedirect(request.getContextPath() + "/admin/customer?success=User%20Deleted%20Successfully!");
			}else {
				response.sendRedirect(request.getContextPath() + "/admin/customer?err=User%20Not%20Deleted!");
			}
		}catch(Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/customer?err=User%20Not%20Deleted!");
		}
	}

}
