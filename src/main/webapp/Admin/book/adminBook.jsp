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
			<%@include file="/admin/adminSidePanel.jsp"%>
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
										onclick="handleShowModal(<%=book.getBookId()%>)">Show</button>
									<button class="btn"
										onclick="handleEditModal(<%=book.getBookId()%>)">Edit</button>
									<button class="btn"
										onclick="handleDeleteModal(<%=book.getBookId()%>)">Delete</button>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%@include file="adminAddBookModal.jspf"%>
				
				<%@include file="adminEditBookModal.jspf"%>
				
				<%@include file="adminShowBookModal.jspf"%>
				
				<%@include file="adminDeleteBookModal.jspf"%>
				
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
	<%@include file="../../errorHandler.jsp"%>
	<script>
	function handleDeleteModal(bookId){
		const deleteBookId = document.getElementById("delete_book_id");
		const deletePrevUrl = document.getElementById("delete_prev_url");
		<%for (Book book : books) {%>
		if ("<%=book.getBookId()%>" === bookId.toString()) {
			deleteBookId.value = <%=book.getBookId()%>
			deletePrevUrl.value = '<%=(book.getImageUrl() != null) ? book.getImageUrl() : ""%>';
			deleteModal.show();
			return;
		}
<%}%>
		
	}
	function handleShowModal(bookId){
		const showTitle = document.getElementById('show_title');
		const showDescription = document.getElementById('show_description');
		const showAuthorId = document.getElementById('show_author_id');
		const showAuthorName = document.getElementById('show_author_name');
		const showPrice = document.getElementById('show_price');
		const showQuantity = document.getElementById('show_quantity');
		const showPublicationDate = document.getElementById('show_date');
		const showISBN = document.getElementById('show_isbn');
		const showRating = document.getElementById('show_rating');
		const showPublisherId = document.getElementById('show_publisher_id');
		const showPublisherName = document.getElementById('show_publisher_name');
		const showGenreId = document.getElementById('show_genre_id');
		const showGenreName = document.getElementById('show_genre_name');
		const showImage = document.getElementById("show_image");
		<%for (Book book : books) {%>
			if ("<%=book.getBookId()%>" === bookId.toString()) {
				showTitle.innerText = "<%=book.getTitle()%>";
				showDescription.innerText = "<%=book.getDescription()%>";
				showAuthorId.innerText = "<%=book.getAuthorId()%>";
				showAuthorName.innerText = "<%=book.getAuthorName()%>";
				showPrice.innerText = "<%=book.getPrice()%>";
				showQuantity.innerText = "<%=book.getQuantity()%>";
				showPublicationDate.innerText = "<%=book.getPublicationDate()%>";
				showISBN.innerText = "<%=book.getISBN()%>";
				showRating.innerText = "<%=book.getRating()%>";
				showPublisherId.innerText = "<%=book.getPublisherId()%>";
				showPublisherName.innerText = "<%=book.getPublisherName()%>";
				showGenreId.innerText = "<%=book.getGenreId()%>";
				showGenreName.innerText = "<%=book.getGenreName()%>";
				showImage.src = '<%=(book.getImageUrl() != null) ? book.getImageUrl() : (request.getContextPath() + "/image/book.jpg")%>';
				showDetailsModal.showModal();
				return;
			}
	<%}%>
	}
	function handleEditModal(bookId){
		const editTitleInput = document.getElementById('edit_title');
		const editDescriptionTextarea = document.getElementById('edit_description');
		const editPriceInput = document.getElementById('edit_price');
		const editQuantityInput = document.getElementById('edit_quantity');
		const editPublicationDateInput = document.getElementById('edit_date');
		const editISBNInput = document.getElementById('edit_isbn');
		const editRatingInput = document.getElementById('edit_rating');
		const editAuthorIdSelect = document.getElementById('edit_author_id');
		const editPublisherIdSelect = document.getElementById('edit_publisher_id');
		const editGenreIdSelect = document.getElementById('edit_genre_id');
		const editPrevUrl = document.getElementById('prev_url');
		const editImage = document.getElementById('edit_image');
		const editBookId = document.getElementById("edit_book_id");

		<%for (Book book : books) {%>
		if ("<%=book.getBookId()%>" === bookId.toString()) {
			editBookId.value = "<%=book.getBookId()%>";
			editQuantityInput.value = "<%=book.getQuantity()%>";
			editTitleInput.value = "<%=book.getTitle()%>";
			editAuthorIdSelect.value = "<%=book.getAuthorId()%>";
			editPriceInput.value = "<%=book.getPrice()%>";
			editPublicationDateInput.valueAsDate = new Date("<%=book.getPublicationDate()%>");
			editISBNInput.value = "<%=book.getISBN()%>";
			editRatingInput.value = "<%=book.getRating()%>";
			editDescriptionTextarea.value = "<%=book.getDescription()%>";
			editPublisherIdSelect.value = "<%=book.getPublisherId()%>";
			editGenreIdSelect.value = "<%=book.getGenreId()%>";
			editPrevUrl.value = '<%=(book.getImageUrl() != null) ? book.getImageUrl() : ""%>';
			editImage.src = '<%=(book.getImageUrl() != null) ? book.getImageUrl() : (request.getContextPath() + "/image/book.jpg")%>';
			editDetailsModal.showModal();
			return;
		}
<%}%>
	}
	
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