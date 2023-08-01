<%@ page import="models.Book"%>
<%@ page import="java.util.*"%>
<%
@SuppressWarnings("unchecked")
List<Book> books = (List<Book>) request.getAttribute("books");
%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">Delete Book</h1>
	<div class="container mx-auto mt-5">
		<form action="AdminPanelServlet" method="post" class="card">
			<input type="hidden" name="action" value="deleteBook">
			<div class="card-body space-y-6">
				<div class="form-control">
					<label class="label"> <span class="label-text">Select Book</span></label>
					<select name="book_id" class="select select-bordered" onchange="handleBookSelection(this)">
						<option value="0" disabled selected>Select a book</option>
						<%
						for (Book book : books) {
						%>
						<option value="<%=book.getBookId()%>"><%=book.getTitle()%></option>
						<%
						}
						%>
					</select>
				</div>
				<button type="submit" class="btn btn-outline btn-disabled" id="deleteButton" disabled>Delete</button>
			</div>
		</form>
	</div>

	<script>
		function handleBookSelection(selectElement) {
			var deleteButton = document.getElementById("deleteButton");
			
			if (selectElement.value === "0") {
				deleteButton.disabled = true;
				deleteButton.classList.add("btn-disabled");
				deleteButton.classList.remove("btn-error");
			} else {
				deleteButton.disabled = false;
				deleteButton.classList.remove("btn-disabled");
				deleteButton.classList.add("btn-error");
			}
		}
	</script>
</body>
