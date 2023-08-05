package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import dbaccess.*;

public class CartDAO {
    public ArrayList<Cart> getCart(int userid, Integer limit, Integer offset) throws SQLException {
		Connection conn = DBConnection.getConnection();
        ArrayList<Cart> cartItems = new ArrayList<Cart>();
        try {
            String sql = "SELECT c.cart_id, b.image, b.book_id, b.title, a.name AS author, b.price, b.quantity AS max, c.quantity\r\n"
            + "FROM cart c\r\n"
            + "INNER JOIN books b ON c.book_id = b.book_id\r\n"
            + "INNER JOIN authors a ON b.author_id = a.author_id\r\n"
            + "WHERE user_id = ?\r\n"
            + "LIMIT ?, ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, offset);
            pst.setInt(3, limit);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Cart item = new Cart();
                item.setCartId(rs.getInt("cart_id"));
				item.setBookId(rs.getInt("book_id"));
				item.setTitle(rs.getString("title"));
				item.setAuthor(rs.getString("author"));
				item.setPrice(rs.getBigDecimal("price"));
				item.setMax(rs.getInt("max"));
				item.setQuantity(rs.getInt("quantity"));
				item.setImage(rs.getString("image"));

				cartItems.add(item);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
        return cartItems;
    }

    public Integer getTotalCartItems(int userid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Integer totalRecords = 0;
        try{
            String sql = "SELECT COUNT(*) AS total_records\r\n"
            + "FROM cart c\r\n"
            + "INNER JOIN books b ON c.book_id = b.book_id\r\n"
            + "INNER JOIN authors a ON b.author_id = a.author_id\r\n"
            + "WHERE user_id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                totalRecords = rs.getInt(1);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return totalRecords;
    }

    public Double getSubtotal(int userid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Double subtotal = 0.00;
        try{
            String sql = "SELECT CAST(SUM(b.price*c.quantity) AS DECIMAL(7, 2)) AS subtotal\r\n"
            + "FROM cart c\r\n"
            + "INNER JOIN books b ON c.book_id = b.book_id\r\n"
            + "INNER JOIN authors a ON b.author_id = a.author_id\r\n"
            + "WHERE user_id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                subtotal = rs.getDouble(1);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return subtotal;
    }

    public Integer addToCart(int userid, int bookId, int quantity) throws SQLException {
		Connection conn = DBConnection.getConnection();
        int rowsAffected = 0;
        try {
            String sql = "INSERT INTO cart (user_id, book_id, quantity)\r\n" 
            + "VALUES (?, ?, ?)\r\n"
            + "ON DUPLICATE KEY UPDATE\r\n" 
            + "quantity = quantity + ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);
            pst.setInt(2, bookId);
            pst.setInt(3, quantity);
            pst.setInt(4, quantity);
            rowsAffected = pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return rowsAffected;
    }

    public Integer deleteFromCart(int cartId) throws SQLException {
		Connection conn = DBConnection.getConnection();
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM cart WHERE cart_id = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, cartId);
            rowsAffected = pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return rowsAffected;
    }
}
