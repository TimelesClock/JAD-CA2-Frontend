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
}