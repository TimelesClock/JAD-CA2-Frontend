package models;

import dbaccess.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AuthorDAO {
    public ArrayList<Author> getAuthors() throws Exception {
        Connection conn = DBConnection.getConnection();
        ArrayList<Author> authors = new ArrayList<>();
        try {

            String sql = "SELECT * FROM authors";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(rs.getInt("author_id"), rs.getString("name")));
            }
            return authors;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return authors;
    }
    
    public static int insertNewAuthor(String authorName) {
		int newAuthorId = -1;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "INSERT INTO authors (name) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, authorName);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					newAuthorId = rs.getInt(1);
				}
				rs.close();
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newAuthorId;
	}
}
