package models;
import dbaccess.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BookDAO {
	// public ArrayList<Book> getBooks(String search, String genreId, Integer minRating, Integer maxRating, Double minPrice, Double maxPrice, Integer limit, Integer offset) throws SQLException{
    public ArrayList<Book> getBooks(HashMap<String,Object> filter, Integer limit, Integer offset) throws SQLException{
		Connection conn =  DBConnection.getConnection();
		ArrayList<Book> books = new ArrayList<Book>();
        System.out.println("filter: "+filter);
		try {
			String sql = "SELECT * FROM books b WHERE 1=1";
			if (filter.get("search") != null && !((String) filter.get("search")).isEmpty()) {
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
			while(rs.next()) {
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
                book.setImage(rs.getString("image"));

                books.add(book);
            }
            rs.close();
            pst.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return books;
	}
	
	public HashMap<String, Object> getBook(Integer bookId) throws SQLException{
		Connection conn = DBConnection.getConnection();
		HashMap<String, Object> book = new HashMap<String, Object>();
		try {
			String sql = "SELECT b.image, b.title, a.name AS author, b.price, b.quantity, p.name AS publisher, b.publication_date, b.ISBN, g.name AS genre, b.rating, b.description \r\n"
            		+ "FROM books b\r\n"
            		+ "LEFT JOIN authors a ON a.author_id = b.author_id\r\n"
            		+ "LEFT JOIN publishers p ON p.publisher_id = b.publisher_id\r\n"
            		+ "LEFT JOIN genres g ON g.genre_id = b.genre_id\r\n"
            		+ "WHERE book_id = ?;";
			PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                book.put("image", rs.getString("image"));
                book.put("title", rs.getString("title"));
                book.put("author", rs.getString("author"));
                book.put("price", rs.getDouble("price"));
                book.put("quantity", rs.getInt("quantity"));
                book.put("publisher", rs.getString("publisher"));
                book.put("publication_date", rs.getDate("publication_date"));
                book.put("ISBN", rs.getString("ISBN"));
                book.put("genre", rs.getString("genre"));
                book.put("rating", rs.getDouble("rating"));
                book.put("description", rs.getString("description"));
            }
            rs.close();
            pst.close();
            
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return book;
	}

    public Integer getTotalBooks() throws SQLException{
        Connection conn = DBConnection.getConnection();
        Integer numberOfBooks = 0;
        try{
            String sql = "SELECT COUNT(*) AS number_of_books FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                numberOfBooks = rs.getInt("number_of_books");
            }
            rs.close();
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return numberOfBooks;
    }

    public ArrayList<Book> getAllBooks(Integer limit, Integer offset) throws SQLException{
        Connection conn = DBConnection.getConnection();
        ArrayList<Book> books = new ArrayList<>();
        try{
            String sql = "SELECT * FROM books LIMIT ?,?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, offset);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
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
                book.setImage(rs.getString("image"));

                books.add(book);
            }
            rs.close();
            stmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return books;
    }
	
}
