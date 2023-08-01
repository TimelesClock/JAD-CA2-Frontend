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

public class AdminUtil {
	public static void checkAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException,SQLException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userid") == null) {
			response.sendRedirect(request.getContextPath()+"/home");
			return;
		}

		UserDAO db = new UserDAO();
		String userid = (String) session.getAttribute("userid");
		if (!db.getRole(userid).equals("admin")) {
			response.sendRedirect(request.getContextPath()+"/home");
			return;
		}
	}
	
	public static void addContext(HttpServletRequest request)throws ServletException, IOException {
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
	
	public static void addBookContext(HttpServletRequest request,Integer page) {
		try {
			BookDAO db = new BookDAO();
			ArrayList<Book> books = new ArrayList<Book>();
			if (page == null) {
				page = 1;
			}
			Integer limit = 25;
			Integer offset = (page-1) * limit;
			
			books = db.getAllBooks(limit,offset);
			
			request.setAttribute("books", books);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void addBookCountContext(HttpServletRequest request) {
		try {
			BookDAO db = new BookDAO();
			Integer bookCount = db.getTotalBooks();
			Integer pages = bookCount/25;
			request.setAttribute("totalPages",pages);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addUserContext(HttpServletRequest request) {
		try {
			AppUtil app = new AppUtil();
			List<User> users = new ArrayList<>();
			Response res = app.get("users/getUsers");

			if (res.getStatus() != Response.Status.OK.getStatusCode()) {
				request.setAttribute("err", res.getStatus() + " GET request error");

			}

			users = (ArrayList<User>) res.readEntity(new GenericType<ArrayList<User>>() {
			});
			request.setAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
