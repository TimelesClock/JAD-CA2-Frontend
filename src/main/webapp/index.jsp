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
	String search;
	String genreId;
	String minRating;
	String maxRating;
	String minPrice;
	String maxPrice;%>

	<%
	try {
		search = request.getParameter("books");
		genreId = request.getParameter("genreId");
		minRating = request.getParameter("minRating");
		maxRating = request.getParameter("maxRating");
		minPrice = request.getParameter("minPrice");
		maxPrice = request.getParameter("maxPrice");
		String pageNum = request.getParameter("page");
		String totalPageRaw = request.getAttribute("totalPages") != null
		? request.getAttribute("totalPages").toString()
		: "";
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
	<div class="flex flex-row">
		<div class="flex justify-center ms-48 mt-10 w-1/2">

			<form action="BookServlet" method="get" class="w-2/3">
				<input type="text" name="books" placeholder="Search books"
					class="input input-bordered w-full max-w-xs" /> <input
					type="submit" value="Search" class="btn btn-primary mt-2">
			</form>
		</div>
		<div class=" w-2/3 flex h-min m-5 rounded">
			<form id="filters" action="BookServlet" method="get" class="position-lg-fixed me-lg-5">
				<input class="hidden" name="bookName"
					value="<%=search != null ? search : ""%>">
				<h2 class="flex flex-row p-3 text-xl font-bold" id="filter-title">Filter</h2>
				<div class="flex flex-row">
					<div class="flex-row me-3 ps-3 pb-3 filter-box rounded">
						<select class="form-select rounded-xl mt-8 p-2 border"
							aria-label="" id="genreId" name="genre">
							<option class="genre-option" disabled selected>Genre</option>
							<%
							@SuppressWarnings("unchecked")
							List<Genre> genres = (List<Genre>) request.getAttribute("genres");
							if (genres != null){
							for (Genre item : genres) {
							%>
							<option class="genre-option" id="<%=item.getId()%>">
								<%=item.getName()%>
							</option>
							<%
							}}
							%>
						</select>
					</div>
					<div class="filter-box ps-3 pb-4 rounded">
						<h3>Rating</h3>
						<div class="flex flex-row mt-2 justify-start">
							<div class="flex w-20 me-2">
								<input class="form-control w-full rounded-xl p-2 border"
									type="text" placeholder="Min" pattern="[-+]?[0-9]*[.,]?[0-9]+"
									title="Please enter numbers." id="minRating" name="minRating" />
							</div>
							<div class="flex w-20">
								<input class="form-control w-full rounded-xl p-2 border"
									type="text" placeholder="Max" pattern="[-+]?[0-9]*[.,]?[0-9]+"
									title="Please enter numbers." id="maxRating" name="maxRating" />
							</div>
						</div>
					</div>
					<div class="filter-box ps-3 pb-4 rounded">
						<h3>Price</h3>
						<div class="flex flex-row mt-2 justify-start">
							<div class="flex w-20 me-2">
								<input class="form-control w-full rounded-xl p-2 border"
									type="text" placeholder="Min" pattern="[-+]?[0-9]*[.,]?[0-9]+"
									title="Please enter numbers." id="minPrice" name="minPrice" />
							</div>
							<div class="flex w-20">
								<input class="form-control w-full rounded-xl p-2 border"
									type="text" placeholder="Max" pattern="[-+]?[0-9]*[.,]?[0-9]+"
									title="Please enter numbers." id="maxPrice" name="maxPrice" />
							</div>
						</div>
					</div>
				</div>
				<div class="flex flex-row filter-box rounded">
					<div class="flex m-2 rounded">
						<button class="btn btn-outline btn-info px-3" type="submit"
							id="filter-submit">Apply Filter</button>
					</div>
					<div class="flex m-2 rounded">
						<button class="btn btn-error btn-outline px-3" type="reset"
							id="filter-reset">Reset</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="flex justify-center">
		<div class="join">
			<a
				href="BookServlet?page=<%=currentPage - 1%><%=search != null ? ("&books=" + search) : ""%>
				<%=genreId != null ? ("&genreId=" + genreId) : ""%>
				<%=minRating != null ? ("&minRating=" + minRating) : ""%>
				<%=maxRating != null ? ("&maxRating=" + maxRating) : ""%>
				<%=minPrice != null ? ("&minPrice=" + minPrice) : ""%>
				<%=maxPrice != null ? ("&maxPrice=" + maxPrice) : ""%>"
				class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
			<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
				href="BookServlet?page=<%=currentPage + 1%><%=search != null ? ("&books=" + search) : ""%>
				<%=genreId != null ? ("&genreId=" + genreId) : ""%>
				<%=minRating != null ? ("&minRating=" + minRating) : ""%>
				<%=maxRating != null ? ("&maxRating=" + maxRating) : ""%>
				<%=minPrice != null ? ("&minPrice=" + minPrice) : ""%>
				<%=maxPrice != null ? ("&maxPrice=" + maxPrice) : ""%>"
				class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
		</div>
	</div>



	<div class="flex flex-wrap my-10 justify-evenly xl:px-60 lg:px-32">
		<%
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>) request.getAttribute("books");
		if (books != null) {
			for (Book book : books) {
		%>
		<div
			class="card lg:m-1 lg:w-72 xl:w-96 h-1/6 bg-base-100 shadow-xl m-2 mt-5 border border-base-300">
			<%
			if (book.getImageUrl() != null && !book.getImageUrl().equals("")) {
			%>
			<figure class="w-full h-80">
				<img src="data:image/jpeg;base64,<%=book.getImageUrl()%>"
					alt="Book img" class="w-full h-full">
			</figure>
			<%
			} else {
			%>
			<figure class="w-full h-80">
				<span class="	"><%=book.getTitle()%></span>
			</figure>
			<%
			}
			%>

			<div class="card-body">
				<h2 class="card-title line-clamp-1">
					<%=book.getTitle()%>
				</h2>
				<p class="line-clamp-2"><%=book.getDescription().length() > 100 ? book.getDescription().substring(0, 100) + "..." : book.getDescription()%></p>
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
				href="BookServlet?page=<%=currentPage - 1%><%=search != null ? ("&books=" + search) : ""%>
				<%=genreId != null ? ("&genreId=" + genreId) : ""%>
				<%=minRating != null ? ("&minRating=" + minRating) : ""%>
				<%=maxRating != null ? ("&maxRating=" + maxRating) : ""%>
				<%=minPrice != null ? ("&minPrice=" + minPrice) : ""%>
				<%=maxPrice != null ? ("&maxPrice=" + maxPrice) : ""%>"
				class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
			<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
				href="BookServlet?page=<%=currentPage + 1%><%=search != null ? ("&books=" + search) : ""%>
				<%=genreId != null ? ("&genreId=" + genreId) : ""%>
				<%=minRating != null ? ("&minRating=" + minRating) : ""%>
				<%=maxRating != null ? ("&maxRating=" + maxRating) : ""%>
				<%=minPrice != null ? ("&minPrice=" + minPrice) : ""%>
				<%=maxPrice != null ? ("&maxPrice=" + maxPrice) : ""%>"
				class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
		</div>
	</div>
	<%@include file="footer.html"%>
	<script>
		function resetFilters() {

			var url = new URL(window.location.href);
			url.search = '';

			window.location.href = url.toString();
		}

		var resetButton = document.getElementById("filter-reset");
		resetButton.addEventListener("click", resetFilters);
	</script>

</body>
</html>
