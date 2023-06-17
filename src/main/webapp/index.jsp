<%@ page import="java.util.*"%>
<%@ page import="classes.Book"%>
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
	pageContext.setAttribute("cartItems", 4);
	%>
	<%@include file="header.jsp"%>
	<%!int currentPage = 1;
	int totalPages = 1;
	String search;%>
	<%

	%>
	<%
	try {
		search = request.getParameter("books");
		String pageNum = request.getParameter("page");
		String totalPageRaw = request.getAttribute("totalPages").toString();
		currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
		totalPages = totalPageRaw != null ? Integer.parseInt(totalPageRaw) : totalPages;
	} catch (NumberFormatException e) {
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
	<div class="flex justify-center mt-10">
		<div class="w-2/3">
			<form action="BookServlet" method="get">
				<input type="text" name="books" placeholder="Search books"
					class="input input-bordered w-full max-w-xs" /> <input
					type="submit" value="Search" class="btn btn-primary mt-2">
			</form>
		</div>
	</div>
	<div class="flex justify-center mt-10">
		<div class="join">
			<a
				href="BookServlet?page=<%=currentPage - 1%><%=search != null ? ("&books=" + search) : ""%>"
				class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
			<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
				href="BookServlet?page=<%=currentPage + 1%><%=search != null ? ("&books=" + search) : ""%>"
				class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
		</div>
	</div>

	<div class="flex flex-wrap my-10 px-80 justify-between">
		<%
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) request.getAttribute("books");
		if (books != null) {
			for (Book book : books) {
		%>
		<div
			class="card md:w-72 lg:w-96 h-1/6 bg-base-100 shadow-xl m-2 border border-base-300">
			<%
			if (book.getImage() != null && !book.getImage().equals("")) {
			%>
			<figure class="w-full h-80">
				<img src="data:image/jpeg;base64,<%=book.getImage()%>"
					alt="Book img" class="w-full h-full">
			</figure>
			<%
			} else {
			%>
			<figure class="w-full h-80">
				<span class="book-title"><%=book.getTitle()%></span>
			</figure>
			<%
			}
			%>

			<div class="card-body">
				<h2 class="card-title line-clamp-1">
					<%=book.getTitle()%>
				</h2>
				<p class = "line-clamp-2"><%=book.getDescription().length() > 100 ? book.getDescription().substring(0, 100) + "..." : book.getDescription()%></p>
				<div class="card-actions justify-end">
					<form class="btn btn-primary" action="BookDetailsServlet"
						method="get">
						<input type="hidden" name="bookId" value='<%=book.getBookId()%>'>
						<input type="submit" value="SHOW DETAILS" />
					</form>
				</div>
			</div>
		</div>
		<%
		}
		}
		%>
	</div>
	<div class="flex justify-center mt-10 mb-20">
		<div class="join">
			<a
				href="BookServlet?page=<%=currentPage - 1%><%=search != null ? ("&books=" + search) : ""%>"
				class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
			<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
				href="BookServlet?page=<%=currentPage + 1%><%=search != null ? ("&books=" + search) : ""%>"
				class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
		</div>
	</div>
	<%@include file="footer.html"%>

</body>
</html>
