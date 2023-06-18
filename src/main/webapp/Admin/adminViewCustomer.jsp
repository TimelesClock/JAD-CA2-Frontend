<%@ page import="classes.User"%>
<%@ page import="java.util.List"%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">View Customers</h1>
	<div class="container mx-auto mt-5">
		<table class="table table-zebra">
			<thead>
				<tr>
					<th class="text-center">User ID</th>
					<th class="text-center">Customer Name</th>
					<th class="text-center">Email</th>
					<th class="text-center">Phone</th>
				</tr>
			</thead>
			<tbody>
				<%
				@SuppressWarnings("unchecked")
				List<User> customers = (List<User>) request.getAttribute("users");
				for (User customer : customers) {
				%>
				<tr>
					<td class="text-center"><%=customer.getUserId()%></td>
					<td class="text-center"><%=customer.getName()%></td>
					<td class="text-center"><%=customer.getEmail()%></td>
					<td class="text-center"><%=customer.getPhone()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</body>
