package servlets.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.CartDAO;

/**
 * Servlet implementation class CustomerCartSummary
 */
@WebServlet("/customer/cart/summary")
public class CustomerCartSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerCartSummary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        
		int totalQuantity = -1;
		double subtotal = -1;
		double gst = -1;
		double total = -1;
		try {
        	CartDAO db = new CartDAO();
        	totalQuantity = db.getTotalCartQuantity(userid);
        	
        	if (totalQuantity == -1) {
    			request.setAttribute("err", "Something went wrong.");
    		}
        	
            subtotal = db.getSubtotal(userid);
            
            if (subtotal == -1) {
    			request.setAttribute("err", "Subtotal failed to be retrieved.");
    		} else {
    			gst = 0.08 * subtotal;
    			total = subtotal + gst;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.setAttribute("totalQuantity", totalQuantity);
		request.setAttribute("subtotal", subtotal);
		request.setAttribute("gst", gst);
		request.setAttribute("total", total);
		System.out.println("totalQuantity: " + totalQuantity);
		response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&page=" + page + "&success=Quantity%20Plus%20One");
//        request.getRequestDispatcher("/customer/customerCart.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
