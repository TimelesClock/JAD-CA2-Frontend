<%@ page import="java.util.*"%>
<%@ page import="classes.Book"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	Sanity Check #011

	<%@page import="java.sql.Timestamp"%>
	<%@page import="java.sql.Blob"%>
	<%
	try {
		Blob image = (Blob) request.getAttribute("image");
		String title = (String) request.getAttribute("title");
		String author = (String) request.getAttribute("author");
		Double price = (Double) request.getAttribute("price");
		Integer quantity = (Integer) request.getAttribute("quantity");
		String publisher = (String) request.getAttribute("publisher");
		Timestamp publicationDate = (Timestamp) request.getAttribute("publicationDate");
		String ISBN = (String) request.getAttribute("ISBN");
		String genre = (String) request.getAttribute("genre");
		Integer rating = (Integer) request.getAttribute("rating");
		String description = (String) request.getAttribute("description");
		Integer bookId = (Integer) request.getAttribute("bookId");
	%>
	<div class="max-w-7xl mx-auto px-4">
		<div class=" flex flex-col w-full lg:flex-row">
			<div class="grid flex-shrink-0 place-items-center my-20 lg:pr-6">
				<figure>
					<img src="data:image/jpeg;base64,<%=image%>" alt="Book img">
				</figure>
			</div>
			<div class="divider lg:divider-horizontal"></div>
			<div class="grid flex-shrink my-20">
				<div class="flex flex-col space-y-5 lg:pl-4">
					<h2 class="text-2xl text-black font-bold">
						<%=title%>
					</h2>
					<%=genre%>
					<div class="badge badge-secondary text-xs">
						<%=genre%>
					</div>
					<p class="text-lg">
						<span class="text-black">By</span>
						<%=author%>
					</p>
					<h3 class="text-black text-xl font-semibold">
						<%=price%>
					</h3>
					<p>
						<span class="text-black">Quantity:</span>
						<%=quantity%>
					</p>
					<button type="button" class="btn btn-primary w-[200px]">
						Add to Cart</button>
					<div class="tabs pt-12">
						<button type="button" class="tab tab-lg tab-lifted tab-active">
							Description</button>
							<div class="tab-content">
								<p class="text-black"><%=description%></p>
							</div>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400">Product
							Details</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400">Vendor
							Info</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400">Reviews
						</button>
					</div>
					
					<div class="tab-content">
						<p class="text-black">Tab 2 content</p>
					</div>
					<div class="tab-content">
						<p class="text-black">Tab 3 content</p>
					</div>
					<div class="tab-content">
						<p class="text-black">Tab 4 content</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
	} catch (Exception e) {
	String err = e.getMessage();
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
	<%@include file="footer.html"%>
</body>
</html>