package servlets.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cart;
import models.CartDAO;

/**
 * Servlet implementation class CustomerCart
 */
@WebServlet("/customer/cart")
public class CustomerCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerCart() {
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
		List<Cart> cartItems = new ArrayList<>();
		// Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 5;
        int offset = (page - 1) * limit;
        int totalRecords = -1;
        int totalPages = 0;
        double subtotal = -1;
        try {
        	CartDAO db = new CartDAO();
        	cartItems = db.getCart(userid, limit, offset);
        	
        	if (cartItems.size() == 0) {
    			request.setAttribute("err", "Cart is empty.");
    		}
        	
        	totalRecords = db.getTotalCartItems(userid);
        	
        	if (totalRecords == -1) {
    			request.setAttribute("err", "Something went wrong.");
    		}

            totalPages = (int) Math.ceil((double) totalRecords / limit);
    		
            subtotal = db.getSubtotal(userid);
            
            if (subtotal == -1) {
    			request.setAttribute("err", "Subtotal failed to be retrieved.");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.setAttribute("cart", cartItems);
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("subtotal", subtotal);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/customerCart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
