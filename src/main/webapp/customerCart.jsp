<%@ page import="java.util.*"%>
<%@ page import="classes.Cart"%>
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
	Sanity Check #025
	<%=session.getAttribute("userId")%>
	<%@page import="java.sql.Date"%>

	<%@include file="header.jsp"%>

	<div class=" bg-base-100 pt-20">
		<h1 class="mb-10 text-center text-2xl font-bold">Shopping Cart</h1>
		<div
			class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
			<div class="rounded-lg md:w-2/3">
				<%
				try {
					@SuppressWarnings("unchecked")
					int totalRecords = (int) request.getAttribute("totalRecords");
					double subtotal = (double) request.getAttribute("subtotal");
					List<Cart> cartItems = (List<Cart>) request.getAttribute("cart");
					if (cartItems != null) {
						for (Cart item : cartItems) {
					System.out.println(item.getTitle());
				%>
				<div
					class="justify-between mb-6 rounded-lg bg-base-200 p-6 shadow-md sm:flex sm:justify-start">
					<%
					if (item.getImage() != null && !item.getImage().equals("")) {
					%>
					<figure class="w-full rounded-lg sm:w-40">
						<img src="data:image/jpeg;base64,<%=item.getImage()%>"
							alt="Book img" class="w-full h-full">
					</figure>
					<%
					}
					%>
					<div class="sm:ml-4 sm:flex sm:w-full sm:justify-between">
						<div class="mt-5 sm:mt-0">
							<h2 class="text-lg font-bold"><%=item.getTitle()%></h2>
							<p class="mt-1 text-xs">
								By
								<%=item.getAuthor()%></p>
						</div>
						<div
							class="mt-4 flex justify-between sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
							<div class="flex items-center border-base-200">
								<span
									class="cursor-pointer rounded-l bg-base-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50"
									id="decrease"> - </span> <input
									class="h-8 w-8 border border-base-100 bg-base-100 text-center text-xs outline-none [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
									type="number" value="<%=item.getQuantity()%>" min="1" /> <span
									class="cursor-pointer rounded-r bg-base-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50"
									id="increase"> + </span>
								<script>
									document
											.getElementById('decrease')
											.addEventListener(
													'click',
													function() {
														var quantityInput = document
																.getElementById("quantity");
														console.log("input",
																quantityInput);
														console
																.log(
																		"value",
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
																.getElementById("quantity");
														console.log("input",
																quantityInput);
														console
																.log(
																		"value",
																		quantityInput.value);
														if (quantityInput.value != null)
															quantityValue = parseInt(quantityInput.value);
														else
															quantityValue = 1;

														if (quantityValue < <%=item.getMax()%>) {
															quantityValue++;
															quantityInput.value = quantityValue;
														}
													});
								</script>
							</div>
							<div class="flex items-center space-x-4">
								<p class="text-sm">
									$<%=item.getPrice()%></p>
								<form action="CustomerPanelServlet" method="post">
									<input type="hidden" name="function" value="deleteFromCart">
									<button type="submit" name="cartId" value="<%=item.getCartId()%>">
										<svg xmlns="http://www.w3.org/2000/svg" fill="none"
											viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
											class="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
		                  					<path stroke-linecap="round" stroke-linejoin="round"
												d="M6 18L18 6M6 6l12 12" />
						                </svg>
						           	</button>
				                </form>
							</div>
						</div>
					</div>
				</div>
				<%
				}
				}
				%>
			</div>
			<!-- Sub total -->
			<div
				class="mt-6 h-full rounded-lg border bg-base-100 p-6 shadow-xl md:mt-0 md:w-1/3">
				<h1 class="mb-3 text-xl text-start font-bold">Summary</h1>
				<div class="mb-2 flex justify-between">
					<p class="">Total Items</p>
					<p class=""><%=totalRecords%></p>
				</div>
				<div class="mb-2 flex justify-between">
					<p class="">Subtotal</p>
					<p class="">
						$<%=subtotal%></p>
				</div>
				<hr class="my-4" />
				<div class="flex justify-between">
					<p class="text-lg font-bold">Total</p>
					<div class="">
						<p class="mb-1 text-lg font-bold">
							$<%=subtotal%>
						</p>
					</div>
				</div>
				<button
					class="disabled mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Check
					out</button>
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