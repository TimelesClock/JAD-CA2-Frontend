package servlets;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import org.jose4j.json.internal.json_simple.JSONObject;

import classes.Book;
import classes.Genre;
import util.AppUtil;


@WebServlet(urlPatterns = {"/home", ""})
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        String search = request.getParameter("books");
        // Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 15;
        int offset = (page - 1) * limit;
        int totalRecords;
        AppUtil app = new AppUtil();
        try {
        	String url = "books/getBooks?limit="+limit+"&offset="+offset;
        	if (search != null) {
        		url += "&search="+search;
        	}
        	Response res = app.get(url);
        	
        	if (res.getStatus() != Response.Status.OK.getStatusCode()) {
    			request.setAttribute("err", "GET Request Error");
    		}

    		books = (ArrayList<Book>) res.readEntity(new GenericType<ArrayList<Book>>() {});
    		
    		res = app.get("books/getTotalBooks");
    		if (res.getStatus() != Response.Status.OK.getStatusCode()) {
    			request.setAttribute("err", "GET Request Error");
    		}
    		String result = (String) res.readEntity(String.class);
    		JsonReader jsonReader = Json.createReader(new StringReader(result));
    		JsonObject reply = jsonReader.readObject();
    		totalRecords = reply.getInt("totalBooks");
    		
    		res = app.get("genre/getGenres");
    		if (res.getStatus() != Response.Status.OK.getStatusCode()) {
    			request.setAttribute("err", "GET Request Error");
    		}
    		genres = (ArrayList<Genre>) res.readEntity(new GenericType<ArrayList<Genre>>() {});
			
            int totalPages = (int) Math.ceil((double) totalRecords / limit);
            request.setAttribute("totalPages", totalPages);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
        }
        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


}
