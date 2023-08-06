<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.User"%>
<%@ page import="models.Address"%>
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
	<%@include file="../header.jsp"%>

	<%
	// customer profile
	try {
		Address address = (Address) request.getAttribute("address");
	%>
	<div class="flex flex-col items-center w-full p-5 rounded-xl space-y-6">
		<form action="<%=request.getContextPath() + "/customer/address"%>" method="post" class="card w-full"
			id="profileForm">
			<h1 class="text-2xl font-bold">My Address</h1>
			<input type="hidden" name="p" value="editProfile">
			<div class="form-control">
				<label class="label"> <span class="label-text">Address</span>
				</label> <input type="text" required name="add_address" class="input input-bordered"
					value=<%=address != null ? address.getAddress() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Block</span>
				</label> <input type="text" required name="add_address2" class="input input-bordered"
					value=<%=address != null ? address.getAddress2() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">District</span>
				</label> <input type="text" required name="add_district" class="input input-bordered"
					value=<%=address != null ? address.getDistrict() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Country</span>
				</label> <input type="text" required name="add_country" class="input input-bordered"
					value=<%=address != null ? address.getCountry() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">City</span>
				</label> <input type="text" required name="add_city" class="input input-bordered"
					value=<%=address != null ? address.getCity() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Postal Code</span>
				</label> <input type="text" required name="add_postal_code" class="input input-bordered"
					value=<%=address != null ? address.getPostalCode() : ""%>>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Phone</span>
				</label> <input type="text" required name="add_phone" class="input input-bordered"
					value=<%=address != null ? address.getPhone() : ""%>>
			</div>
			<button type="submit" class="btn btn-primary mt-5">Save
				Changes</button>
		</form>
		<%
		try {
			String success = (String) request.getParameter("success");
			if (success != null) {
				
		%>
		<p class=""><%=success%></p>
		<%
			}
		} catch (Exception e) {
			String err = (String) request.getParameter("err");
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

	<%@include file="../footer.html"%>
</body>
</html>