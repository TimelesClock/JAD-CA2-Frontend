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
@WebServlet("/admin/book/add")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
maxFileSize = 1024 * 1024 * 10,
maxRequestSize = 1024 * 1024 * 50)
public class AdminAddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath()+"/admin/book");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		String title = request.getParameter("title");
        int authorId = Integer.parseInt(request.getParameter("author_id"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String publicationDate = request.getParameter("publicationDate");
        String ISBN = request.getParameter("ISBN");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String description = request.getParameter("description");
        int publisherId = Integer.parseInt(request.getParameter("publisher_id"));
        int genreId = Integer.parseInt(request.getParameter("genre_id"));
        Part imageFile = request.getPart("imageFile");

        // Create a BookDAO instance and call the addBook method
        BookDAO bookDAO = new BookDAO();
        try {
            int generatedBookId = bookDAO.addBook(title, authorId, price, quantity, publicationDate, ISBN, rating, description, publisherId, genreId, imageFile);
            // Assuming the book was successfully added to the database
            response.sendRedirect(request.getContextPath()+"/admin/book");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error and response to the client
            response.sendRedirect(request.getContextPath()+"/admin/book");
        }
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/admin/book");
		}
	}

}
