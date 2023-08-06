package servlets.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;
/**
 * Servlet implementation class AdminEditOrder
 */
@WebServlet("/admin/order/edit")
public class AdminEditOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath() + "/admin/customer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("status");
		String orderId = request.getParameter("edit_order_id");
		try {
			OrderDAO db = new OrderDAO();
			Integer rowsAffected = db.updateStatus(status,orderId);
			if(rowsAffected == 0) {
				response.sendRedirect(request.getContextPath() + "/admin/order?err=Status%20Not%20Changed");
				return;
			}else{
				response.sendRedirect(request.getContextPath() + "/admin/order?success=Status%20Changed%20Successfully");
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/order?err=Something%20Went%20Wrong");
			return;
		}
	}

}
