package servlets.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cart;
import models.CartDAO;
import models.Order;
import models.OrderDAO;

/**
 * Servlet implementation class CustomerOrder
 */
@WebServlet("/customer/order")
public class CustomerOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		HashMap<String,Object> hashmap = new HashMap<>();
		ArrayList<Order> orders = new ArrayList<>();
		ArrayList<Cart> cartItems = new ArrayList<>();
		// Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 5;
        int offset = (page - 1) * limit;
        int totalRecords = -1;
        int totalPages = 0;
        try {
        	OrderDAO db = new OrderDAO();
        	hashmap = db.getOrderHistory(userid, limit, offset);
        	
        	orders = (ArrayList<Order>) hashmap.get("orders");
        	
        	if (orders.size() == 0) {
    			request.setAttribute("err", "Order history is empty.");
    		}
        	
        	cartItems = (ArrayList<Cart>) hashmap.get("cartItems");
        	
        	if (orders.size() != cartItems.size()) {
    			request.setAttribute("err", "Something went wrong.");
    		}
        	
        	totalRecords = db.getTotalOrderHistory(userid);
        	
        	if (totalRecords == -1) {
    			request.setAttribute("err", "Total orders made not found.");
    		}

            totalPages = (int) Math.ceil((double) totalRecords / limit);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
        request.setAttribute("orders", orders);
		request.setAttribute("cart", cartItems);
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/customer/customerOrder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
