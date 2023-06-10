package books;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        String bookName = request.getParameter("books");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
            String query = "";
            ResultSet rs;
            if (bookName != null) {
            	query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id WHERE b.title LIKE ?";
            	PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, "%"+bookName+"%");
                rs = pst.executeQuery();
            }else {
            	Statement stmt = con.createStatement();
            	query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id";
            	rs = stmt.executeQuery(query);
            }
            
            
            
            while(rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPublicationDate(rs.getTimestamp("publication_date"));
                book.setISBN(rs.getString("ISBN"));
                book.setRating(rs.getInt("rating"));
                book.setDescription(rs.getString("description"));
                book.setPublisherId(rs.getInt("publisher_id"));
                book.setGenreId(rs.getInt("genre_id"));
                books.add(book);
            }

            
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("err", e.getMessage());
        }
        
        request.setAttribute("books", books);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
