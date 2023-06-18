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
	Sanity Check #008
	<%@include file="header.jsp"%>

	<%
	try {
		String username = (String) session.getAttribute("username");
		String email = (String) request.getAttribute("email");
		String phone = (String) request.getAttribute("phone");
	%>
	<div class="flex flex-col items-center w-full p-5 rounded-xl space-y-6">
		<form action="CustomerPanelServlet" method="post" class="card w-full"
			id="changePasswordForm">
			<h1 class="text-2xl font-bold">Change Password</h1>
			<input type="hidden" name="p" value="changePassword">
			<div class="form-control">
				<label class="label"> <span class="label-text">New Password</span>
				</label> <input type="text" name="password" class="input input-bordered"
					required>
			</div>
			<div class="form-control">
				<label class="label"> <span class="label-text">Please key in your new password again</span>
				</label> <input type="text" name="passwordCheck" class="input input-bordered"
					required>
			</div>
			<button type="submit" class="btn btn-primary mt-5">Save
				Changes</button>
		</form>
		<%
		try {
			String success = (String) request.getParameter("success");
			String err = (String) request.getParameter("err");
			if (success != null) {
				
		%>
		<p class=""><%=success%></p>
		<%
			} else if (err != null) {
		%>
		<p class=""><%=err%></p>	
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