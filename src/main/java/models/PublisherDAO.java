package models;



import dbaccess.*;
import java.sql.*;
import java.util.ArrayList;

public class PublisherDAO{
    public ArrayList<Publisher> getPublishers() throws Exception {
        Connection conn = DBConnection.getConnection();
        ArrayList<Publisher> publishers = new ArrayList<>();
        try {

            String sql = "SELECT * FROM publishers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                publishers.add(new Publisher(rs.getInt("publisher_id"), rs.getString("name")));
            }
            return publishers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return publishers;
    }
    
    public static int addPublisher(String publisherName) {
		int newPublisherId = -1;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "INSERT INTO publishers (name) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, publisherName);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					newPublisherId = rs.getInt(1);
				}
				rs.close();
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newPublisherId;
	}
}