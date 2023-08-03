package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.*;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Servlet implementation class AdminAddBook
 */
@WebServlet("/admin/book/delete")
public class AdminDeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminDeleteBook() {
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
		response.sendRedirect(request.getContextPath() + "/admin/book");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		String prev_url = request.getParameter("prev_url");
		
		try {
			BookDAO db = new BookDAO();
			Boolean deleted = db.deleteBook(bookId, prev_url);
			if(deleted) {
				response.sendRedirect(request.getContextPath() + "/admin/book?success=Book%20Deleted%20Successfully!");
			}else {
				response.sendRedirect(request.getContextPath() + "/admin/book?err=Book%20Not%20Deleted!");
			}
		}catch(Exception e) {
			response.sendRedirect(request.getContextPath() + "/admin/book?err=Book%20Not%20Deleted!");
		}
	}

}
