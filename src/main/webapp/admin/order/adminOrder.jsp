<%@ page import="models.Book"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Author"%>
<%@ page import="models.Publisher"%>
<%@ page import="models.Genre"%>
<%@ page import="models.Order"%>
<%@ page import="models.OrderItem"%>
<%
@SuppressWarnings("unchecked")
ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
%>
<%!int currentPage;
	int totalPages;%>
<%
try {
	String pageNum = request.getParameter("page");
	String totalPageRaw = request.getAttribute("totalPages") != null
	? request.getAttribute("totalPages").toString()
	: "";
	currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
	totalPages = totalPageRaw != null ? Integer.parseInt(totalPageRaw) : totalPages;
} catch (Exception e) {
	totalPages = 1;
	currentPage = 1;
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="/admin/adminSidePanel.jsp"%>
			<div class="w-10/12 grid">
				<h1 class="text-2xl font-bold mb-4 ms-5">View Orders</h1>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
				<div class="container mx-auto mt-5">
					<table class="table table-zebra">
						<thead>
							<tr>
								<th class="text-center">Order ID</th>
								<th class="text-center">Date</th>
								<th class="text-center">Status</th>
								<th class="text-center">Subtotal</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (orders != null) {
								for (Order order : orders) {
							%>
							<tr>
								<td class="text-center"><%=order.getOrderId()%></td>
								<td class="text-center"><%=order.getOrderDate()%></td>
								<td class="text-center"><%=order.getStatus()%></td>
								<td class="text-center"><%=order.getSubtotal()%></td>
								<td class="text-center">
									<button class="btn"
										onclick="handleShowModal(<%=order.getOrderId()%>)">Show</button>
									<button class="btn"
										onclick="handleEditModal(<%=order.getOrderId()%>)">Edit
										Status</button>
								</td>
							</tr>
							<%
							}
							}
							%>
						</tbody>
					</table>
				</div>
				<%@include file="adminShowOrderModal.jspf"%>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
	function handleShowModal(orderId) {
		var show_order_id = document.getElementById('show_order_id');
		var show_order_date = document.getElementById('show_order_date');
		var show_status = document.getElementById('show_status');
		var show_subtotal = document.getElementById('show_subtotal');
		var book_table = document.getElementById('book_table');
		<%for (Order order : orders) {%>
			if ("<%=order.getOrderId()%>" === orderId.toString()) {
				show_order_id.innerHTML = "<%=order.getOrderId()%>";
				show_order_date.innerHTML = "<%=order.getOrderDate()%>";
				show_status.innerHTML = "<%=order.getStatus()%>";
				show_subtotal.innerHTML = "<%=order.getSubtotal()%>";
				
				var orderItems = [
	                <% 
	                ArrayList<OrderItem> items = order.getOrderItems();
	                for (int i = 0; i < items.size(); i++) {
	                    OrderItem item = items.get(i);
	                %>
	                {
	                    orderId: "<%=item.getOrderId()%>",
	                    bookId: "<%=item.getBookId()%>",
	                    quantity: "<%=item.getQuantity()%>",
	                    bookName: "<%=item.getBookName()%>",
	                    price:"<%=item.getPrice()%>"
	                }<%= i < items.size() - 1 ? "," : "" %>
	                <% } %>
	            ];
				var tableRows = "";
				for(var i = 0; i < orderItems.length; i++){
					tableRows += '<tr>';
					tableRows += '<td class="text-center">' + orderItems[i].bookName + '</td>';
					tableRows += '<td class="text-center">' + orderItems[i].price + '</td>';
					tableRows += '<td class="text-center">' + orderItems[i].quantity + '</td>';
					tableRows += '</tr>';
				}
				book_table.innerHTML = tableRows;
				show_order_modal.showModal();
				return;
			}
		<%}%>
	}
	</script>
</body>