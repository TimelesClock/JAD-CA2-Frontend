<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.User"%>
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
	try {
		User user = (User) request.getAttribute("user");
		
	%>
	<div class="flex flex-col items-center w-full p-5 rounded-xl space-y-6">
		<form action="CustomerPanelServlet" method="post" class="card w-full"
			id="profileForm">
			<h1 class="text-2xl font-bold">My Profile</h1>
			<input type="hidden" name="p" value="editProfile">
			<div class="form-control">
				<label class="label"> <span class="label-text">Username</span>
				</label> <input type="text" name="username" class="input input-bordered"
					value=<%=user.getName()%> required>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Email</span>
				</label> <input type="text" name="email" class="input input-bordered"
					value=<%=user.getEmail()%> required>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Phone</span>
				</label> <input type="number" name="phone" class="input input-bordered"
					value=<%=user.getPhone()%> required>
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
</body>
</html>