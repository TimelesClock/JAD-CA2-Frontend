package servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Book;
import models.BookDAO;

@WebServlet("/BookDetails")
public class BookDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookDetailsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Book book = new Book();
		try {
			int bookId = 0;
			try {
				bookId = (int) Integer.parseInt(request.getParameter("bookId"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
	            System.out.println("error");
	            request.setAttribute("err", e.getMessage());
	            response.sendRedirect(request.getContextPath() + "/home");
	            return;
			}
			BookDAO db = new BookDAO();
            book = db.getBook(bookId);
            
            if (book.getISBN() == null) {
            	response.sendRedirect(request.getContextPath() + "/home");
            	return;
            }
            
		} catch(Exception e) {
            e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
		request.setAttribute("book", book);
        request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
	}
}
