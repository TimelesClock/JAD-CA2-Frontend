package servlets.authenticate;

import java.io.IOException;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;

import models.*;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("login");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String name = request.getParameter("name");
        	UserDAO db = new UserDAO();
        	String userid = db.register(name, email, password, phone);
        	
        	if (userid == null) {
        		request.setAttribute("err","Error in registration!");
        		request.getRequestDispatcher("login.jsp").forward(request, response);
        	}else {
        		 HttpSession session = request.getSession();
                 session.setAttribute("userid", userid);
                 response.sendRedirect("home");
        	}

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", "An error occurred");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
	}

}
