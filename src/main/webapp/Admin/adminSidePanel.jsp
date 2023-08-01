<body>

	<div class="w-2/12 h-screen shadow-xl">
		<form action="home" method="get" class="mx-2 p-0">
			<button type="submit" class="btn btn-outline w-full h-full">Back
				To Homepage</button>
		</form>
		<h2 class="text-2xl font-bold my-4 ms-5">Admin panel</h2>
		<div class="divider">Books</div>
		<ul class="p-2 space-y-2 menu">
			<li>
				<form action="admin/book" method="get" class="mx-2 p-0">
					<button type="submit" class="w-full h-full p-3">Books</button>
				</form>
			</li>
		</ul>
		<div class="divider">Customer</div>
		<ul class="p-2 space-y-2 menu">
			<li>
				<form action="admin" method="get" class="mx-2 p-0">
					<input type="hidden" name="p" value="viewCustomer">
					<button type="submit" class="w-full h-full p-3">View
						Customer</button>
				</form>
			</li>
			<li>
				<form action="admin" method="get" class="mx-2 p-0">
					<input type="hidden" name="p" value="addCustomer">
					<button type="submit" class="w-full h-full p-3">Add
						Customer</button>
				</form>
			</li>
			<li>
				<form action="admin" method="get" class="mx-2 p-0">
					<input type="hidden" name="p" value="editCustomer">
					<button type="submit" class="w-full h-full p-3">Edit
						Customer</button>
				</form>
			</li>
			<li>
				<form action="admin" method="get" class="mx-2 p-0">
					<input type="hidden" name="p" value="deleteCustomer">
					<button type="submit" class="w-full h-full p-3">Delete
						Customer</button>
				</form>
			</li>
		</ul>
	</div>
</body>