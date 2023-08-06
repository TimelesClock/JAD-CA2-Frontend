<%@ page import="java.util.*"%>
<%@ page import="models.Order"%>
<%@ page import="models.Cart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
	rel="stylesheet">
</head>
<body>
	<%@page import="java.sql.Date"%>

	<%@include file="../header.jsp"%>
	<%!int currentPage = 1;
	int totalPages = 1;
	String search;
	%>
	<%
	String err = (String) request.getAttribute("err");
	if (err != null) {
		int maxLength = 50;
		if (err.length() > maxLength) {
			err = err.substring(0, maxLength) + "...";
		}
	%>
	<!-- Error Message -->
	<div class="toast toast-top toast-center justify-center">
		<div class="alert alert-error">
			<span><%=err%></span>
		</div>
	</div>
	<%
	}
	%>
	<div class=" bg-base-100 pt-20">
		<h1 class="mb-10 text-center text-4xl font-bold">Order History</h1>
		<div
			class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
			<div class="rounded-lg md:w-2/3">
				<%
				try {
					@SuppressWarnings("unchecked")

					String success = (String) request.getAttribute("success");
					int totalRecords = (int) request.getAttribute("totalRecords");
					List<Order> orders = (List<Order>) request.getAttribute("orders");
					List<Cart> cartItems = (List<Cart>) request.getAttribute("cart");
					if (cartItems != null) {
						for (int i = 0; i < cartItems.size(); i++) {
				%>
				<div
					class="justify-between mb-6 rounded-lg bg-base-200 p-6 shadow-md sm:flex sm:justify-start">
					<%
					if (cartItems.get(i).getImage() != null && !cartItems.get(i).getImage().equals("")) {
					%>
					<figure class="w-full rounded-lg sm:w-40">
						<img src="<%=cartItems.get(i).getImage()%>" alt="Book img"
							class="w-full h-full">
					</figure>
					<%
					}
					%>
					<div class="sm:ml-4 sm:flex sm:w-full sm:justify-between">
						<div class="mt-5 sm:mt-0">
							<h2 class="text-lg font-bold"><%=cartItems.get(i).getTitle()%></h2>
							<p class="mt-1 text-xs">
								By
								<%=cartItems.get(i).getAuthor()%>
							</p>
							<p class="mt-1 text-xs">
								Status: 
								<%=orders.get(i).getStatus()%>
							</p>
						</div>
						<div
							class="mt-4 flex justify-between sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
							<div class="flex items-end justify-end space-x-4">
								<p class="text-sm">
									Unit Price: $<%=cartItems.get(i).getPrice()%>
								</p>
							</div>
							<div class="flex items-end justify-end border-base-200">
								<p class="text-sm">
									Purchased <%=cartItems.get(i).getQuantity()%> on <%=orders.get(i).getOrderDate()%>
								</p>
							</div>
							
						</div>
					</div>
				</div>
				
				<%
				}
				}
				%>
			</div>
		</div>
	</div>

	<%
	try {
		String pageNum = request.getParameter("page");
		String totalPageRaw = request.getAttribute("totalPages").toString();
		int currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
		int totalPages = totalPageRaw != null ? Integer.parseInt(totalPageRaw) : 1;
	%>
	<div class="flex justify-center mt-10 mb-20">
		<div class="join">
			<a
				href="<%=request.getContextPath() + "/customer/order"%>?p=myOrder&page=<%=currentPage - 1%>"
				class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">
				« </a> <a href="#" class="join-item btn">Page <%=currentPage%></a> <a
				href="<%=request.getContextPath() + "/customer/order"%>?p=myOrder&page=<%=currentPage + 1%>"
				class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
		</div>
	</div>
	<%
	} catch (NumberFormatException e) {
		err = e.getMessage();
	}
	%>

	<%
	} catch (Exception e) {
		err = e.getMessage();
	%>
	<!-- Error Message -->
	<div class="toast toast-top toast-center justify-center">
		<div class="alert alert-error">
			<span><%=err%></span>
		</div>
	</div>
	<%
	}
	%>

	<%@include file="../footer.html"%>
</body>
</html>