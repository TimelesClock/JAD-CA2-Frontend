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



@WebServlet("/Login")
public class AuthenticateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public AuthenticateServlet() {
    	super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	if (session != null && session.getAttribute("role")!=null) {
    		response.sendRedirect("home");
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
        	request.getRequestDispatcher("").forward(request,response);
        }
        else if (action.equals("login")) {
            login(request, response, email, password);
        } else if (action.equals("register")) {
            register(request, response, email, password, name, phone);
        }
    }

    @SuppressWarnings("unchecked")
	private void login(HttpServletRequest request, HttpServletResponse response, String email, String password)
            throws ServletException, IOException {
        try {
        	AppUtil app = new AppUtil();
        	JSONObject json = new JSONObject();
        	json.put("email", email);
        	json.put("password", password);
        	Response res = app.post("login",json);
        	
        	if (res.getStatus() != Response.Status.OK.getStatusCode()) {
    			request.setAttribute("err", res.getStatus()+" POST request error");
    		}

    		JsonObject data = JsonUtil.getData(res);
    		
    		if (data == null) {
    			request.setAttribute("err","Error in POST request");
    			request.getRequestDispatcher("login.jsp").forward(request, response);
    			return;
    		}
    		
    		if (data.getString("status").equals("success")) {
    			HttpSession session = request.getSession();
    			session.setAttribute("token", data.getString("token"));
    			response.sendRedirect("home");
    		}else {
    			request.setAttribute("err",data.getString("message"));
    			request.getRequestDispatcher("login.jsp").forward(request, response);
    		}

            

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err",e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @SuppressWarnings("unchecked")
	private void register(HttpServletRequest request, HttpServletResponse response, String email, String password,String name,String phone)
            throws ServletException, IOException {
        try {
        	AppUtil app = new AppUtil();
        	JSONObject json = new JSONObject();
        	json.put("email", email);
        	json.put("password", password);
        	json.put("name", name);
        	json.put("phone", phone);
        	Response res = app.post("register",json);
        	
        	if (res.getStatus() != Response.Status.OK.getStatusCode()) {
    			request.setAttribute("err", res.getStatus()+" POST request error");
    		}
        	
        	JsonObject data = JsonUtil.getData(res);
        	
        	if (data == null) {
    			request.setAttribute("err","Error in POST request");
    			request.getRequestDispatcher("login.jsp").forward(request, response);
    			return;
    		}
        	
        	if (data.getString("status") == "fail") {
        		request.setAttribute("err", data.getString("message"));
        		request.getRequestDispatcher("login.jsp").forward(request, response);
        		return;
        	}

            HttpSession session = request.getSession();
            session.setAttribute("token", data.getString("token"));
            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", "An error occurred");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

}
