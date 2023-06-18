package servlets;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/AuthenticateServlet")
public class AuthenticateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public AuthenticateServlet() {
    	super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session != null && session.getAttribute("role")!=null) {
    		response.sendRedirect("BookServlet");
    	} else {
    		request.getRequestDispatcher("/login.jsp").forward(request, response);
    	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if (action.equals("logout")){
        	session.invalidate();
        	response.sendRedirect("BookServlet");
        }
        else if (action.equals("login")) {
            login(request, response, email, password);
        } else if (action.equals("register")) {
            register(request, response, email, password, name, phone);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response, String email, String password)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");

            String query = "SELECT * FROM Users WHERE email = ? AND password = MD5(?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                int timeout = 24 * 60 * 60; // 24 hours
                session.setMaxInactiveInterval(timeout);
                session.setAttribute("userId", rs.getInt("user_id"));
                session.setAttribute("username", rs.getString("name"));
                session.setAttribute("role", rs.getString("role"));
            } else {
                response.sendRedirect("AuthenticateServlet?err=Invalid%20email%20or%20password");
                return;
            }
            response.sendRedirect("BookServlet");
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("AuthenticateServlet?err=Something%20went%20wrong");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response, String email, String password,String name,String phone)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jad", "root", "root");

            // Check if the email already exists
            String checkQuery = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet checkRs = checkStmt.executeQuery();

            if (checkRs.next()) {
                request.setAttribute("err", "Email already exists");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                conn.close();
                return;
            }

            // Validate pw length
            if (password.length() < 8) {
                request.setAttribute("er", "Password should be at least 8 characters long");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                conn.close();
                return;
            }

            // Insert new user into the db
            String insertQuery = "INSERT INTO Users (name,email, password,role,phone) VALUES (?,?,MD5(?),?,?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);
            insertStmt.setString(4, "customer");
            insertStmt.setString(5, phone);
            insertStmt.executeUpdate();

            HttpSession session = request.getSession();
            int timeout = 24 * 60 * 60; // 24 hours
            session.setMaxInactiveInterval(timeout);
            session.setAttribute("role", "customer");
            response.sendRedirect("BookServlet");

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("err", "An error occurred");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
