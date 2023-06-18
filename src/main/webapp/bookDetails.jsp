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
	Sanity Check #023

	<%@page import="java.sql.Date"%>

	<%@include file="header.jsp"%>
	<%
	try {
		String image = (String) request.getAttribute("image");
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
		Integer userId = (Integer) session.getAttribute("userId");
	%>
	<div class="max-w-7xl mx-auto px-4">
		<div class=" flex flex-col w-full lg:flex-row">
			<div class="grid flex-shrink-0 place-items-center my-20 lg:pr-6">
				<%
				if (image != null && !image.equals("")) {
				%>
				<figure class="w-full h-80">
					<img src="data:image/jpeg;base64,<%=image%>" alt="Book img"
						class="w-full h-full">
				</figure>
				<%
				} else {
				%>
				<figure class="w-full h-80">
					<span class=""><%=title%></span>
				</figure>
				<%
				}
				%>
			</div>
			<div class="divider lg:divider-horizontal"></div>
			<div class="grid flex-shrink my-20">
				<div class="flex flex-col lg:pl-4">
					<h2 class="text-2xl font-bold">
						<%=title%>
					</h2>
					<div class="badge badge-accent mt-2 text-s p-2">
						<%=genre%>
					</div>
					<p class="mt-5 text-lg">
						<span class="">By</span>
						<%=author%>
					</p>
					<h3 class="my-5 text-xl font-semibold">
						$<%=price%>
					</h3>
					<form action="CustomerPanelServlet" method="post">
						<input type="hidden" name="function" value="addToCart"> <input
							type="hidden" name="bookId" value="<%=bookId%>">
						<div class="flex flex-row items-center mb-5">
							<span class="me-2">Quantity </span>
							<div
								class="form-control mt-1 flex flex-row max-w-min border rounded-xl">
								<span
									class="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50" id="decrease">
									- </span> 
									<input
									class="input h-8 w-20 border bg-white text-center text-xs outline-none [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
									type="number" name="quantity" id="quantity" value="1" min="1" /> <span
									class="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50" id="increase">
									+ </span>
							</div>
							<p class="ms-4">
								<%=quantity%>
								available
							</p>
						</div>

						<div class="form-control">
							<button type="submit" class="btn btn-primary w-full">
								Add to Cart</button>
						</div>
					</form>
					<div class="tabs pt-6 pb-3">
						<button type="button" class="tab tab-lg tab-lifted tab-active"
							id="descriptionTab">Description</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400"
							id="productTab">Product Details</button>
						<button type="button" class="tab tab-lg tab-lifted text-gray-400"
							id="reviewsTab">Reviews</button>
					</div>

					<div class="tab-content" id="descriptionContent">
						<p class=""><%=description%></p>
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
						Rating:
						<div class="rating flex flex-row">
							<%
							for (int i = 1; i <= 5; i++) {
								if (i <= rating) {
							%>
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
								stroke-width="0" class="w-8 h-8 fill-amber-400">
  								<path stroke-linecap="round" stroke-linejoin="round"
									d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
							</svg>
							<%
							} else {
							%>
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
								stroke-width="1" class="w-8 h-8 stroke-slate-800 fill-none">
  								<path stroke-linecap="round" stroke-linejoin="round"
									d="M11.48 3.499a.562.562 0 011.04 0l2.125 5.111a.563.563 0 00.475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 00-.182.557l1.285 5.385a.562.562 0 01-.84.61l-4.725-2.885a.563.563 0 00-.586 0L6.982 20.54a.562.562 0 01-.84-.61l1.285-5.386a.562.562 0 00-.182-.557l-4.204-3.602a.563.563 0 01.321-.988l5.518-.442a.563.563 0 00.475-.345L11.48 3.5z" />
							</svg>
							<%
							}
							}
							%>
						</div>
					</div>

					<script>
						document
								.getElementById('decrease')
								.addEventListener(
										'click',
										function() {
											var quantityInput = document.getElementById("quantity");
											console.log("input", quantityInput);
											console.log("value",
													quantityInput.value);
											if (quantityInput.value != null)
												quantityValue = parseInt(quantityInput.value);
											else
												quantityValue = 1;

											if (quantityValue > 1) {
												quantityValue--;
												quantityInput.value = quantityValue;
											}
										});
						document
								.getElementById('increase')
								.addEventListener(
										'click',
										function() {
											var quantityInput = document.getElementById("quantity");
											console.log("input", quantityInput);
											console.log("value",
													quantityInput.value);
											if (quantityInput.value != null)
												quantityValue = parseInt(quantityInput.value);
											else
												quantityValue = 1;

											if (quantityValue <
					<%=quantity%>
						) {
												quantityValue++;
												quantityInput.value = quantityValue;
											}
										});
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