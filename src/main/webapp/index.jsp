<%@ page import="java.util.*"%>
<%@ page import="books.Book"%>
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
	<%
	@SuppressWarnings("unchecked")
	String err = (String) request.getAttribute("err");
	if (err != null) {
		int maxLength = 50;
		if (err.length() > maxLength) {
			err = err.substring(0, maxLength) + "...";
		}
	%>
	<div class="toast toast-top toast-center justify-center">
		<div class="alert alert-error">
			<span><%=err%></span>
		</div>
	</div>
	<%
	}
	%>
	<div class="flex justify-center mt-10">
		<div class="w-2/3">
			<form action="BookServlet" method="get">
				<input type="text" name="books" placeholder="Search books"
					class="input input-bordered w-full max-w-xs" /> <input
					type="submit" value="Search" class="btn btn-primary mt-2">
			</form>
		</div>
	</div>

	<div
		class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-4 m-10 mx-32 px-32">
		<%
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) request.getAttribute("books");
		if (books != null) {
			for (Book book : books) {
		%>
		<div class="card bg-base-100 shadow-xl">
			<div class="card-body">
				<h2 class="card-title">
					<%=book.getTitle()%>
				</h2>
				<p><%=book.getDescription().length() > 100 ? book.getDescription().substring(0, 100) + "..." : book.getDescription()%></p>
				<div class="card-actions justify-end">
					<button class="btn btn-primary">Show Details</button>
				</div>
			</div>
		</div>
		<%
		}
		}
		%>
	</div>




</body>
</html>
