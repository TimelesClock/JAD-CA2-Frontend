package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CartDAO;

/**
 * Servlet implementation class CustomerDeleteCart
 */
@WebServlet("/customer/cart/delete")
public class CustomerDeleteCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerDeleteCart() {
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
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int cartId = Integer.parseInt(request.getParameter("cartId"));
		int rowsAffected = 0;
		try {
			CartDAO db = new CartDAO();
			rowsAffected = db.deleteFromCart(cartId);
			
			if (rowsAffected > 0) {
				// request.setAttribute("success", "Added To Cart");
				response.sendRedirect(request.getContextPath() + "/customer/cart/&page=" + page + "&success=Deleted%20From%20Cart");
			} else {
				response.sendRedirect(request.getContextPath() + "/customer/cart/&page=" + page + "&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/customer/cart/&page=" + page + "&err=Something%20Went%20Wrong");
		}
	}

}
