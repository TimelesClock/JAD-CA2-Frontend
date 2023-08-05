package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BookDAO;
import models.CartDAO;

/**
 * Servlet implementation class CustomerAddCart
 */
@WebServlet("/customer/cart/add")
public class CustomerAddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerAddCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		int maxQuantity = -1;
		int rowsAffected = 0;
		try {
			BookDAO db1 = new BookDAO();
			maxQuantity = db1.getQuantityOfBook(bookId);
			
			if (maxQuantity == -1) {
				response.sendRedirect(request.getContextPath() + "/bookDetails/" + bookId + "&err=Something%20Went%20Wrong");
			} else if (quantity > maxQuantity) {
				response.sendRedirect(request.getContextPath() + "/bookDetails/" + bookId + "&err=Cannot%20Add%20More%20Than%20Available%20Quantity");
			}
			
			CartDAO db2 = new CartDAO();
			rowsAffected = db2.addToCart(rowsAffected, bookId, quantity);
			
			if (rowsAffected > 0) {
				// request.setAttribute("success", "Added To Cart");
				response.sendRedirect(request.getContextPath() + "/bookDetails/" + bookId + "&success=Added%20To%20Cart");
			} else {
				response.sendRedirect(request.getContextPath() + "/bookDetails/" + bookId + "&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/bookDetails/" + bookId + "&err=Something%20Went%20Wrong");
		}
	}

}
