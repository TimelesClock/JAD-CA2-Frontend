package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.AdminUtil;

/**
 * Servlet implementation class AdminBook
 */
@WebServlet("/admin/customer")
public class AdminCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCustomer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println(request.getContextPath());
			String page_raw = request.getParameter("page");
			Integer page;
			try {
				page = Integer.parseInt(page_raw);
			}catch(Exception e) {
				page = 1;
			}
			AdminUtil.checkAdmin(request, response);
			AdminUtil.addUserContext(request,page);
			AdminUtil.addUserCountContext(request);
			request.getRequestDispatcher("/admin/customer/adminCustomer.jsp").forward(request, response);;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
