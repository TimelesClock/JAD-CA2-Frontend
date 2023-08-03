package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.*;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Servlet implementation class AdminAddBook
 */
@WebServlet("/admin/book/edit")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AdminEditBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminEditBook() {
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
		// no go away
		response.sendRedirect(request.getContextPath() + "/admin/book");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String title = request.getParameter("title");
			BigDecimal price = new BigDecimal(request.getParameter("price"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			String publicationDate = request.getParameter("publication_date");
			String ISBN = request.getParameter("ISBN");
			int rating = Integer.parseInt(request.getParameter("rating"));
			String description = request.getParameter("description");
			Part imageFile = request.getPart("image");
			String prev_url = request.getParameter("prev_url");
			int bookId = Integer.parseInt(request.getParameter("book_id"));
			// Create a BookDAO instance and call the addBook method
			BookDAO bookDAO = new BookDAO();
			try {
				int newAuthorId = Integer.parseInt(request.getParameter("author_id"));
				int newGenreId = Integer.parseInt(request.getParameter("genre_id"));
				int newPublisherId = Integer.parseInt(request.getParameter("publisher_id"));

				// Insert new author if authorId is 0
				if (newAuthorId == 0) {
					String newAuthorName = request.getParameter("new_author_name");
					newAuthorId = AuthorDAO.insertNewAuthor(newAuthorName);
				}

				// Insert new genre if genreId is 0
				if (newGenreId == 0) {
					String newGenreName = request.getParameter("new_genre_name");
					newGenreId = GenreDAO.insertNewGenre(newGenreName);
				}

				// Insert new publisher if publisherId 0
				if (newPublisherId == 0) {
					String newPublisherName = request.getParameter("new_publisher_name");
					newPublisherId = PublisherDAO.addPublisher(newPublisherName);
				}

				Boolean updated = bookDAO.editBook(bookId, title, newAuthorId, price, quantity, publicationDate, ISBN,
						rating, description, newPublisherId, newGenreId, imageFile, prev_url);
				if (updated) {
					response.sendRedirect(
							request.getContextPath() + "/admin/book?success=Book%20Updated%20Successfully!");
				} else {
					response.sendRedirect(request.getContextPath() + "/admin/book?err=Book%20Was%20Not%20Updated!");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				// Handle database error and response to the client
				response.sendRedirect(request.getContextPath() + "/admin/book?err=Something%20Went%20Wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/book?err=Something%20Went%20Wrong");
		}
	}

}
