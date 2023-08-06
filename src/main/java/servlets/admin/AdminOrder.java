package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.AdminUtil;

/**
 * Servlet implementation class AdminOrder
 */
@WebServlet("/admin/order")
public class AdminOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String page_raw = request.getParameter("page");
			Integer page;
			try {
				page = Integer.parseInt(page_raw);
			}catch(Exception e) {
				page = 1;
			}
			String status = request.getParameter("status");
			if(status == null || status.equals("All")) {
				status = "All";
			}
			
			AdminUtil.addOrderContext(request,page,status);
			AdminUtil.addOrderCountContext(request,status);
			request.getRequestDispatcher("/admin/order/adminOrder.jsp").forward(request, response);;
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
