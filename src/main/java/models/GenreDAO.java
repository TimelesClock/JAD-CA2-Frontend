package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dbaccess.*;

public class GenreDAO {
	public ArrayList<Genre> getGenres() throws SQLException {
		ArrayList<Genre> genres = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "SELECT * FROM genres";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Genre genre = new Genre();
				genre.setId(rs.getInt("genre_id"));
				genre.setName(rs.getString("name"));

				genres.add(genre);
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return genres;
	}
}
