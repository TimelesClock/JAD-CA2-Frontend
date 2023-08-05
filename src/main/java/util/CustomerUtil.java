package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.UserDAO;

public class CustomerUtil {
	public static Boolean checkCustomer(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session == null || session.getAttribute("userid") == null) {
				return false;
			}

			UserDAO db = new UserDAO();
			String userId = (String) session.getAttribute("userid");
			String role = db.getRole(userId);
			if (role == null || !role.equals("customer")) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
