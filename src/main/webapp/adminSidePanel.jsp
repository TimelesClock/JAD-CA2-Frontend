
<body>
	<div class="w-2/12 h-screen shadow-xl">
    <h2 class="text-2xl font-bold my-4 ms-5">Admin panel</h2>
    <div class="divider">Books</div>
    <ul class="p-2 space-y-2 menu">
        <li>
            <form action="AdminPanelServlet" method="get" class="mx-2">
                <input type="hidden" name="p" value="addBook">
                <button type="submit" class="">Add new book</button>
            </form>
        </li>
        <li>
            <form action="AdminPanelServlet" method="get" class="mx-2">
                <input type="hidden" name="p" value="editBook">
                <button type="submit" class="">Edit existing book</button>
            </form>
        </li>
        <li>
            <form action="AdminPanelServlet" method="get" class="mx-2">
                <input type="hidden" name="p" value="deleteBook">
                <button type="submit" class="">Delete existing book</button>
            </form>
        </li>
    </ul>
    <div class="divider">Inventory</div>
    <ul class="p-2 space-y-2 menu">
        <li>
            <form action="AdminPanelServlet" method="get" class="mx-2">
                <input type="hidden" name="p" value="viewInventory">
                <button type="submit" class="">View Inventory</button>
            </form>
        </li>
        <li>
            <form action="AdminPanelServlet" method="get" class="mx-2">
                <input type="hidden" name="p" value="updateInventory">
                <button type="submit" class="">Update Inventory Level</button>
            </form>
        </li>
    </ul>
</div>


</body>
