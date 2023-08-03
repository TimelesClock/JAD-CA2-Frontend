<body>
	<script src="https://cdn.jsdelivr.net/npm/theme-change@2.0.2/index.js"></script>
	<div class="w-2/12 h-screen shadow-xl">
		<form action="<%=request.getContextPath() + "/home" %>" method="get" class="mx-2 p-0">
			<button type="submit" class="btn btn-outline w-full h-full">Back
				To Homepage</button>
		</form>
		<h2 class="text-2xl font-bold my-4 ms-5">Admin panel</h2>
		<div class="divider">Books</div>
		<ul class="p-2 space-y-2 menu">
			<li>
				<form action="<%=request.getContextPath() + "/admin/book" %>" method="get" class="mx-2 p-0 flex">
					<button type="submit" class="w-full h-full p-3 text-start">Books</button>
				</form>
			</li>
		</ul>
		<div class="divider">Customer</div>
		<ul class="p-2 space-y-2 menu">
			<li>
				<form action="<%=request.getContextPath() + "/admin/customer" %>" method="get" class="mx-2 p-0 flex">
					<button type="submit" class="w-full h-full p-3 text-start">Customer</button>
				</form>
			</li>
		</ul>
	</div>
</body>