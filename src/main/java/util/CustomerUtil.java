package util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cart;
import models.CartDAO;
import models.User;
import models.UserDAO;

public class CustomerUtil {
	public static Boolean checkCustomer(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userid") == null) {
				return false;
			}

			UserDAO db = new UserDAO();
			String userId = (String) session.getAttribute("userid");
			String role = db.getRole(userId);
			if (role == null || !role.equals("customer")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void addCartContext (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            return;
        }

		ArrayList<Cart> cartItems = new ArrayList<Cart>();
		try {
        	CartDAO db = new CartDAO();
        	cartItems = db.getCartItems(userid);
        	
        	if (cartItems.size() == 0) {
    			request.setAttribute("err", "Cart is empty.");
    		}        	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.setAttribute("cartItems", cartItems);
		return;
	}
	
	public static void addCartSummaryContext (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		int userid = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            return;
        }
		
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
		return;
	}
	
	public static void addCardContext (HttpServletRequest request, HttpServletResponse response, String cardNumber, String expMonth, String expYear, String cvv) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("cardNumber", cardNumber);
		request.setAttribute("expMonth", expMonth);
		request.setAttribute("expYear", expYear);
		request.setAttribute("cvv", cvv);
		return;
	}
}
