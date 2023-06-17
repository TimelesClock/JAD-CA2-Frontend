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
	<%
	String err = (String) request.getParameter("err");
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
	<%
	String success = (String) request.getParameter("success");
	if (success != null) {
		int maxLength = 50;
		if (success.length() > maxLength) {
			success = success.substring(0, maxLength) + "...";
		}
	%>
	<!-- Success Message -->
	<div class="toast toast-top toast-center justify-center">
		<div class="alert alert-success">
			<span><%=success%></span>
		</div>
	</div>
	<%
	}
	%>
	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="adminSidePanel.jsp"%>
			<div class="w-10/12">
				<%
				String content = request.getParameter("p");
				if (content != null && content.equals("addBook")) {
				%><%@include file="adminAddBook.jsp"%>
				<%
				} else if (content != null && content.equals("editBook")) {
				%><%@include file="adminEditBook.jsp"%>
				<%
				} else if (content != null && content.equals("deleteBook")) {
				%><%@include file="adminDeleteBook.jsp"%>
				<%
				} else {
				%><div>Hello</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

</body>
</html>