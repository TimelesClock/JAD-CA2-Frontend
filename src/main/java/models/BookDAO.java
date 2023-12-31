package models;

import dbaccess.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.Part;

import com.cloudinary.Cloudinary;

import cloudinary.*;

public class BookDAO {
	public ArrayList<Book> getBooks(HashMap<String, Object> filter, Integer limit, Integer offset) throws SQLException {
		Connection conn = DBConnection.getConnection();
		ArrayList<Book> books = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM books b WHERE 1=1";
			if (filter.get("search") != null) {
				sql += " AND b.title LIKE ?";
			}
			if (filter.get("genreId") != null) {
				sql += " AND b.genre_id = ?";
			}
			if (filter.get("minRating") != null) {
				sql += " AND b.rating >= ?";
			}
			if (filter.get("maxRating") != null) {
				sql += " AND b.rating <= ?";
			}
			if (filter.get("minPrice") != null) {
				sql += " AND b.price >= ?";
			}
			if (filter.get("maxPrice") != null) {
				sql += " AND b.price <= ?";
			}
			sql += " LIMIT ?,?;";

			PreparedStatement pst = conn.prepareStatement(sql);

			// Set parameters based on the condition
			int parameterIndex = 1;
			if (filter.get("search") != null) {
				pst.setString(parameterIndex++, "%" + filter.get("search") + "%");
			}
			if (filter.get("genreId") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("genreId"));
			}
			if (filter.get("minRating") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("minRating"));
			}
			if (filter.get("maxRating") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("maxRating"));
			}
			if (filter.get("minPrice") != null) {
				pst.setBigDecimal(parameterIndex++, new BigDecimal((Double) filter.get("minPrice")));
			}
			if (filter.get("maxPrice") != null) {
				pst.setBigDecimal(parameterIndex++, new BigDecimal((Double) filter.get("maxPrice")));
			}
			pst.setInt(parameterIndex++, offset);
			pst.setInt(parameterIndex, limit);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setAuthorId(rs.getInt("author_id"));
				book.setPrice(rs.getBigDecimal("price"));
				book.setQuantity(rs.getInt("quantity"));
				book.setPublicationDate(rs.getString("publication_date"));
				book.setISBN(rs.getString("ISBN"));
				book.setRating(rs.getInt("rating"));
				book.setDescription(rs.getString("description"));
				book.setPublisherId(rs.getInt("publisher_id"));
				book.setGenreId(rs.getInt("genre_id"));
				book.setImage(rs.getString("image_url"));

				books.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return books;
	}
 
	public Book getBook(Integer bookId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Book book = new Book();
		try {
			String sql = "SELECT b.image_url, b.title, a.author_id, a.name AS author, b.price, b.quantity, p.name AS publisher, b.publication_date, b.ISBN, g.genre_id, g.name AS genre, b.rating, b.description \r\n"
					+ "FROM books b\r\n" + "LEFT JOIN authors a ON a.author_id = b.author_id\r\n"
					+ "LEFT JOIN publishers p ON p.publisher_id = b.publisher_id\r\n"
					+ "LEFT JOIN genres g ON g.genre_id = b.genre_id\r\n" 
					+ "WHERE book_id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, bookId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				book.setBookId(bookId);
				book.setImage(rs.getString("image_url"));
				book.setTitle(rs.getString("title"));
				book.setAuthorId(rs.getInt("author_id"));
				book.setAuthorName(rs.getString("author"));
				book.setPrice(rs.getBigDecimal("price"));
				book.setQuantity(rs.getInt("quantity"));
				book.setPublisherName(rs.getString("publisher"));
				book.setPublicationDate(rs.getString("publication_date"));
				book.setISBN(rs.getString("ISBN"));
				book.setGenreId(rs.getInt("genre_id"));
				book.setGenreName(rs.getString("genre"));
				book.setRating(rs.getInt("rating"));
				book.setDescription(rs.getString("description"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return book;
	}

	public Integer getTotalBooks(HashMap<String, Object> filter) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer totalBooks = 0;
		try {
			String sql = "SELECT COUNT(*) AS total_books FROM books b WHERE 1=1";
			if (filter.get("search") != null) {
				sql += " AND b.title LIKE ?";
			}
			if (filter.get("genreId") != null) {
				sql += " AND b.genre_id = ?";
			}
			if (filter.get("minRating") != null) {
				sql += " AND b.rating >= ?";
			}
			if (filter.get("maxRating") != null) {
				sql += " AND b.rating <= ?";
			}
			if (filter.get("minPrice") != null) {
				sql += " AND b.price >= ?";
			}
			if (filter.get("maxPrice") != null) {
				sql += " AND b.price <= ?";
			}
			sql += ";";

			PreparedStatement pst = conn.prepareStatement(sql);

			// Set parameters based on the condition
			int parameterIndex = 1;
			if (filter.get("search") != null) {
				pst.setString(parameterIndex++, "%" + filter.get("search") + "%");
			}
			if (filter.get("genreId") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("genreId"));
			}
			if (filter.get("minRating") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("minRating"));
			}
			if (filter.get("maxRating") != null) {
				pst.setInt(parameterIndex++, (Integer) filter.get("maxRating"));
			}
			if (filter.get("minPrice") != null) {
				pst.setBigDecimal(parameterIndex++, new BigDecimal((Double) filter.get("minPrice")));
			}
			if (filter.get("maxPrice") != null) {
				pst.setBigDecimal(parameterIndex++, new BigDecimal((Double) filter.get("maxPrice")));
			}
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				totalBooks = rs.getInt("total_books");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return totalBooks;
	}
	
	public Integer getQuantityOfBook(int bookId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer quantityOfBooks = 0;
		try {
			String sql = "SELECT quantity FROM books WHERE book_id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, bookId);;
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				quantityOfBooks = rs.getInt("quantity");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return quantityOfBooks;
	}

	public ArrayList<Book> getAllBooks(Integer limit, Integer offset,String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		ArrayList<Book> books = new ArrayList<>();
		try {
			String sql = "SELECT b.*, p.name AS publisher, a.name AS author, g.name AS genre, SUM(oi.quantity) AS total_quantity_sold "
		            + "FROM books b "
		            + "JOIN genres g ON b.genre_id = g.genre_id "
		            + "JOIN publishers p ON b.publisher_id = p.publisher_id "
		            + "JOIN authors a ON b.author_id = a.author_id "
		            + "LEFT JOIN order_items oi ON b.book_id = oi.book_id "
		            + "WHERE b.title LIKE ? "
		            + "GROUP BY b.book_id, p.name, a.name, g.name "
		            + "ORDER BY book_id ASC LIMIT ?,?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%"+search+"%");
			stmt.setInt(2, offset);
			stmt.setInt(3, limit);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("book_id"));
				book.setTitle(rs.getString("title"));
				book.setAuthorId(rs.getInt("author_id"));
				book.setPrice(rs.getBigDecimal("price"));
				book.setQuantity(rs.getInt("quantity"));
				book.setPublicationDate(rs.getString("publication_date"));
				book.setISBN(rs.getString("ISBN"));
				book.setRating(rs.getInt("rating"));
				book.setDescription(rs.getString("description"));
				book.setPublisherId(rs.getInt("publisher_id"));
				book.setGenreId(rs.getInt("genre_id"));
				book.setImage(rs.getString("image_url"));
				book.setPublisherName(rs.getString("publisher"));
				book.setAuthorName(rs.getString("author"));
				book.setGenreName(rs.getString("genre"));
				book.setTotalQuantitySold(rs.getInt("total_quantity_sold"));
				books.add(book);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return books;
	}

	public int addBook(String title, int authorId, BigDecimal price, int quantity, String publicationDate, String ISBN,
			int rating, String description, int publisherId, int genreId, Part imageFile) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int generatedBookId = -1;
		try {
			String sql = "INSERT INTO books (title, author_id, price, quantity, publication_date, ISBN, rating, description, publisher_id, genre_id, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, title);
			stmt.setInt(2, authorId);
			stmt.setBigDecimal(3, price);
			stmt.setInt(4, quantity);
			stmt.setString(5, publicationDate);
			stmt.setString(6, ISBN);
			stmt.setInt(7, rating);
			stmt.setString(8, description);
			stmt.setInt(9, publisherId);
			stmt.setInt(10, genreId);

			// Upload the image to Cloudinary and get the image_url using SDK in
			// cloudinaryConnection
			Cloudinary cloudinary = CloudinaryConnection.getCloudinary();
			String imageUrl = CloudinaryConnection.uploadImageToCloudinary(cloudinary, imageFile);
			stmt.setString(11, imageUrl);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {

				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedBookId = generatedKeys.getInt(1);
				}
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return generatedBookId;
	}

	public boolean editBook(int bookId, String title, int authorId, BigDecimal price, int quantity,
			String publicationDate, String ISBN, int rating, String description, int publisherId, int genreId,
			Part imageFile, String prev_url) throws SQLException, IOException {
		Connection conn = DBConnection.getConnection();
		Boolean updated = false;
		try {
			String sql = "UPDATE books SET title = ?, author_id = ?, price = ?, publisher_id = ?, genre_id = ?, ISBN = ?, rating = ?, description = ?, publication_date = ?,quantity = ?,image_url = ? WHERE book_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setInt(2, authorId);
			stmt.setBigDecimal(3, price);
			stmt.setInt(4, publisherId);
			stmt.setInt(5, genreId);
			stmt.setString(6, ISBN);
			stmt.setInt(7, rating);
			stmt.setString(8, description);
			stmt.setString(9, publicationDate);
			stmt.setInt(10, quantity);
			stmt.setInt(12, bookId);

			// Upload the image to Cloudinary and get the image_url using SDK in
			// cloudinaryConnection
			if (imageFile != null) {
				Cloudinary cloudinary = CloudinaryConnection.getCloudinary();
				System.out.println(prev_url.equals(""));
				if (prev_url != null && !prev_url.equals("")) {
					CloudinaryConnection.deleteFromCloudinary(cloudinary, prev_url);
				}
				String imageUrl = CloudinaryConnection.uploadImageToCloudinary(cloudinary, imageFile);
				stmt.setString(11, imageUrl);
			} else {
				stmt.setString(11, prev_url);
			}

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {

				updated = true;
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return updated;
	}

	public Boolean deleteBook(int bookId,String prev_url) throws SQLException{
		Boolean deleted = false;
		Connection conn = DBConnection.getConnection();
		try {
			Cloudinary cloudinary = CloudinaryConnection.getCloudinary();
			if (prev_url != null && !prev_url.equals("")) {
				CloudinaryConnection.deleteFromCloudinary(cloudinary, prev_url);
			}
			String sql = "DELETE FROM books WHERE book_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookId);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				deleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return deleted;
	}
	
	public Integer getAllTotalBooks(String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer numberOfBooks = 0;
		try {
			String sql = "SELECT COUNT(*) AS number_of_books FROM books WHERE title LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%"+search+"%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				numberOfBooks = rs.getInt("number_of_books");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		System.out.println(numberOfBooks);
		return numberOfBooks;
	}
}
