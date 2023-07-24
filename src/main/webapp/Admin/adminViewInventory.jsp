<%@ page import="classes.Book"%>
<%@ page import="java.util.List"%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">View Inventory</h1>
	<div class="container mx-auto mt-5">
		<table class="table table-zebra">
			<thead>
				<tr>
					<th class="text-center">Book ID</th>
					<th class="text-center">Book Name</th>
					<th class="text-center">Price</th>
					<th class="text-center">Quantity</th>
				</tr>
			</thead>
			<tbody>
				<%
				@SuppressWarnings("unchecked")
				List<Book> books = (List<Book>) request.getAttribute("books");
				for (Book book : books) {
				%>
				<tr>
					<td class="text-center"><%=book.getBookId()%></td>
					<td class="text-center"><%=book.getTitle()%></td>
					<td class="text-center"><%=book.getPrice()%></td>
					<td class="text-center"><%=book.getQuantity()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
