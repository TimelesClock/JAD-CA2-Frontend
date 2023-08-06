<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%
@SuppressWarnings("unchecked")
List<Order> orders = (List<Order>) request.getAttribute("orders");
HashMap<Integer, Integer> bookQuantities = new HashMap<>();
for (Order order : orders) {
	for (OrderItem item : order.getOrderItems()) {
		bookQuantities.put(item.getBookId(), bookQuantities.getOrDefault(item.getBookId(), 0) + item.getQuantity());
	}
}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="/admin/adminSidePanel.jsp"%>
			<div class="w-10/12">
				<h1 class="text-xl font-bold my-4">Sales Dashboard</h1>

				<div class="flex flex-row mb-8">
					<div class="w-1/2">
						<h2 class="text-lg font-bold">Sales Overview (Bar Chart)</h2>
						<canvas id="salesBarChart" width="400" height="200"></canvas>
					</div>
					<div class="w-1/2">
						<h2 class="text-lg font-bold ">Sales Overview (Line Chart)</h2>
						<canvas id="salesLineChart" width="400" height="200"></canvas>
					</div>
				</div>
				
				<div class="flex flex-row mb-8">
					<div class="w-1/2">
						<h2 class="text-lg font-bold">Top 10 customers by purchase
							value</h2>
						<table class="table table-zebra">
							<thead class="">
								<tr>
									<th class="">Customer Name</th>
									<th class="">Customer ID</th>
									<th class="">Total Spent</th>
								</tr>
							</thead>
							<tbody>
								<%
								@SuppressWarnings("unchecked")
								List<Map<String, Object>> customerData = (List<Map<String, Object>>) request.getAttribute("customerData");
								%>
								<%
								for (Map<String, Object> customer : customerData) {
								%>
								<tr>
									<td class=""><%=customer.get("customer_name")%></td>
									<td class=""><%=customer.get("customer_id")%></td>
									<td class=""><%=customer.get("total_spent")%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
		var labels = [];
		var salesData = [];
		<%for (Order order : orders) {%>
		labels.push("<%=order.getOrderDate()%>");
		salesData.push(<%=order.getSubtotal()%>);
	<%}%>
		var barCtx = document.getElementById('salesBarChart').getContext('2d');
		var barChart = new Chart(barCtx, {
			type : 'bar',
			data : {
				labels : labels,
				datasets : [ {
					label : 'Sales',
					data : salesData,
					backgroundColor : 'rgba(75, 192, 192, 0.2)',
					borderColor : 'rgba(75, 192, 192, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				scales : {
					y : {
						beginAtZero : true
					}
				}
			}
		});

		var lineCtx = document.getElementById('salesLineChart')
				.getContext('2d');
		var lineChart = new Chart(lineCtx, {
			type : 'line',
			data : {
				labels : labels,
				datasets : [ {
					label : 'Sales',
					data : salesData,
					backgroundColor : 'rgba(75, 192, 192, 0.2)',
					borderColor : 'rgba(75, 192, 192, 1)',
					borderWidth : 1
				} ]
			},
			options : {
				scales : {
					y : {
						beginAtZero : true
					}
				}
			}
		});
	</script>
</body>