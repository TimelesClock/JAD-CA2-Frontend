package servlets;
import java.io.IOException;
import java.sql.Blob;
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
import classes.Book;
@WebServlet("/BookServlet")
//test
public class bookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public bookServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        String bookName = request.getParameter("books");
        // Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 15;
        int offset = (page - 1) * limit;
        int totalRecords;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
            String query = "";
            ResultSet rs,ts;
            

            if (bookName != null) {
                query = "SELECT COUNT(*) FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id WHERE b.title LIKE ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, "%"+bookName+"%");
                ts = pst.executeQuery();
                ts.next();
                totalRecords = ts.getInt(1);

                query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id WHERE b.title LIKE ? LIMIT ?,?";
                pst = con.prepareStatement(query);
                pst.setString(1, "%"+bookName+"%");
                pst.setInt(2, offset);
                pst.setInt(3, limit);
                rs = pst.executeQuery();
            } else {
                query = "SELECT COUNT(*) FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id";
                PreparedStatement pst = con.prepareStatement(query);
                ts = pst.executeQuery();
                ts.next();
                totalRecords = ts.getInt(1);

                query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id LIMIT ?, ?";
                pst = con.prepareStatement(query);
                pst.setInt(1, offset);
                pst.setInt(2, limit);
                rs = pst.executeQuery();
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
                Blob blob = rs.getBlob("image");
                if (blob != null) {
                    book.setImage(blob.getBytes(1, (int) blob.length()));
                }

                books.add(book);
            }
            
            con.close();
            // Calculate total pages
            int totalPages = (int) Math.ceil((double) totalRecords / limit);
            request.setAttribute("totalPages", totalPages);
        } catch(Exception e) {
            e.printStackTrace();
            request.setAttribute("err", e.getMessage());
        }
        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
