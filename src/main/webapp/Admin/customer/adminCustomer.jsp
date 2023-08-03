<%@ page import="models.User"%>
<%@ page import="java.util.List"%>
<%
@SuppressWarnings("unchecked")
List<User> users = (List<User>) request.getAttribute("users");
%>
<%!int currentPage;
	String imageUrl;
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
			<%@include file="../adminSidePanel.jsp"%>
			<div class="w-10/12 grid">
				<h1 class="text-2xl font-bold mb-4 ms-5">View Customer</h1>
				<button class="btn" onclick="customer_modal.showModal()">Add
					New Customer</button>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/customer?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/customer?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
				<div class="container mx-auto mt-5">
					<table class="table table-zebra">
						<thead>
							<tr>
								<th class="text-center">Customer ID</th>
								<th class="text-center">Customer Name</th>
								<th class="text-center">Email</th>
								<th class="text-center">Phone</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (User user : users) {
							%>
							<tr>
								<td class="">

									<div class="font-bold"><%=user.getUserId()%></div>
								</td>
								<td class="text-center"><%=user.getName()%></td>
								<td class="text-center"><%=user.getEmail()%></td>
								<td class="text-center"><%=user.getPhone()%></td>
								<td class="text-center">
									<button class="btn"
										onclick="handleShowModal(<%=user.getUserId()%>)">Show</button>
									<button class="btn"
										onclick="handleEditModal(<%=user.getUserId()%>)">Edit</button>
									<button class="btn"
										onclick="handleDeleteModal(<%=user.getUserId()%>)">Delete</button>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%@include file="adminAddCustomerModal.jspf"%>

				<%@include file="adminEditCustomerModal.jspf"%>

				<%@include file="adminShowCustomerModal.jspf"%>

				<%@include file="adminDeleteCustomerModal.jspf"%>

				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/customer?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/customer?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
	function handleDeleteModal(bookId){
		const deleteBookId = document.getElementById("delete_user_id");
		<%for (User user : users) {%>
		if ("<%=user.getUserId()%>" === userId.toString()) {
			deleteuserId.value = <%=user.getUserId()%>
			deleteModal.show();
			return;
		}
<%}%>
		
	}
	function handleShowModal(userId){

		<%for (User user : users) {%>
			if ("<%=user.getUserId()%>" === userId.toString()) {
				
				showDetailsModal.showModal();
				return;
			}
	<%}%>
	}
	function handleEditModal(userId){

		<%for (User user : users) {%>
		if ("<%=user.getUserId()%>" === userId.toString()) {

			editDetailsModal.showModal();
			return;
		}
<%}%>
	}

	</script>
</body>