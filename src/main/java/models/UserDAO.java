package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dbaccess.*;

public class UserDAO {

	public ArrayList<User> getUsers(Integer limit, Integer offset,String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		ArrayList<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM users WHERE role = 'customer' AND name LIKE ? LIMIT ?,?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search+"%");
			pstmt.setInt(2, offset);
			pstmt.setInt(3, limit);
			ResultSet userRs = pstmt.executeQuery();
			while (userRs.next()) {
				User user = new User();
				user.setUserId(userRs.getInt("user_id"));
				user.setName(userRs.getString("name"));
				user.setEmail(userRs.getString("email"));
				user.setRole(userRs.getString("role"));
				user.setPhone(userRs.getString("phone"));
				user.setAddressId(Integer.toString(userRs.getInt("address_id")));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return users;
	}
	
	public ArrayList<User> getResellers(Integer limit, Integer offset,String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		ArrayList<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM users WHERE role = 'reseller' AND name LIKE ? LIMIT ?,?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+search+"%");
			pstmt.setInt(2, offset);
			pstmt.setInt(3, limit);
			ResultSet userRs = pstmt.executeQuery();
			while (userRs.next()) {
				User user = new User();
				user.setUserId(userRs.getInt("user_id"));
				user.setName(userRs.getString("name"));
				user.setEmail(userRs.getString("email"));
				user.setRole(userRs.getString("role"));
				user.setPhone(userRs.getString("phone"));
				user.setAddressId(Integer.toString(userRs.getInt("address_id")));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return users;
	}
	
	public Integer getTotalUsers(String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer numberOfUsers = 0;
		try {
			String sql = "SELECT COUNT(*) AS number_of_users FROM users WHERE role = 'customer' AND name LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%"+search+"%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				numberOfUsers = rs.getInt("number_of_users");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return numberOfUsers;
	}
	
	public Integer getTotalResellers(String search) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer numberOfUsers = 0;
		try {
			String sql = "SELECT COUNT(*) AS number_of_users FROM users WHERE role = 'reseller' AND name LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,"%"+search+"%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				numberOfUsers = rs.getInt("number_of_users");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return numberOfUsers;
	}

	public User getUserById(int userid) throws SQLException {
		Connection conn = DBConnection.getConnection();
		User user = new User();
		try {
			String sql = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setInt(1, userid);
			ResultSet userRs = userStmt.executeQuery();
			while (userRs.next()) {
				user.setUserId(userRs.getInt("user_id"));
				user.setName(userRs.getString("name"));
				user.setEmail(userRs.getString("email"));
				user.setRole(userRs.getString("role"));
				user.setPhone(userRs.getString("phone"));
				user.setAddressId(Integer.toString(userRs.getInt("address_id")));
				user.setStripeCustomerId(userRs.getString("stripe_customer_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return user;
	}

	public Integer editUserById(int userid, String name, String email, String phone, String addressId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET name = ?, email = ?, phone = ?, address_id = ? WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, name);
			userStmt.setString(2, email);
			userStmt.setString(3, phone);
			userStmt.setString(4, addressId);
			userStmt.setInt(5, userid);
			rowsAffected = userStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}
	
	public Integer editUserProfileById(int userid, String name, String email, String phone) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, name);
			userStmt.setString(2, email);
			userStmt.setString(3, phone);
			userStmt.setInt(4, userid);
			rowsAffected = userStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}

	public Integer changeUserPasswordById(int userid, String password) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET password = MD5(?) WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, password);
			userStmt.setInt(2, userid);
			rowsAffected = userStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}
	
	public Integer changeUserAddressById(int userid, String addressId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET address_id = ? WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, addressId);
			userStmt.setInt(2, userid);
			rowsAffected = userStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}
	
	public Integer changeUserStripeCustomerIdById(int userid, String stripeCustomerId) throws SQLException {
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET stripe_customer_id = ? WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, stripeCustomerId);
			userStmt.setInt(2, userid);
			rowsAffected = userStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}

	public String login(String email, String password) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String userid = null;
		try {
			String sql = "SELECT * FROM users WHERE email = ? AND password = MD5(?)";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, email);
			userStmt.setString(2, password);
			ResultSet rs = userStmt.executeQuery();
			if (rs.next()) {
				userid = String.valueOf(rs.getInt("user_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return userid;
	}

	public String register(String name, String email, String password, String phone) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String userid = null;
		try {
			String sql = "INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, MD5(?), ?, 'customer')";
			PreparedStatement userStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			userStmt.setString(1, name);
			userStmt.setString(2, email);
			userStmt.setString(3, password);
			userStmt.setString(4, phone);
			userStmt.executeUpdate();
			ResultSet rs = userStmt.getGeneratedKeys();
			if (rs.next()) {
				userid = String.valueOf(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return userid;
	}

	public Integer userCountByEmail(String email) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Integer count = 0;
		try {
			String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, email);
			ResultSet rs = userStmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return count;
	}

	public String getRole(String userid) throws SQLException{
		Connection conn = DBConnection.getConnection();
		String role = null;
		try {
			String sql = "SELECT role FROM users WHERE user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				role = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return role;
	}
	
	public String getToken(String userid) throws SQLException{
		Connection conn = DBConnection.getConnection();
		String token = null;
		try {
			String sql = "SELECT token FROM users WHERE user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				token = rs.getString("token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return token;
	}
	
	public boolean deleteUser(String userid) throws SQLException {
		Connection conn = DBConnection.getConnection();
		boolean isDeleted = false;
		try {
			String sql = "DELETE FROM users WHERE user_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				isDeleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return isDeleted;
	}
	public String addUser(String name, String email, String password, String phone,String addressId,String role) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String userid = null;
		try {
			String sql;
			if (role.equals("reseller")) {
				sql = "INSERT INTO users (name, email, password, phone, role,address_id,token) VALUES (?, ?, MD5(?), ?, ?,?,MD5(?))";
			}else {
				sql = "INSERT INTO users (name, email, password, phone, role,address_id) VALUES (?, ?, MD5(?), ?, ?,?)";
			}
			
			PreparedStatement userStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			userStmt.setString(1, name);
			userStmt.setString(2, email);
			userStmt.setString(3, password);
			userStmt.setString(4, phone);
			userStmt.setString(5,role);
			userStmt.setString(6,addressId);
			if (role.equals("reseller")) {
				userStmt.setString(7,email+password);
			}
			userStmt.executeUpdate();
			ResultSet rs = userStmt.getGeneratedKeys();
			if (rs.next()) {
				userid = String.valueOf(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return userid;
	}
	

}