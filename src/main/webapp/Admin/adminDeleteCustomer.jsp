<%@ page import="classes.User"%>
<%@ page import="java.util.*"%>
<%
@SuppressWarnings("unchecked")
List<User> customers = (List<User>) request.getAttribute("users");
%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">Delete Customer</h1>
	<div class="container mx-auto mt-5">
		<form action="AdminPanelServlet" method="post" class="card">
			<input type="hidden" name="action" value="deleteCustomer">
			<div class="card-body space-y-6">
				<div class="form-control">
					<label class="label"> <span class="label-text">Select Customer</span></label>
					<select name="customer_id" class="select select-bordered" onchange="handleCustomerSelection(this)">
						<option value="0" disabled selected>Select a customer</option>
						<%
						for (User customer : customers) {
						%>
						<option value="<%=customer.getUserId()%>"><%=customer.getName()+"  ID:"+customer.getUserId()%></option>
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
		function handleCustomerSelection(selectElement) {
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
