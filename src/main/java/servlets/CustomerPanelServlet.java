package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.Cart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			String context = request.getParameter("function");
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
			String query = "SELECT email, phone FROM users WHERE id = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				request.setAttribute("email", rs.getString("email"));
				request.setAttribute("phone", rs.getString("phone"));
			} else {
				response.sendRedirect("AuthenticateServlet");
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
	}

	private void getCart(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		List<Cart> cartItems = new ArrayList<>();
		// Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 10;
        int offset = (page - 1) * limit;
        int totalRecords = 0;
        int subtotal = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
			String query = "SELECT COUNT(*), CAST(SUM(b.price*c.quantity) AS DECIMAL(7, 2)) AS subtotal\r\n"
					+ "FROM cart c\r\n"
					+ "INNER JOIN books b ON c.book_id = b.book_id\r\n"
					+ "INNER JOIN authors a ON b.author_id = a.author_id\r\n"
					+ "WHERE user_id = ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet ts = pst.executeQuery();
            ts.next();
            totalRecords = ts.getInt(1);
            subtotal = ts.getInt(2);
            
            query = "SELECT b.image, b.book_id, b.title, a.name AS author, b.price, b.quantity AS max, c.quantity\r\n"
            		+ "FROM cart c\r\n"
            		+ "INNER JOIN books b ON c.book_id = b.book_id\r\n"
            		+ "INNER JOIN authors a ON b.author_id = a.author_id\r\n"
            		+ "WHERE user_id = ?\r\n"
            		+ "LIMIT ?, ?;\r\n";
			pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setInt(2, offset);
            pst.setInt(3, limit);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Cart item = new Cart();
				item.setBookId(rs.getInt("book_id"));
				item.setTitle(rs.getString("title"));
				item.setAuthor(rs.getString("author"));
				item.setPrice(rs.getBigDecimal("price"));
				item.setMax(rs.getInt("max"));
				item.setQuantity(rs.getInt("quantity"));
				item.setImage(rs.getString("image"));

				cartItems.add(item);
			}

			con.close();
			// Calculate total pages
            int totalPages = (int) Math.ceil((double) totalRecords / limit);
            request.setAttribute("totalPages", totalPages);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.setAttribute("cart", cartItems);
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("subtotal", subtotal);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/customerCart.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("role") != null
				&& session.getAttribute("role").equals("customer")) {
			String context = request.getParameter("function");
			int userId = (int) session.getAttribute("userId");
			if (context == null) {
				request.getRequestDispatcher("customerHeader.jsp").forward(request, response);
			} else if (context.equals("editProfile")) {
				editProfile(request, response, session, userId);
			} else if (context.equals("changePassword")) {
				changePassword(request, response, userId);
			} else if (context.equals("addToCart")) {
				addToCart(request, response, userId);
			}
		} else {
			response.sendRedirect("AuthenticateServlet");
		}
	}

	private void editProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session, int userId)
			throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
			String query = "UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, phone);
			pst.setInt(4, userId);
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				request.setAttribute("success", "Profile Updated Successfully");
				session.setAttribute("username", username);
				getProfile(request, response, userId);
			} else {
				request.setAttribute("err", "Something Went Wrong");
			}

			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
	}

	private void changePassword(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		try {
			String password = request.getParameter("password");
			String passwordCheck = request.getParameter("passwordCheck");

			if (password.length() < 8) {
				request.setAttribute("err", "Your password must have a minimum length of 8 characters");
			} else if (!password.equals(passwordCheck)) {
				request.setAttribute("err", "The passwords you entered do not match");
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
				String query = "UPDATE users SET password = MD5(?) WHERE user_id = ?;";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1, password);
				pst.setInt(2, userId);
				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					request.setAttribute("success", "Password Changed Successfully");
				} else {
					request.setAttribute("err", "Something Went Wrong");
				}

				pst.close();
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
		request.getRequestDispatcher("customerChangePassword.jsp").forward(request, response);
	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		try {
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
			String query = "INSERT INTO cart (user_id, book_id, quantity)\r\n" + "VALUES (?, ?, ?)\r\n"
					+ "ON DUPLICATE KEY UPDATE\r\n" + "    quantity = quantity + ?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setInt(2, bookId);
			pst.setInt(3, quantity);
			pst.setInt(4, quantity);
			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {
				request.setAttribute("success", "Added To Cart");
				getProfile(request, response, userId);
			} else {
				request.setAttribute("err", "Something Went Wrong");
			}

			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
	}

}
