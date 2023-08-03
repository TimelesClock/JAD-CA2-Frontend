package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Book;
import models.BookDAO;
import models.Genre;
import models.GenreDAO;


@WebServlet(urlPatterns = {"/home", ""})
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        HashMap<String,Object> filter = new HashMap<>();
        List<String> filterHeader = Arrays.asList("search", "genreId", "minRating", "maxRating", "minPrice", "maxPrice");
        List<String> filterData = new ArrayList<>();
        for (int i = 0; i < filterHeader.size(); i++) {
        	filterData.add(request.getParameter(filterHeader.get(i)));
        	if (i > 3 && filterData.get(i) != null) filter.put(filterHeader.get(i), Double.parseDouble(filterData.get(i)));
        	else if (i > 0 && filterData.get(i) != null) filter.put(filterHeader.get(i), Integer.parseInt(filterData.get(i)));
        	else filter.put(filterHeader.get(i), filterData.get(i));
        }
        //List<String> filterList = new ArrayList<>();
        //filterList.add(request.getParameter("books"));
        //filterList.add(request.getParameter("genreId"));
        //filterList.add(request.getParameter("minRating"));
        //filterList.add(request.getParameter("maxRating"));
        //filterList.add(request.getParameter("minPrice"));
        //filterList.add(request.getParameter("maxPrice"));
        //String search = request.getParameter("books");
        //String genreId = request.getParameter("genreId");
        //String minRating = request.getParameter("minRating");
        //String maxRating = request.getParameter("maxRating");
        //String minPrice = request.getParameter("minPrice");
        //String maxPrice = request.getParameter("maxPrice");
        
        //filter.put("search", search);
        //filter.put("genreId", genreId);
        //try {
        //	filter.put("minRating", Integer.parseInt(minRating));
        //	filter.put("minRating", Integer.parseInt(maxRating));
        //} catch (NumberFormatException e) {
        //	e.printStackTrace();
        //    System.out.println("Filter rating error when parsing");
        //    request.setAttribute("err", "Rating: "+e.getMessage());
        //    return;
        //}
        //try {
        //	filter.put("minRating", Double.parseDouble(minPrice));
        //	filter.put("minRating", Double.parseDouble(maxPrice));
        //} catch (NumberFormatException e) {
        //	e.printStackTrace();
        //    System.out.println("Filter price error when parsing");
        //    request.setAttribute("err", "Price: "+e.getMessage());
        //    return;
        //}
        // Get page and limit from request, or set default values
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 15;
        int offset = (page - 1) * limit;
        int totalRecords = 0;
        int totalPages = 0;
        try {
            BookDAO db1 = new BookDAO();
            books = db1.getBooks(filter, limit, offset);
        	
        	if (books == null) {
    			request.setAttribute("err", "No books found.");
    		}

        	totalRecords = db1.getTotalBooks();
        	
    		if (totalRecords == 0) {
    			request.setAttribute("err", "Zero books found.");
    		}

            totalPages = (int) Math.ceil((double) totalRecords / limit);
    		
    		GenreDAO db2 = new GenreDAO();
    		genres = db2.getGenres();
    		
    		if (genres == null) {
    			request.setAttribute("err", "No genres found.");
    		}
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
        }
        request.setAttribute("books", books);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


}
