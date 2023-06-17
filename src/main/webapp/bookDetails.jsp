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
	Sanity Check #014

	<%@page import="java.sql.Date"%>
	<%@page import="java.sql.Blob"%>
	
	<%@include file="header.jsp"%>
	<%
	try {
		Blob image = (Blob) request.getAttribute("image");
		String title = (String) request.getAttribute("title");
		String author = (String) request.getAttribute("author");
		Double price = (Double) request.getAttribute("price");
		Integer quantity = (Integer) request.getAttribute("quantity");
		String publisher = (String) request.getAttribute("publisher");
		Date publicationDate = (Date) request.getAttribute("publicationDate");
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
					<div class="badge badge-accent text-xs">
						<%=genre%>
					</div>
					<p class="text-lg">
						<span class="text-black">By</span>
						<%=author%>
					</p>
					<h3 class="text-black text-xl font-semibold">
						$<%=price%>
					</h3>
					<p>
						<span class="text-black">Quantity:</span>
						<%=quantity%>
					</p>
					<button type="button" class="btn btn-primary w-[200px]">
						Add to Cart</button>
					<div class="tabs pt-12">
						<button type="button" class="tab tab-lg tab-lifted tab-active"
							id="descriptionTab">Description</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400"
							id="productTab">Product Details</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400"
							id="reviewsTab">Reviews</button>
					</div>

					<div class="tab-content" id="descriptionContent">
						<p class="text-black"><%=description%></p>
					</div>
					<div class="tab-content hidden" id="productContent">
						<table class="table">
							<tbody>
								<tr>
									<th>ISBN</th>
									<td><%=ISBN%></td>
								</tr>
								<tr>
									<th>Publisher</th>
									<td><%=publisher%></td>
								</tr>
								<tr>
									<th>Publication Date</th>
									<td><%=publicationDate%></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="tab-content hidden" id="reviewsContent">
						Rating: <%=rating%> / 5
					</div>

					<script>
						document
								.getElementById('descriptionTab')
								.addEventListener(
										'click',
										function() {
											document
													.getElementById('descriptionContent').classList
													.remove('hidden');
											document
													.getElementById('productContent').classList
													.add('hidden');
											document
													.getElementById('reviewsContent').classList
													.add('hidden');
											document
													.getElementById('descriptionTab').classList
													.add('tab-active');
											document
													.getElementById('productTab').classList
													.remove('tab-active');
											document
													.getElementById('reviewsTab').classList
													.remove('tab-active');
										});

						document
								.getElementById('productTab')
								.addEventListener(
										'click',
										function() {
											document
													.getElementById('productContent').classList
													.remove('hidden');
											document
													.getElementById('descriptionContent').classList
													.add('hidden');
											document
													.getElementById('reviewsContent').classList
													.add('hidden');
											document
													.getElementById('productTab').classList
													.add('tab-active');
											document
													.getElementById('descriptionTab').classList
													.remove('tab-active');
											document
													.getElementById('reviewsTab').classList
													.remove('tab-active');
										});
						document
								.getElementById('reviewsTab')
								.addEventListener(
										'click',
										function() {
											document
													.getElementById('reviewsContent').classList
													.remove('hidden');
											document
													.getElementById('descriptionContent').classList
													.add('hidden');
											document
													.getElementById('productContent').classList
													.add('hidden');
											document
													.getElementById('reviewsTab').classList
													.add('tab-active');
											document
													.getElementById('descriptionTab').classList
													.remove('tab-active');
											document
													.getElementById('productTab').classList
													.remove('tab-active');
										});
					</script>
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