package servlets;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import javax.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.*;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/home");
		}catch(Exception e) {
			response.sendRedirect(request.getContextPath() + "/home");
		}
		
	}
}
