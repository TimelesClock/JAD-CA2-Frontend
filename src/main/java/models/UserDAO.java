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
	
	public ArrayList<User> getUsers() throws SQLException{
		Connection conn = DBConnection.getConnection();
		ArrayList<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM Users WHERE role = 'customer'";
			Statement userStmt = conn.createStatement();
	        ResultSet userRs = userStmt.executeQuery(sql);
	        while (userRs.next()) {
	            User user = new User();
	            user.setUserId(userRs.getInt("user_id"));
	            user.setName(userRs.getString("name"));
	            user.setEmail(userRs.getString("email"));
	            user.setRole(userRs.getString("role"));
	            user.setPhone(userRs.getString("phone"));
	            users.add(user);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return users;
	}

	public User getUserById(int userId) throws SQLException{
		Connection conn = DBConnection.getConnection();
		User user = new User();
		try {
			String sql = "SELECT * FROM Users WHERE user_id = ?";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setInt(1, userId);
	        ResultSet userRs = userStmt.executeQuery();
	        while (userRs.next()) {
	            user.setUserId(userRs.getInt("user_id"));
	            user.setName(userRs.getString("name"));
	            user.setEmail(userRs.getString("email"));
	            user.setRole(userRs.getString("role"));
	            user.setPhone(userRs.getString("phone"));
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return user;
	}

	public Integer editUserById(int userId, HashMap<String,String> userInfo) throws SQLException{
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET name = ?, email = ?, phone = ? WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, userInfo.get("name"));
			userStmt.setString(2, userInfo.get("email"));
			userStmt.setString(3, userInfo.get("phone"));
			userStmt.setInt(4, userId);
	        rowsAffected = userStmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return rowsAffected;
	}

	public Integer changeUserPasswordById(int userId, HashMap<String,String> userInfo) throws SQLException{
		Connection conn = DBConnection.getConnection();
		int rowsAffected = 0;
		try {
			String sql = "UPDATE users SET password = MD5(?) WHERE user_id = ?;";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, userInfo.get("password"));
			userStmt.setInt(2, userId);
	        rowsAffected = userStmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return rowsAffected;
	}
	
	public String login(String email, String password) throws SQLException{
		Connection conn = DBConnection.getConnection();
		String userid = null;
		try {
			String sql = "SELECT * FROM Users WHERE email = ? AND password = MD5(?)";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, email);
			userStmt.setString(2, password);
	        ResultSet rs = userStmt.executeQuery();
	        if (rs.next()) {
	        	userid = String.valueOf(rs.getInt("user_id"));
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return userid;
	}

	public String register(String name, String email,String password,String phone) throws SQLException{
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
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return userid;
	}

	public Integer userCountByEmail(String email) throws SQLException{
		Connection conn = DBConnection.getConnection();
		Integer count = 0;
		try {
			String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
			PreparedStatement userStmt = conn.prepareStatement(sql);
			userStmt.setString(1, email);
	        ResultSet rs = userStmt.executeQuery();
	        if (rs.next()) {
	        	count = rs.getInt(1);
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return count;
	}
}
