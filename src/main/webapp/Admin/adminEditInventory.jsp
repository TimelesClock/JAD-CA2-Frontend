<%@ page import="classes.Book"%>
<%@ page import="java.util.*"%>
<%
@SuppressWarnings("unchecked")
List<Book> books = (List<Book>) request.getAttribute("books");
%>
<body>
    <h1 class="text-2xl font-bold mb-4 ms-5">Edit Inventory</h1>
    <div class="container mx-auto mt-5">
        <form action="AdminPanelServlet" method="post" class="card">
            <input type="hidden" name="action" value="editInventory">
            <div class="card-body space-y-6">
                <div class="form-control">
                    <label class="label"> <span class="label-text">Select Book</span></label>
                    <select name="book_id" class="select select-bordered" onchange="handleBookSelection(this)">
                        <option value="0" selected>Select a book</option>
                        <% for (Book book : books) { %>
                        <option value="<%=book.getBookId()%>"><%=book.getTitle()%></option>
                        <% } %>
                    </select>
                </div>
                <div id="bookDetails" style="display: none;">
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Price</span></label>
                        <input type="number" name="price" class="input input-bordered" step=0.01 required>
                    </div>
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Quantity</span></label>
                        <input type="number" name="quantity" class="input input-bordered" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Update Inventory</button>
            </div>
        </form>
    </div>

    <script>
        function handleBookSelection(selectElement) {
            var bookDetails = document.getElementById("bookDetails");
            var priceInput = document.getElementsByName("price")[0];
            var quantityInput = document.getElementsByName("quantity")[0];

            if (selectElement.value === "0") {
                bookDetails.style.display = "none";
                priceInput.value = "";
                quantityInput.value = "";
            } else {
                bookDetails.style.display = "block";
                var bookId = selectElement.value;
                <% for (Book book : books) { %>
                if ("<%=book.getBookId()%>" === bookId) {
                    priceInput.value = <%=book.getPrice()%>;
                    quantityInput.value = <%=book.getQuantity()%>;
                    return
                }
                <% } %>
            }
        }
    </script>
</body>
