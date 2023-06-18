package servlets;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Author;
import classes.Book;
import classes.Genre;
import classes.Publisher;
import util.DatabaseUtil;
@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public BookServlet() {
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        addContext(request, response);
        
        String bookName = request.getParameter("books");
        // Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 15;
        int offset = (page - 1) * limit;
        int totalRecords;
        try {
        	ServletContext context = getServletContext();
	    	Connection conn = DatabaseUtil.getConnection(context);
            String query = "";
            ResultSet rs,ts;
            
            String genre = request.getParameter("genre");
            String minRating = request.getParameter("minRating");
            String maxRating = request.getParameter("maxRating");
            String minPrice = request.getParameter("minPrice");
            String maxPrice = request.getParameter("maxPrice");
            if (bookName != null || genre != null || minRating != null || maxRating != null || minPrice != null || maxPrice != null) {
                query = "SELECT COUNT(*) FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id WHERE 1=1";
                if (bookName != null && !bookName.isEmpty()) {
                    query += " AND b.title LIKE ?";
                }
                if (genre != null && !genre.isEmpty()) {
                    query += " AND g.name = ?";
                }
                if (minRating != null && !minRating.isEmpty()) {
                    query += " AND b.rating >= ?";
                }
                if (maxRating != null && !maxRating.isEmpty()) {
                    query += " AND b.rating <= ?";
                }
                if (minPrice != null && !minPrice.isEmpty()) {
                    query += " AND b.price >= ?";
                }
                if (maxPrice != null && !maxPrice.isEmpty()) {
                    query += " AND b.price <= ?";
                }

                PreparedStatement pst = conn.prepareStatement(query);
                int parameterIndex = 1;
                if (bookName != null && !bookName.isEmpty()) {
                    pst.setString(parameterIndex++, "%" + bookName + "%");
                }
                if (genre != null && !genre.isEmpty()) {
                    pst.setString(parameterIndex++, genre);
                }
                if (minRating != null && !minRating.isEmpty()) {
                    pst.setString(parameterIndex++, minRating);
                }
                if (maxRating != null && !maxRating.isEmpty()) {
                    pst.setString(parameterIndex++, maxRating);
                }
                if (minPrice != null && !minPrice.isEmpty()) {
                    pst.setBigDecimal(parameterIndex++, new BigDecimal(minPrice));
                }
                if (maxPrice != null && !maxPrice.isEmpty()) {
                    pst.setBigDecimal(parameterIndex++, new BigDecimal(maxPrice));
                }

                ts = pst.executeQuery();
                ts.next();
                totalRecords = ts.getInt(1);

                query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id WHERE 1=1";
                if (bookName != null && !bookName.isEmpty()) {
                    query += " AND b.title LIKE ?";
                }
                if (genre != null && !genre.isEmpty()) {
                    query += " AND g.name = ?";
                }
                if (minRating != null && !minRating.isEmpty()) {
                    query += " AND b.rating >= ?";
                }
                if (maxRating != null && !maxRating.isEmpty()) {
                    query += " AND b.rating <= ?";
                }
                if (minPrice != null && !minPrice.isEmpty()) {
                    query += " AND b.price >= ?";
                }
                if (maxPrice != null && !maxPrice.isEmpty()) {
                    query += " AND b.price <= ?";
                }
                query += " LIMIT ?, ?";

                pst = conn.prepareStatement(query);
                parameterIndex = 1;
                if (bookName != null && !bookName.isEmpty()) {
                    pst.setString(parameterIndex++, "%" + bookName + "%");
                }
                if (genre != null && !genre.isEmpty()) {
                    pst.setString(parameterIndex++, genre);
                }
                if (minRating != null && !minRating.isEmpty()) {
                    pst.setString(parameterIndex++, minRating);
                }
                if (maxRating != null && !maxRating.isEmpty()) {
                    pst.setString(parameterIndex++, maxRating);
                }
                if (minPrice != null && !minPrice.isEmpty()) {
                    pst.setBigDecimal(parameterIndex++, new BigDecimal(minPrice));
                }
                if (maxPrice != null && !maxPrice.isEmpty()) {
                    pst.setBigDecimal(parameterIndex++, new BigDecimal(maxPrice));
                }
                pst.setInt(parameterIndex++, offset);
                pst.setInt(parameterIndex, limit);
                //Query builder ftw
                rs = pst.executeQuery();
            } else {
                query = "SELECT COUNT(*) FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id";
                PreparedStatement pst = conn.prepareStatement(query);
                ts = pst.executeQuery();
                ts.next();
                totalRecords = ts.getInt(1);

                query = "SELECT * FROM Books b INNER JOIN Genres g ON b.genre_id = g.genre_id LIMIT ?, ?";
                pst = conn.prepareStatement(query);
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
                book.setImage(rs.getString("image"));

                books.add(book);
            }
            
            conn.close();
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
    
	private void addContext(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ServletContext context = getServletContext();
	    	Connection conn = DatabaseUtil.getConnection(context);

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

			request.setAttribute("genres", genres);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
