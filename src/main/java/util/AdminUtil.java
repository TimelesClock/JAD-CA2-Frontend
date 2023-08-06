package util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import models.Author;
import models.AuthorDAO;
import models.Book;
import models.BookDAO;
import models.Genre;
import models.GenreDAO;
import models.Publisher;
import models.PublisherDAO;
import models.User;
import models.UserDAO;
import models.AddressDAO;
import models.Address;
import models.Order;
import models.OrderDAO;

public class AdminUtil {
	public static Boolean checkAdmin(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userid") == null) {
				return false;
			}

			UserDAO db = new UserDAO();
			String userid = (String) session.getAttribute("userid");
			String role = db.getRole(userid);
			if (role == null || !role.equals("admin")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static void addContext(HttpServletRequest request) throws ServletException, IOException {
		try {
			List<Author> authors = new ArrayList<>();
			AuthorDAO au = new AuthorDAO();
			authors = au.getAuthors();

			List<Publisher> publishers = new ArrayList<>();
			PublisherDAO pu = new PublisherDAO();
			publishers = pu.getPublishers();

			List<Genre> genres = new ArrayList<>();
			GenreDAO ge = new GenreDAO();
			genres = ge.getGenres();

			request.setAttribute("authors", authors);
			request.setAttribute("publishers", publishers);
			request.setAttribute("genres", genres);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addBookContext(HttpServletRequest request, Integer page,String search) {
		try {
			BookDAO db = new BookDAO();
			ArrayList<Book> books = new ArrayList<Book>();
			if (page == null) {
				page = 1;
			}
			Integer limit = 25;
			Integer offset = (page - 1) * limit;

			books = db.getAllBooks(limit, offset,search);

			request.setAttribute("books", books);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addBookCountContext(HttpServletRequest request,String search) {
		try {
			BookDAO db = new BookDAO();
			Integer bookCount = db.getAllTotalBooks(search);
			Integer pages = (int) Math.ceil((double) bookCount / 25);
			System.out.println(pages);
			request.setAttribute("totalPages", pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addUserContext(HttpServletRequest request, Integer page,String search) {
		try {
			UserDAO db = new UserDAO();
			ArrayList<User> users = new ArrayList<User>();
			if (page == null) {
				page = 1;
			}
			Integer limit = 25;
			Integer offset = (page - 1) * limit;

			users = db.getUsers(limit, offset,search);

			request.setAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addResellerContext(HttpServletRequest request, Integer page,String search) {
		try {
			UserDAO db = new UserDAO();
			ArrayList<User> users = new ArrayList<User>();
			if (page == null) {
				page = 1;
			}
			Integer limit = 25;
			Integer offset = (page - 1) * limit;

			users = db.getResellers(limit, offset,search);

			request.setAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addResellerCountContext(HttpServletRequest request,String search) {
		try {
			UserDAO db = new UserDAO();
			Integer userCount = db.getTotalResellers(search);
			Integer pages = (int) Math.ceil((double) userCount / 25);
			request.setAttribute("totalPages", pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addUserCountContext(HttpServletRequest request,String search) {
		try {
			UserDAO db = new UserDAO();
			Integer userCount = db.getTotalUsers(search);
			Integer pages = (int) Math.ceil((double) userCount / 25);
			request.setAttribute("totalPages", pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addAddressContext(HttpServletRequest request) {
		try {
			AddressDAO db = new AddressDAO();
			ArrayList<Address> addresses = new ArrayList<Address>();
			addresses = db.getAddresses();
			request.setAttribute("addresses",addresses);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addOrderContext(HttpServletRequest request, Integer page,String status) {
		try {
			OrderDAO db = new OrderDAO();
			List<Order> orders = new ArrayList<Order>();
			if (page == null) {
				page = 1;
			}
			Integer limit = 25;
			Integer offset = (page - 1) * limit;

			orders = db.getOrders(limit, offset,status);

			request.setAttribute("orders", orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addOrderCountContext(HttpServletRequest request,String status) {
		try {
			OrderDAO db = new OrderDAO();
			Integer bookCount = db.getTotalOrders(status);
			Integer pages = (int) Math.ceil((double) bookCount / 25);
			request.setAttribute("totalPages", pages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
