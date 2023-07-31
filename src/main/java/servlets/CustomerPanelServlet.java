package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.Cart;
import classes.User;
import util.AppUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ws.rs.core.GenericType;

@WebServlet("/CustomerPanelServlet")
public class CustomerPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CustomerPanelServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("role") != null
				&& session.getAttribute("role").equals("customer")) {
			String context = request.getParameter("p");
			int userId = (int) session.getAttribute("userId");
			if (context == null) {
				response.sendRedirect("BookServlet");
			} else if (context.equals("myProfile")) {
				getProfile(request, response, userId);
			} else if (context.equals("changePasswordForm")) {
				request.getRequestDispatcher("customerChangePassword.jsp").forward(request, response);
			} else if (context.equals("myCart")) {
				getCart(request, response, userId);
			}
		} else {
			response.sendRedirect("AuthenticateServlet");
		}
	}

	private void getProfile(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		User customer = new User();
		try {
			AppUtil app = new AppUtil();
			String url = "customer/getUserById/"+userId;
			Response res = app.get(url);
			customer = (User) res.readEntity(new GenericType<User>() {});
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.setAttribute("customer", customer);
		request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
	}

	private void getCart(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		List<Cart> cartItems = new ArrayList<>();
		// Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 5;
        int offset = (page - 1) * limit;
        int totalRecords = 0;
        int totalPages = 0;
        double subtotal = 0;
		try {
			AppUtil app = new AppUtil();
			String url = "customer/getCart/"+userId;
			Response res = app.get(url);
			cartItems = (List<Cart>) res.readEntity(new GenericType<List<Cart>>() {});
			
			url = "customer/getTotalCartItems/"+userId;
			res = app.get(url);
			totalRecords = (Integer) res.readEntity(new GenericType<Integer>() {});
			
			// Calculate total pages
            totalPages = (int) Math.ceil((double) totalRecords / limit);
            
            url = "customer/getSummary/"+userId;
			res = app.get(url);
			subtotal = (Double) res.readEntity(new GenericType<Double>() {});
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("role") != null
				&& session.getAttribute("role").equals("customer")) {
			String context = request.getParameter("p");
			int userId = (int) session.getAttribute("userId");
			if (context == null) {
				request.getRequestDispatcher("customerHeader.jsp").forward(request, response);
			} else if (context.equals("editProfile")) {
				editProfile(request, response, session, userId);
			} else if (context.equals("changePassword")) {
				changePassword(request, response, userId);
			} else if (context.equals("addToCart")) {
				addToCart(request, response, userId);
			} else if (context.equals("deleteFromCart")) {
				deleteFromCart(request, response);
			}
		} else {
			response.sendRedirect("AuthenticateServlet");
		}
	}

	@SuppressWarnings("unchecked")
	private void editProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session, int userId)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		int rowsAffected = 0;
		try {
			AppUtil app = new AppUtil();
			JSONObject json = new JSONObject();
			json.put("username", username);
        	json.put("email", email);
        	json.put("phone", phone);
			String url = "customer/editUserById/"+userId;
			Response res = app.put(url, json);
			rowsAffected = (Integer) res.readEntity(new GenericType<Integer>() {});
		
			if (rowsAffected > 0) {
				session.setAttribute("username", username);;
				response.sendRedirect("CustomerPanelServlet?p=myProfile&success=Profile%20Updated%20Successfully");
			} else {
				response.sendRedirect("CustomerPanelServlet?p=myProfile&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void changePassword(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		int rowsAffected = 0;
		try {
			if (password.length() < 8) {
				response.sendRedirect("CustomerPanelServlet?p=changePasswordForm&err=Minimum%20Length%20Of%208%20Characters");
			} else if (!password.equals(passwordCheck)) {
				response.sendRedirect("CustomerPanelServlet?p=changePasswordForm&err=Passwords%20Entered%20Do%20Not%20Match");
			} else {
				AppUtil app = new AppUtil();
				JSONObject json = new JSONObject();
	        	json.put("password", password);
				String url = "customer/changeUserPasswordById/"+userId;
				Response res = app.put(url, json);
				rowsAffected = (Integer) res.readEntity(new GenericType<Integer>() {});
				
				if (rowsAffected > 0) {
					response.sendRedirect("CustomerPanelServlet?p=changePasswordForm&success=Password%20Changed%20Successfully");
				} else {
					response.sendRedirect("CustomerPanelServlet?p=changePasswordForm&err=Something%20Went%20Wrong");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("CustomerPanelServlet?p=changePasswordForm&err=Something%20Went%20Wrong");
		}
	}

	@SuppressWarnings("unchecked")
	private void addToCart(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		int rowsAffected = 0;
		try {
			AppUtil app = new AppUtil();
			JSONObject json = new JSONObject();
			json.put("bookId", bookId);
        	json.put("quantity", quantity);
			String url = "customer/addToCart/"+userId;
			Response res = app.post(url, json);
			rowsAffected = (Integer) res.readEntity(new GenericType<Integer>() {});
			
			if (rowsAffected > 0) {
				request.setAttribute("success", "Added To Cart");
				getProfile(request, response, userId);
			} else {
				response.sendRedirect("CustomerPanelServlet?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("CustomerPanelServlet?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
		}
	}
	
	private void deleteFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int cartId = Integer.parseInt(request.getParameter("cartId"));
		int rowsAffected = 0;
		try {
			AppUtil app = new AppUtil();
			String url = "customer/deleteFromCart/"+cartId;
			Response res = app.delete(url);
			rowsAffected = (Integer) res.readEntity(new GenericType<Integer>() {});

			if (rowsAffected > 0) {
				response.sendRedirect("CustomerPanelServlet?p=myCart&page=" + page + "&success=Deleted%20From%20Cart");
			} else {
				response.sendRedirect("CustomerPanelServlet?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("CustomerPanelServlet?p=myCart&page=" + page + "&err=Something%20Went%20Wrong");
		}
	}

}
