package models;

import dbaccess.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
