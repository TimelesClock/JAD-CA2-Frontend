package servlets;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerPanelServlet")
public class CustomerPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomerPanelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("customer")) {
			String context = request.getParameter("function");
			int userId = (int) session.getAttribute("userId");
			if (context == null) {
				request.getRequestDispatcher("customerHeader.jsp").forward(request, response);
			} else if (context.equals("myProfile")) {
				getProfile(request, response, userId);
			}
		} else {
			response.sendRedirect("AuthenticateServlet");
		}		
	}

	private void getProfile(HttpServletRequest request, HttpServletResponse response, int userId)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
            String query = "SELECT email, phone FROM users WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
            	request.setAttribute("email", rs.getString("email"));
            	request.setAttribute("phone", rs.getString("phone"));
            } else {
            	response.sendRedirect("AuthenticateServlet");
            }
            
            con.close();
		} catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", e.getMessage());
        }
		request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("customer")) {
			String context = request.getParameter("function");
			int userId = (int) session.getAttribute("userId");
			if (context == null) {
				request.getRequestDispatcher("customerHeader.jsp").forward(request, response);
			} else if (context.equals("editProfile")) {
				editProfile(request, response, session, userId);
			}
		} else {
			response.sendRedirect("BookServlet");
		}
	}
	
	private void editProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session, int userId)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");
            String query = "UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?;";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setInt(4, userId);
            int rowsAffected = pst.executeUpdate();
	        if (rowsAffected > 0) {
	        	request.setAttribute("success", "Profile Updated Successfully");
	        	session.setAttribute("username", username);
	        	getProfile(request, response, userId);
	        } else {
	        	request.setAttribute("err", "Something Went Wrong");
	        }
	        
	        pst.close();
            con.close();
		} catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", e.getMessage());
        }
	}

}
