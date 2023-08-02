<%@ page import="models.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="models.Author"%>
<%@ page import="models.Publisher"%>
<%@ page import="models.Genre"%>
<%
@SuppressWarnings("unchecked")
List<Author> authors = (List<Author>) request.getAttribute("authors");
@SuppressWarnings("unchecked")
List<Publisher> publishers = (List<Publisher>) request.getAttribute("publishers");
@SuppressWarnings("unchecked")
List<Genre> genres = (List<Genre>) request.getAttribute("genres");
%>
<%!int currentPage;
	String imageUrl;
	int totalPages;%>
<%
try {
	String pageNum = request.getParameter("page");
	String totalPageRaw = request.getAttribute("totalPages") != null
	? request.getAttribute("totalPages").toString()
	: "";
	currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
	totalPages = totalPageRaw != null ? Integer.parseInt(totalPageRaw) : totalPages;
} catch (Exception e) {
	totalPages = 1;
	currentPage = 1;
}
%>
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
	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="adminSidePanel.jsp"%>
			<div class="w-10/12 grid">
				<h1 class="text-2xl font-bold mb-4 ms-5">View Inventory</h1>
				<button class="btn" onclick="book_modal.showModal()">Add
					New Book</button>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/book?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/book?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
				<div class="container mx-auto mt-5">
					<table class="table table-zebra">
						<thead>
							<tr>
								<th class="text-center">Book ID</th>
								<th class="text-center">Book Name</th>
								<th class="text-center">Price</th>
								<th class="text-center">Quantity</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							@SuppressWarnings("unchecked")
							List<Book> books = (List<Book>) request.getAttribute("books");
							for (Book book : books) {
							%>
							<tr>
								<td class="flex items-center space-x-3">
									<div class="avatar">
										<div class="mask mask-squircle w-12 h-12">
											<%
											String imageUrl = book.getImageUrl();
											if (imageUrl == null) {
												imageUrl = request.getContextPath() + "/image/book.jpg";
											}
											%>
											<img src="<%=imageUrl%>" alt="image" />
										</div>
									</div>
									<div class="ms-5 font-bold"><%=book.getBookId()%></div>
								</td>
								<td class="text-center"><%=book.getTitle()%></td>
								<td class="text-center"><%=book.getPrice()%></td>
								<td class="text-center"><%=book.getQuantity()%></td>
								<td class="text-center">
									<button class="btn"
										onclick="showDetailsModal_<%=book.getBookId()%>.showModal()">Show</button>
									<button class="btn"
										onclick="editDetailsModal_<%=book.getBookId()%>.showModal()">Edit</button>
									<button class="btn"
										onclick="deleteModal_<%=book.getBookId()%>.showModal()">Delete</button>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%@include file="adminAddBookModal.jspf"%>

				<%
				for (Book book : books) {
				%>
				<!-- Show Details Modal -->
				<%@include file="adminShowBookModal.jspf"%>

				<!-- Edit Details Modal -->
				<%@include file="adminEditBookModal.jspf"%>

				<!-- Delete Modal -->
				<dialog id="deleteModal_<%=book.getBookId()%>" class="modal">
				<form class="modal-box" method="post"
					action="<%=request.getContextPath() + "/admin/book/delete"%>">
					<input type="hidden" name="book_id" value="<%=book.getBookId()%>">
					<input type="hidden" name="prev_url" value="<%=book.getImageUrl()%>">
					<h3 class="font-bold text-lg">Delete Book</h3>
					<p class="py-4">Are you sure you want to delete this book?</p>
					<div class="modal-action">
						<button type="submit" class="btn">Yes</button>
						<button type="button" class="btn"
							onclick="this.closest('dialog').close();">No</button>
					</div>
				</form>
				</dialog>
				<%
				}
				%>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/book?page=" + (currentPage - 1)%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/book?page=" + (currentPage + 1)%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../errorHandler.jsp"%>
	<script>
	function handleAuthorSelection(selectElement) {
		var newAuthorForm = document.getElementById("newAuthorForm");
		if (selectElement.value === "0") {
			newAuthorForm.style.display = "block";
			document.getElementsByName("new_author_name")[0].required = true;
		} else {
			newAuthorForm.style.display = "none";
			document.getElementsByName("new_author_name")[0].required = false;
			document.getElementsByName("new_author_name")[0].value = "";
		}
	}

	function handlePublisherSelection(selectElement) {
		var newPublisherForm = document.getElementById("newPublisherForm");
		if (selectElement.value === "0") {
			newPublisherForm.style.display = "block";
			document.getElementsByName("new_publisher_name")[0].required = true;
		} else {
			newPublisherForm.style.display = "none";
			document.getElementsByName("new_publisher_name")[0].required = false;
			document.getElementsByName("new_publisher_name")[0].value = "";
		}
	}

	function handleGenreSelection(selectElement) {
		var newGenreForm = document.getElementById("newGenreForm");
		if (selectElement.value === "0") {
			newGenreForm.style.display = "block";
			document.getElementsByName("new_genre_name")[0].required = true;
		} else {
			newGenreForm.style.display = "none";
			document.getElementsByName("new_genre_name")[0].required = false;
			document.getElementsByName("new_genre_name")[0].value = "";
		}
	}

	function validateForm() {
		var imageInput = document.getElementById("imageInput");
		var file = imageInput.files[0];
		var maxSize = 5 * 1024 * 1024; // 5MB

		if (!file) {
			alert("Please select an image file.");
			return false;
		}

		if (file.size > maxSize) {
			alert("File size exceeds the maximum limit of 5MB.");
			return false;
		}

		var fileType = file.type;
		var validTypes = [ "image/jpeg", "image/png" ];
		if (!validTypes.includes(fileType)) {
			alert("Invalid file type. Only JPEG and PNG files are accepted.");
			return false;
		}

		var reader = new FileReader();
		reader.onload = function(e) {
			var encodedImage = reader.result.split(',')[1];
			document.getElementById("image").value = encodedImage;

			document.getElementById("form").submit();
		};
		reader.readAsDataURL(file);

		return false;
	}
	</script>
</body>