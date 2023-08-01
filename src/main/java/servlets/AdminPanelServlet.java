package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import util.*;
import javax.ws.rs.core.Response;

import models.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Servlet implementation class AdminPanelServlet
 */
@WebServlet("/admin")
public class AdminPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminPanelServlet() {
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
		try {
			AdminUtil.checkAdmin(request, response);

			String context = request.getParameter("p");
			if (context == null) {
				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
			}
//			} else if (context.equals("addBook")) {
//				addContext(request, response);
//			} else if (context.equals("editBook")) {
//				addContext(request, response);
//			} else if (context.equals("deleteBook")) {
//				addBookContext(request);
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//
//			} else if (context.equals("viewInventory")) {
//				addBookContext(request);
//				addContext(request, response);
//
//			} else if (context.equals("editInventory")) {
//				addBookContext(request);
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//			} else if (context.equals("viewCustomer")) {
//				addUserContext(request);
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//			} else if (context.equals("editCustomer")) {
//				addUserContext(request);
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//			} else if (context.equals("deleteCustomer")) {
//				addUserContext(request);
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//			} else {
//				request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
		} else if (action.equals("addBook")) {
			addBook(request, response);
		} else if (action.equals("editBook")) {
			editBook(request, response);
		} else if (action.equals("deleteBook")) {
			deleteBook(request, response);
		} else if (action.equals("editInventory")) {
			editInventory(request, response);
		} else if (action.equals("addCustomer")) {
			addCustomer(request, response);
		} else if (action.equals("editCustomer")) {
			editCustomer(request, response);
		} else if (action.equals("deleteCustomer")) {
			deleteCustomer(request, response);
		} else {
			request.getRequestDispatcher("Admin/adminPanel.jsp").forward(request, response);
		}

	}

	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		int authorId = Integer.parseInt(request.getParameter("author_id"));
		String newAuthorName = request.getParameter("new_author_name");
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String ISBN = request.getParameter("ISBN");
		int rating = Integer.parseInt(request.getParameter("rating"));
		String description = request.getParameter("description");
		Date publicationDate = Date.valueOf(request.getParameter("publication_date"));
		String image = request.getParameter("image");
		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);

			int newAuthorId = authorId;
			int newGenreId = Integer.parseInt(request.getParameter("genre_id"));
			int newPublisherId = Integer.parseInt(request.getParameter("publisher_id"));

			// Insert new author if authorId is 0
			if (authorId == 0) {
				newAuthorId = insertNewAuthor(newAuthorName, conn);
			}

			// Insert new genre if genreId 0
			if (newGenreId == 0) {
				String newGenreName = request.getParameter("new_genre_name");
				newGenreId = insertNewGenre(newGenreName, conn);
			}

			// Insert new publisher if publisherId is 0
			if (newPublisherId == 0) {
				String newPublisherName = request.getParameter("new_publisher_name");
				newPublisherId = insertNewPublisher(newPublisherName, conn);
			}

			// Perform book insertion
			boolean bookInserted = insertBook(title, newAuthorId, price, quantity, newPublisherId, newGenreId, ISBN,
					rating, description, publicationDate, image, conn);

			if (bookInserted) {
				request.setAttribute("success", "Book Added Successfully");
			} else {
				request.setAttribute("err", "Something Went Wrong");
			}

			conn.close();
			response.sendRedirect("AdminPanelServlet?p=addBook&success=Book%20Added%20Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
			response.sendRedirect("AdminPanelServlet?p=addBook&err=" + e.getMessage());
		}
	}

	private int insertNewAuthor(String authorName, Connection conn) {
		int newAuthorId = -1;
		try {
			String query = "INSERT INTO authors (name) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, authorName);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					newAuthorId = rs.getInt(1);
				}
				rs.close();
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newAuthorId;
	}

	private void editBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		String title = request.getParameter("title");
		int authorId = Integer.parseInt(request.getParameter("author_id"));
		String newAuthorName = request.getParameter("new_author_name");
		double price = Double.parseDouble(request.getParameter("price"));
		String ISBN = request.getParameter("ISBN");
		int rating = Integer.parseInt(request.getParameter("rating"));
		String description = request.getParameter("description");
		Date publicationDate = Date.valueOf(request.getParameter("publication_date"));
		String image = request.getParameter("image");

		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);

			int newAuthorId = authorId;
			int newGenreId = Integer.parseInt(request.getParameter("genre_id"));
			int newPublisherId = Integer.parseInt(request.getParameter("publisher_id"));

			// Insert new author if authorId is 0
			if (authorId == 0) {
				newAuthorId = insertNewAuthor(newAuthorName, conn);
			}

			// Insert new genre if genreId is 0
			if (newGenreId == 0) {
				String newGenreName = request.getParameter("new_genre_name");
				newGenreId = insertNewGenre(newGenreName, conn);
			}

			// Insert new publisher if publisherId 0
			if (newPublisherId == 0) {
				String newPublisherName = request.getParameter("new_publisher_name");
				newPublisherId = insertNewPublisher(newPublisherName, conn);
			}

			updateBook(bookId, title, newAuthorId, price, newPublisherId, newGenreId, ISBN, rating, description,
					publicationDate, image, conn);

			conn.close();
			response.sendRedirect("AdminPanelServlet?p=editBook&success=Book%20Updated%20Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
			response.sendRedirect("AdminPanelServlet?p=editBook&err=" + e.getMessage());
		}
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);
			int bookId = Integer.parseInt(request.getParameter("book_id"));

			String query = "DELETE FROM Books WHERE book_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, bookId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				response.sendRedirect("AdminPanelServlet?p=deleteBook&success=Book%20Deleted%20Successfully");
			} else {
				response.sendRedirect("AdminPanelServlet?p=deleteBook&err=Book%20was%20not%20deleted");
			}

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminPanelServlet?p=deleteBook&err=" + e.getMessage());

		}
	}

	private int insertNewGenre(String genreName, Connection conn) {
		int newGenreId = -1;
		try {
			String query = "INSERT INTO genres (name) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, genreName);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					newGenreId = rs.getInt(1);
				}
				rs.close();
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newGenreId;
	}

	private int insertNewPublisher(String publisherName, Connection conn) {
		int newPublisherId = -1;
		try {
			String query = "INSERT INTO publishers (name) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, publisherName);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					newPublisherId = rs.getInt(1);
				}
				rs.close();
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newPublisherId;
	}

	private boolean insertBook(String title, int authorId, double price, int quantity, int publisherId, int genreId,
			String ISBN, int rating, String description, Date publicationDate, String image, Connection conn) {
		boolean bookInserted = false;
		try {
			String query = "INSERT INTO Books (title, author_id, price, quantity, publisher_id, genre_id, ISBN, rating, description, publication_date,image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, title);
			stmt.setInt(2, authorId);
			stmt.setDouble(3, price);
			stmt.setInt(4, quantity);
			stmt.setInt(5, publisherId);
			stmt.setInt(6, genreId);
			stmt.setString(7, ISBN);
			stmt.setInt(8, rating);
			stmt.setString(9, description);
			stmt.setDate(10, publicationDate);
			stmt.setString(11, image);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				bookInserted = true;
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookInserted;
	}

	private boolean updateBook(int bookId, String title, int authorId, double price, int publisherId, int genreId,
			String ISBN, int rating, String description, Date publicationDate, String image, Connection conn) {
		boolean bookUpdated = false;
		try {
			String query = "UPDATE Books SET title = ?, author_id = ?, price = ?, publisher_id = ?, genre_id = ?, ISBN = ?, rating = ?, description = ?, publication_date = ?,image = ? WHERE book_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, title);
			stmt.setInt(2, authorId);
			stmt.setDouble(3, price);
			stmt.setInt(4, publisherId);
			stmt.setInt(5, genreId);
			stmt.setString(6, ISBN);
			stmt.setInt(7, rating);
			stmt.setString(8, description);
			stmt.setDate(9, publicationDate);
			stmt.setString(10, image);
			stmt.setInt(11, bookId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				bookUpdated = true;
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookUpdated;
	}

	private void editInventory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("book_id"));
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);
			String updateQuery = "UPDATE books SET price = ?, quantity = ? WHERE book_id = ?";
			PreparedStatement stmt = conn.prepareStatement(updateQuery);
			stmt.setDouble(1, price);
			stmt.setInt(2, quantity);
			stmt.setInt(3, bookId);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				stmt.close();
				response.sendRedirect("AdminPanelServlet?p=editInventory&success=Inventory%20updated%20successfully");
				return;
			} else {
				stmt.close();
				response.sendRedirect("AdminPanelServlet?p=editInventory&error=Failed%20to%20update%20inventory");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminPanelServlet?p=editInventory&error=" + e.getMessage());
			return;
		}
	}

	private void addCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String role = "customer";

		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);

			String checkQuery = "SELECT COUNT(*) FROM Users WHERE email = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			checkStmt.setString(1, email);
			ResultSet resultSet = checkStmt.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			checkStmt.close();

			if (count > 0) {
				response.sendRedirect("AdminPanelServlet?p=addCustomer&error=Duplicate%20Email");
				conn.close();
				return;
			}

			String insertQuery = "INSERT INTO Users (name, email, password, role, phone) VALUES (?, ?, MD5(?), ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);
			stmt.setString(4, role);
			stmt.setString(5, phone);

			int rowsAffected = stmt.executeUpdate();
			stmt.close();

			if (rowsAffected > 0) {
				response.sendRedirect("AdminPanelServlet?p=addCustomer&success=Customer%20Added%20Successfully");
			} else {
				response.sendRedirect("AdminPanelServlet?p=addCustomer&err=Failed%20to%20Add%20Customer");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminPanelServlet?p=addCustomer&error=" + e.getMessage());
		}
	}

	private void editCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int customerId = Integer.parseInt(request.getParameter("customer_id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");

		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);

			if (password == null || password.isEmpty()) {
				String updateQuery = "UPDATE Users SET name = ?, email = ?, phone = ? WHERE user_id = ?";
				PreparedStatement stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, name);
				stmt.setString(2, email);
				stmt.setString(3, phone);
				stmt.setInt(4, customerId);
				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					stmt.close();
					response.sendRedirect("AdminPanelServlet?p=editCustomer&success=Customer%20updated%20successfully");
					return;
				} else {
					stmt.close();
					response.sendRedirect("AdminPanelServlet?p=editCustomer&error=Failed%20to%20update%20customer");
					return;
				}
			} else {
				String updateQuery = "UPDATE Users SET name = ?, email = ?, password = MD5(?), phone = ? WHERE user_id = ?";
				PreparedStatement stmt = conn.prepareStatement(updateQuery);
				stmt.setString(1, name);
				stmt.setString(2, email);
				stmt.setString(3, password);
				stmt.setString(4, phone);
				stmt.setInt(5, customerId);
				int rowsAffected = stmt.executeUpdate();

				if (rowsAffected > 0) {
					stmt.close();
					response.sendRedirect("AdminPanelServlet?p=editCustomer&success=Customer%20updated%20successfully");
					return;
				} else {
					stmt.close();
					response.sendRedirect("AdminPanelServlet?p=editCustomer&error=Failed%20to%20update%20customer");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminPanelServlet?p=editCustomer&error=" + e.getMessage());
			return;
		}
	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext context = getServletContext();
			Connection conn = DatabaseUtil.getConnection(context);
			int customerId = Integer.parseInt(request.getParameter("customer_id"));

			String query = "DELETE FROM Users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, customerId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				response.sendRedirect("AdminPanelServlet?p=deleteCustomer&success=Customer%20Deleted%20Successfully");
			} else {
				response.sendRedirect("AdminPanelServlet?p=deleteCustomer&err=Customer%20was%20not%20deleted");
			}

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("AdminPanelServlet?p=deleteCustomer&err=" + e.getMessage());

		}
	}

}
