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
	int totalPages;
	String status;%>
<%
try {
	status = request.getParameter("status");
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
				<div class="justify-center my-5">
					<label class="label"> <span class="label-text">Sort
							by status</span>
					</label> <select name="status" class="select select-bordered"
						onChange="handleSort(this)" id="edit_status" required>
						<option value = "All" selected disabled>Select an option </option>
						<option value="All">All</option>
						<option value="Pending">Pending</option>
						<option value="Shipped">Shipped</option>
						<option value="Completed">Completed</option>
						<option value="Cancelled">Cancelled</option>
					</select>
				</div>
				<div class="flex justify-center my-5">

					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage - 1)%><%=status != null ? ("&status=" + status) : ""%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage + 1)%><%=status != null ? ("&status=" + status) : ""%>"
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
				<%@include file="adminEditOrderModal.jspf"%>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage - 1)%><%=status != null ? ("&status=" + status) : ""%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/order?page=" + (currentPage + 1)%><%=status != null ? ("&status=" + status) : ""%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
	
	function handleSort(selectElement) {
		  const selectedValue = selectElement.value;
		  const currentUrl = window.location.href;
		  const url = new URL(currentUrl);
		  url.searchParams.set('status', selectedValue);
		  window.location.href = url.toString();
		}
	function handleEditModal(orderId){
		var edit_status = document.getElementById('edit_status');
		var edit_order_id = document.getElementById('edit_order_id')
		<%for (Order order : orders) {%>
			if ("<%=order.getOrderId()%>" === orderId.toString()){
				edit_status.value = "<%=order.getStatus()%>";
				edit_order_id.value = "<%=order.getOrderId()%>";
				editOrderModal.showModal()
			}
		<%}%>
	}
	
	function handleShowModal(orderId) {
		var show_customer_id = document.getElementById('show_customer_id');
		var show_order_date = document.getElementById('show_order_date');
		var show_status = document.getElementById('show_status');
		var show_subtotal = document.getElementById('show_subtotal');
		var book_table = document.getElementById('book_table');
		<%for (Order order : orders) {%>
			if ("<%=order.getOrderId()%>" === orderId.toString()) {
				show_customer_id.innerHTML = "<%=order.getCustomerId()%>";
				show_order_date.innerHTML = "<%=order.getOrderDate()%>";
				show_status.innerHTML = "<%=order.getStatus()%>";
				show_subtotal.innerHTML = "<%=order.getSubtotal()%>";
				
				var orderItems = [
	                <%ArrayList<OrderItem> items = order.getOrderItems();
for (int i = 0; i < items.size(); i++) {
	OrderItem item = items.get(i);%>
	                {
	                    orderId: "<%=item.getOrderId()%>",
	                    bookId: "<%=item.getBookId()%>",
	                    quantity: "<%=item.getQuantity()%>",
	                    bookName: "<%=item.getBookName()%>",
	                    price:"<%=item.getPrice()%>"
	                }<%=i < items.size() - 1 ? "," : ""%>
	                <%}%>
	            ];
				var tableRows = "";
				for(var i = 0; i < orderItems.length; i++){
					tableRows += '<tr>';
					tableRows += '<td class="text-center">' + orderItems[i].bookName + '</td>';
					tableRows += '<td class="text-center">' + orderItems[i].price + '</td>';
					tableRows += '<td class="text-center">' + orderItems[i].quantity + '</td>';
					tableRows += '<td class="text-center">' + (orderItems[i].quantity*orderItems[i].price) + '</td>';
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