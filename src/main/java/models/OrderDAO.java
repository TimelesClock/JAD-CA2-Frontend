package models;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import dbaccess.*;
import java.util.Map;
import java.util.HashMap;

public class OrderDAO {

	public Order getOrder(int orderId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Order order = null;
		try {
			String sql = "SELECT * FROM orders WHERE order_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, orderId);
	
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setSubtotal(rs.getFloat("subtotal"));
	
				sql = "SELECT * FROM Order_items WHERE order_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, orderId);
	
				ResultSet rsItems = pstmt.executeQuery();
				while (rsItems.next()) {
					OrderItem item = new OrderItem();
					item.setOrderId(rsItems.getInt("order_id"));
					item.setBookId(rsItems.getInt("book_id"));
					item.setQuantity(rsItems.getInt("quantity"));
	
					order.addOrderItem(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return order;
	}

	public HashMap<String,Object> getOrderHistory(int userid, int limit, int offset) throws SQLException {
		Connection conn = DBConnection.getConnection();
		HashMap<String,Object> hashmap = new HashMap<>();
		ArrayList<Order> orders = new ArrayList<>();
		ArrayList<Cart> cartItems = new ArrayList<>();
		try {
			String sql = "SELECT o.order_id, o.order_date, o.status, o.subtotal, oi.quantity, oi.book_id, b.image_url, b.title, a.name AS author, b.price FROM orders o\r\n"
					+ "INNER JOIN order_items oi\r\n"
					+ "ON o.order_id = oi.order_id\r\n"
					+ "INNER JOIN books b\r\n"
					+ "ON oi.book_id = b.book_id\r\n"
					+ "INNER JOIN authors a \r\n"
					+ "ON b.author_id = a.author_id\r\n"
					+ "WHERE o.customer_id = ?\r\n"
					+ "LIMIT ?, ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, limit);
	
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setStatus(rs.getString("status"));
				order.setSubtotal(rs.getDouble("subtotal"));
				orders.add(order);
				
				Cart item = new Cart();
				item.setBookId(rs.getInt("book_id"));
				item.setTitle(rs.getString("title"));
				item.setAuthor(rs.getString("author"));
				item.setPrice(rs.getBigDecimal("price"));
				item.setQuantity(rs.getInt("quantity"));
				item.setImage(rs.getString("image_url"));
				cartItems.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		hashmap.put("orders", orders);
		hashmap.put("cartItems", cartItems);
		return hashmap;
	}
	
	public Integer getTotalOrderHistory(int userid) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Integer totalRecords = 0;
        try{
            String sql = "SELECT COUNT(*) AS total_records FROM orders o\r\n"
            		+ "INNER JOIN order_items oi\r\n"
            		+ "ON o.order_id = oi.order_id\r\n"
            		+ "INNER JOIN books b\r\n"
            		+ "ON oi.book_id = b.book_id\r\n"
            		+ "INNER JOIN authors a \r\n"
            		+ "ON b.author_id = a.author_id\r\n"
            		+ "WHERE o.customer_id = ?;";
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
	
	public Integer insertOrder(Order order) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int orderId = -1;
		try {
			String sql = "INSERT INTO orders(order_date, subtotal) VALUES(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
			pstmt.setDouble(2, order.getSubtotal());
	
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
				order.setOrderId(orderId);
	
				for (OrderItem item : order.getOrderItems()) {
					sql = "INSERT INTO order_items(order_id, book_id, quantity) VALUES(?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, orderId);
					pstmt.setInt(2, item.getBookId());
					pstmt.setInt(3, item.getQuantity());
	
					pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return orderId;
	}

	public List<Order> getAllOrders() throws SQLException {
		Connection conn = DBConnection.getConnection();
		List<Order> orders = new ArrayList<>();
		try {
			String sql = "SELECT * FROM orders";
			Statement stmt = conn.createStatement();
	
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setSubtotal(rs.getFloat("subtotal"));
	
				int orderId = order.getOrderId();
				sql = "SELECT * FROM order_items oi INNER JOIN books b ON oi.book_id = b.book_id WHERE order_id = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, orderId);
	
				ResultSet rsItems = pstmt.executeQuery();
				while (rsItems.next()) {
					OrderItem item = new OrderItem();
					item.setOrderId(rsItems.getInt("order_id"));
					item.setBookId(rsItems.getInt("book_id"));
					item.setQuantity(rsItems.getInt("quantity"));
					item.setBookName(rsItems.getString("title"));
					order.addOrderItem(item);
				}
	
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return orders;
	}

	public List<Order> getOrders(Integer limit, Integer offset, String status) throws SQLException {
		List<Order> orders = new ArrayList<>();
		Integer index = 1;
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt;
		String sql;
		try {
			if (!status.equals("All")) {
				 sql = "SELECT * FROM orders WHERE status = ? ORDER BY order_date DESC LIMIT ?,? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(index++, status);
			} else {
				 sql = "SELECT * FROM orders ORDER BY order_date DESC LIMIT ?,? ";
				pstmt = conn.prepareStatement(sql);
			}

			pstmt.setInt(index++, offset);
			pstmt.setInt(index, limit);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				order.setOrderId(rs.getInt("order_id"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setSubtotal(rs.getFloat("subtotal"));
				order.setStatus(rs.getString("status"));
				order.setCustomerId(rs.getInt("customer_id"));
	
				int orderId = order.getOrderId();
				sql = "SELECT * FROM order_items oi INNER JOIN books b ON oi.book_id = b.book_id WHERE order_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, orderId);
	
				ResultSet rsItems = pstmt.executeQuery();
				while (rsItems.next()) {
					OrderItem item = new OrderItem();
					item.setOrderId(rsItems.getInt("order_id"));
					item.setBookId(rsItems.getInt("book_id"));
					item.setQuantity(rsItems.getInt("quantity"));
					item.setBookName(rsItems.getString("title"));
					item.setPrice(rsItems.getDouble("price"));
					order.addOrderItem(item);
				}
	
				orders.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return orders;
	}

	public Integer getTotalOrders(String status) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer number_of_orders = 0;
		try {
			String sql;
			PreparedStatement stmt;
			if (!status.equals("All")) {
				sql = "SELECT COUNT(*) AS number_of_orders FROM orders WHERE status = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, status);
			}else {
				sql = "SELECT COUNT(*) AS number_of_orders FROM orders";
				stmt = conn.prepareStatement(sql);
			}
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				number_of_orders = rs.getInt("number_of_orders");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return number_of_orders;
	}

	public Integer updateStatus(String status, String orderId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE orders SET status = ? WHERE order_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, status);
			stmt.setString(2, orderId);
			rowsAffected = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}
	
	public List<Map<String, Object>> getTopCustomers() {
		Connection conn = DBConnection.getConnection();
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			String sql = "SELECT u.name AS customer_name, u.user_id AS customer_id, ROUND(SUM(o.subtotal), 2) AS total_spent " +
			             "FROM users u " +
			             "JOIN orders o ON u.user_id = o.customer_id " +
			             "GROUP BY u.name, u.user_id " +
			             "ORDER BY total_spent DESC " +
			             "LIMIT 10";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("customer_name", rs.getString("customer_name"));
				row.put("customer_id", rs.getInt("customer_id"));
				row.put("total_spent", rs.getDouble("total_spent"));
				result.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
