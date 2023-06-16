package servlets;

import classes.Author;
import classes.Publisher;
import classes.Genre;
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminPanelServlet
 */
@WebServlet("/AdminPanelServlet")
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
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
			String context = request.getParameter("p");
			if (context == null) {
				request.getRequestDispatcher("adminPanel.jsp").forward(request, response);
			} else if (context.equals("addBook")) {
				addBookContext(request, response);
			}

		} else {
			response.sendRedirect("BookServlet");
		}
	}

	private void addBookContext(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");

		    // Fetch Authors
		    String authorQuery = "SELECT * FROM authors";
		    Statement authorStmt = conn.createStatement();
		    ResultSet authorRs = authorStmt.executeQuery(authorQuery);
		    List<Author> authors = new ArrayList<>();
		    while (authorRs.next()) {
		        int authorId = authorRs.getInt("author_id");
		        String authorName = authorRs.getString("name");
		        Author author = new Author(authorId, authorName);
		        authors.add(author);
		    }
		    authorRs.close();
		    authorStmt.close();

		    // Fetch Publishers
		    String publisherQuery = "SELECT * FROM publishers";
		    Statement publisherStmt = conn.createStatement();
		    ResultSet publisherRs = publisherStmt.executeQuery(publisherQuery);
		    List<Publisher> publishers = new ArrayList<>();
		    while (publisherRs.next()) {
		        int publisherId = publisherRs.getInt("publisher_id");
		        String publisherName = publisherRs.getString("name");
		        Publisher publisher = new Publisher(publisherId, publisherName);
		        publishers.add(publisher);
		    }
		    publisherRs.close();
		    publisherStmt.close();

		    // Fetch Genres
		    String genreQuery = "SELECT * FROM genres";
		    Statement genreStmt = conn.createStatement();
		    ResultSet genreRs = genreStmt.executeQuery(genreQuery);
		    List<Genre> genres = new ArrayList<>();
		    while (genreRs.next()) {
		        int genreId = genreRs.getInt("genre_id");
		        String genreName = genreRs.getString("name");
		        Genre genre = new Genre(genreId, genreName);
		        genres.add(genre);
		    }
		    genreRs.close();
		    genreStmt.close();

		    conn.close();

		    request.setAttribute("authors", authors);
		    request.setAttribute("publishers", publishers);
		    request.setAttribute("genres", genres);

		    request.getRequestDispatcher("adminPanel.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
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
        if (action != null && action.equals("addBook")) {
            addBook(request, response);
        } else {
        	request.getRequestDispatcher("adminPanel.jsp").forward(request, response);
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
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");

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

	        // Insert new publisher if publisherId is 0
	        if (newPublisherId == 0) {
	            String newPublisherName = request.getParameter("new_publisher_name");
	            newPublisherId = insertNewPublisher(newPublisherName, conn);
	        }

	        // Perform book insertion
	        boolean bookInserted = insertBook(title, newAuthorId, price, quantity, newPublisherId, newGenreId, ISBN, rating, description, publicationDate, conn);

	        if (bookInserted) {
	        	request.setAttribute("success", "Book Added Successfully");
	        } else {
	        	request.setAttribute("err", "Something Went Wrong");
	        }

	        conn.close();
	        response.sendRedirect("AdminPanelServlet?p=addBook&success=Book%20Added%20Successfully");
	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("err", e.getMessage());
	        response.sendRedirect("AdminPanelServlet?p=addBook&err="+e.getMessage());
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

	private boolean insertBook(String title, int authorId, double price, int quantity, int publisherId, int genreId, String ISBN, int rating, String description, Date publicationDate, Connection conn) {
	    boolean bookInserted = false;
	    try {
	        String query = "INSERT INTO Books (title, author_id, price, quantity, publisher_id, genre_id, ISBN, rating, description, publication_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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



}
