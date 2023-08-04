<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<% List<Order> orders = (List<Order>) request.getAttribute("orders"); 
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

				<!-- Charts of Sales -->
				<div class="flex flex-row mb-8">
					<!-- Bar Chart of Sales -->
					<div class="w-1/2">
						<h2 class="text-lg font-bold">Sales Overview (Bar Chart)</h2>
						<canvas id="salesBarChart" width="400" height="200"></canvas>
					</div>
					<!-- Line Chart of Sales -->
					<div class="w-1/2">
						<h2 class="text-lg font-bold ">Sales Overview (Line Chart)</h2>
						<canvas id="salesLineChart" width="400" height="200"></canvas>
					</div>
				</div>
				<div class="flex flex-row mb-8">
					<!-- Pie Chart -->
					<div class="w-1/2">
						<h2 class="text-lg font-bold">Books Quantity Distribution (Pie Chart)</h2>
						<canvas id="booksPieChart" width="400" height="200"></canvas>
					</div>
					<!-- Polar Area Chart -->
					<div class="w-1/2">
						<h2 class="text-lg font-bold">Books Quantity Distribution (Polar Area Chart)</h2>
						<canvas id="booksPolarAreaChart" width="400" height="200"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
		var labels = [];
		var salesData = [];
		<%
			for (Order order : orders) {
		%>
		labels.push("<%= order.getOrderDate() %>");
		salesData.push(<%= order.getSubtotal() %>);
		<%
			}
		%>
		var bookLabels = [];
		var quantitiesData = [];
		var colors = [];
		<%
			String[] colorArray = {"#30a170", "#155c3d", "#378f99", "#15555c", "#15592d", "#318c51"};
		HashMap<String, Integer> bookQuantityMap = new HashMap<>();
		for (Order order : orders) {
			for (OrderItem item : order.getOrderItems()) {
				bookQuantityMap.put(item.getBookName(), bookQuantityMap.getOrDefault(item.getBookName(), 0) + item.getQuantity());
			}
		}
		int colorIndex = 0;
		for (String bookName : bookQuantityMap.keySet()) {
		%>
			bookLabels.push("<%= bookName %>");
			quantitiesData.push(<%= bookQuantityMap.get(bookName) %>);
			colors.push("<%= colorArray[colorIndex % colorArray.length] %>");
			<%=colorIndex++%>
		<%
			}
		%>

		var barCtx = document.getElementById('salesBarChart').getContext('2d');
		var barChart = new Chart(barCtx, {
			type: 'bar',
			data: {
				labels: labels,
				datasets: [{
					label: 'Sales',
					data: salesData,
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			},
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});

		var lineCtx = document.getElementById('salesLineChart').getContext('2d');
		var lineChart = new Chart(lineCtx, {
			type: 'line',
			data: {
				labels: labels,
				datasets: [{
					label: 'Sales',
					data: salesData,
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			},
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});
		
		var pieCtx = document.getElementById('booksPieChart').getContext('2d');
		var pieChart = new Chart(pieCtx, {
			type: 'pie',
			data: {
				labels: bookLabels,
				datasets: [{
					data: quantitiesData,
					backgroundColor: colors,
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			}
		});

		var polarAreaCtx = document.getElementById('booksPolarAreaChart').getContext('2d');
		var polarAreaChart = new Chart(polarAreaCtx, {
			type: 'polarArea',
			data: {
				labels: bookLabels,
				datasets: [{
					data: quantitiesData,
					backgroundColor: colors,
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				}]
			}
		});
	</script>
</body>