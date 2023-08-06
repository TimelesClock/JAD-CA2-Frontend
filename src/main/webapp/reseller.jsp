<%@ page import="java.util.*"%>
<%@ page import="models.Book"%>
<%@ page import="models.Genre"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
	rel="stylesheet">
</head>
<body>
	<%@include file="header.jsp"%>
	<%!int currentPage = 1;
	int totalPages = 1;
	String token;%>

	<%
	try {
		token = (String)request.getAttribute("token");
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
	<!-- Error Handler -->
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
	<div class="flex flex-row">
		<div class="flex justify-center ms-48 mt-10 w-1/2">
			API Token: <%=token %>
		</div>
		<div class=" w-2/3 flex h-min m-5 rounded"></div>
	</div>
	<script>
		
	</script>

</body>
</html>
