package models;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import dbaccess.*;

public class OrderDAO {

	public Order getOrder(int orderId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String sql = "SELECT * FROM orders WHERE order_id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, orderId);

		ResultSet rs = pstmt.executeQuery();
		Order order = null;
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

		conn.close();
		return order;
	}

	public void insertOrder(Order order) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String sql = "INSERT INTO orders(order_date, subtotal) VALUES(?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
		pstmt.setFloat(2, order.getSubtotal());

		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			int orderId = rs.getInt(1);
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

		conn.close();
	}

	public List<Order> getAllOrders() throws SQLException {
		List<Order> orders = new ArrayList<>();

		Connection conn = DBConnection.getConnection();
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

		conn.close();
		return orders;
	}

	public List<Order> getOrders(Integer limit, Integer offset, String status) throws SQLException {
		List<Order> orders = new ArrayList<>();
		Integer index = 1;
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt;
		String sql;
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

		conn.close();
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
}
