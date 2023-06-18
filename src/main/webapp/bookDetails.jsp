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
	Sanity Check #020

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
				<figure>
					<img src="data:image/jpeg;base64,<%=image%>" alt="Book img">
				</figure>
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
						<div class="flex flex-row">
							<span class="">Quantity </span>
							<div
								class="form-control mt-1 flex flex-row max-w-min border rounded-xl">
								<button class="p-2" id="decrease">-</button>
								<input type="number"
									class="input text-center w-20 input-bordered [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
									name="quantity" value="1" min="1">
								<button class="p-2" id="increase">+</button>
							</div>
						</div>
						<p class="mb-5">
							<%=quantity%>
							available
						</p>
						<div class="form-control">
							<button type="submit" class="btn btn-primary w-[200px]">
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
						<%=rating%>
						/ 5
						<div class="rating">
							<input type="radio" name="rating-2"
								class="mask mask-star-2 bg-orange-400" /> <input type="radio"
								name="rating-2" class="mask mask-star-2 bg-orange-400" /> <input
								type="radio" name="rating-2"
								class="mask mask-star-2 bg-orange-400" /> <input type="radio"
								name="rating-2" class="mask mask-star-2 bg-orange-400" /> <input
								type="radio" name="rating-2"
								class="mask mask-star-2 bg-orange-400" />
						</div>
					</div>

					<script>
						document
								.getElementById('decrease')
								.addEventListener(
										'click',
										function() {
											var quantityInput = document
													.querySelector('.input[name="quantity"]');
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
											var quantityInput = document
													.querySelector('.input[name="quantity"]');
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
						document
								.addEventListener(
										'DOMContentLoaded',
										function() {
											var ratingElements = document
													.querySelectorAll('.rating');
											var element = ratingElements[
					<%=rating%>
						- 1];
											element.classList.add('checked');
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