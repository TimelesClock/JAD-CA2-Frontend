package servlets;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookDetailsServlet")
public class bookDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public bookDetailsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookId = (int) Integer.parseInt(request.getParameter("bookId"));
		
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
            String query = "SELECT b.image, b.title, a.name AS author, b.price, b.quantity, p.name AS publisher, b.publication_date, b.ISBN, g.name AS genre, b.rating, b.description \r\n"
            		+ "FROM books b\r\n"
            		+ "LEFT JOIN authors a ON a.author_id = b.author_id\r\n"
            		+ "LEFT JOIN publishers p ON p.publisher_id = b.publisher_id\r\n"
            		+ "LEFT JOIN genres g ON g.genre_id = b.genre_id\r\n"
            		+ "WHERE book_id = ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            
            rs.next();
            request.setAttribute("bookId", bookId);
            Blob image = rs.getBlob("image");
            if (image != null) {
                request.setAttribute("image", (image.getBytes(1, (int) image.length())));
            } else {
            	request.setAttribute("image", image);
            }
            request.setAttribute("title", rs.getString("title"));
            request.setAttribute("author", rs.getString("author"));
            request.setAttribute("price", rs.getDouble("price"));
            request.setAttribute("quantity", rs.getInt("quantity"));
            request.setAttribute("publisher", rs.getString("publisher"));
            request.setAttribute("publicationDate", rs.getTimestamp("publication_date"));
            request.setAttribute("ISBN", rs.getString("ISBN"));
            request.setAttribute("genre", rs.getString("genre"));
            request.setAttribute("rating", rs.getInt("rating"));
            request.setAttribute("description", rs.getString("description"));
            
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", e.getMessage());
        }
        
        request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
	}
}
