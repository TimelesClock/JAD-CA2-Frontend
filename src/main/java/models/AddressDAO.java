package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbaccess.*;

public class AddressDAO {
	public ArrayList<Address> getAddresses() throws SQLException {
        Connection conn = DBConnection.getConnection();
        ArrayList<Address> addresses = new ArrayList<>();
        try {
            String sql = "SELECT * FROM address";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet addressRs = pstmt.executeQuery();
            while (addressRs.next()) {
                Address address = new Address();
                address.setAddressId(addressRs.getInt("address_id"));
                address.setAddress(addressRs.getString("address"));
                address.setAddress2(addressRs.getString("address2"));
                address.setDistrict(addressRs.getString("district"));
                address.setCountry(addressRs.getString("country"));
                address.setCity(addressRs.getString("city"));
                address.setPostalCode(addressRs.getString("postal_code"));
                address.setPhone(addressRs.getString("phone"));
                addresses.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return addresses;
    }
	
	public String addAddress(String address, String address2, String district, String country, String city, String postal_code, String phone) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String addressID = null;

		try {
			String sql = "INSERT INTO address (address, address2, district, country, city, postal_code, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, address);
			pstmt.setString(2, address2);
			pstmt.setString(3, district);
			pstmt.setString(4, country);
			pstmt.setString(5, city);
			pstmt.setString(6, postal_code);
			pstmt.setString(7, phone);

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						addressID = Integer.toString(generatedKeys.getInt(1));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return addressID;
	}
}
