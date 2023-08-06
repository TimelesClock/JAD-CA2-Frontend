package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.BookDAO;
import models.CartDAO;

/**
 * Servlet implementation class CustomerQuantityDeleteOne
 */
@WebServlet("/customer/cart/minusOne")
public class CustomerQuantityMinusOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerQuantityMinusOne() {
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
		HttpSession session = request.getSession(false);
		int userid = -1;
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int bookId = -1;
		int quantity = -1;
		int currentQuantity = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
			bookId = Integer.parseInt(request.getParameter("bookId"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
            return;
        }
		int rowsAffected = 0;
		try {			
			CartDAO db2 = new CartDAO();
			currentQuantity = db2.getCartItemQuantity(userid, bookId);
			if (currentQuantity == -1) {
				response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
				return;
			} else if (currentQuantity == 1) {
				response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&err=Cannot%20Deduct%20Any%20Further");
				return;
			} else {
				rowsAffected = db2.addToCart(userid, bookId, quantity);
				
				if (rowsAffected > 0) {
					// request.setAttribute("success", "Added To Cart");
					response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&success=Quantity%20Minus%20One");
//					response.sendRedirect(request.getContextPath() + "/customer/cart/summary&page=" + page);
					return;
				} else {
					response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
			return;
		}
	}

}
