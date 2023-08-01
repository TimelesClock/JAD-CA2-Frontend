<%@ page import="models.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="models.Author"%>
<%@ page import="models.Publisher"%>
<%@ page import="models.Genre"%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">View Inventory</h1>
	<button class="btn" onclick="book_modal.showModal()">Add New
		Book</button>
	<div class="flex justify-center my-5">
		<div class="join">
			<a href="" class="join-item btn">«</a> <a href="#"
				class="join-item btn">Page 1</a> <a href="" class="join-item btn">»</a>
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
								<img src="image/book.jpg" alt="Avatar Tailwind CSS Component" />
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
	<%@include file="adminAddBookModal.jsp"%>
	

	<%
	for (Book book : books) {
	%>
	<!-- Show Details Modal -->
	<dialog id="showDetailsModal_<%=book.getBookId()%>" class="modal">
	<form method="dialog" class="modal-box">
		<h3 class="font-bold text-lg">Book Details</h3>
		<!-- You can populate book details here -->
		<div class="modal-action">
			<button class="btn">Close</button>
		</div>
	</form>
	</dialog>

	<!-- Edit Details Modal -->
	<dialog id="editDetailsModal_<%=book.getBookId()%>" class="modal">
	<form method="dialog" class="modal-box">
		<h3 class="font-bold text-lg">Edit Details</h3>
		<!-- You can add form to edit book details here -->
		<div class="modal-action">
			<button class="btn">Close</button>
		</div>
	</form>
	</dialog>

	<!-- Delete Modal -->
	<dialog id="deleteModal_<%=book.getBookId()%>" class="modal">
	<form method="dialog" class="modal-box">
		<h3 class="font-bold text-lg">Delete Book</h3>
		<p class="py-4">Are you sure you want to delete this book?</p>
		<div class="modal-action">
			<button class="btn">Yes</button>
			<button class="btn">No</button>
		</div>
	</form>
	</dialog>
	<%
	}
	%>
	<div class="flex justify-center my-5">
		<div class="join">
			<a href="" class="join-item btn">«</a> <a href="#"
				class="join-item btn">Page 1</a> <a href="" class="join-item btn">»</a>
		</div>
	</div>
	
</body>
