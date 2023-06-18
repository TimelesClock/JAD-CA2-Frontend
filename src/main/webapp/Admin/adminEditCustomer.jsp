<%@page import = "classes.User" %>
<%@page import = "java.util.*" %>
<%
@SuppressWarnings("unchecked")
List<User> customers = (List<User>) request.getAttribute("users");
%>
<body>
    <h1 class="text-2xl font-bold mb-4 ms-5">Edit Customer</h1>
    <div class="container mx-auto mt-5">
        <form action="AdminPanelServlet" method="post" class="card" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="editCustomer">
            <div class="card-body space-y-6">
                <div class="form-control">
                    <label class="label"> <span class="label-text">Select Customer</span></label>
                    <select name="customer_id" class="select select-bordered" onchange="handleCustomerSelection(this)">
                        <option value="0" selected>Select a customer</option>
                        <% for (User customer : customers) { %>
                        <option value="<%=customer.getUserId()%>"><%=customer.getName()+"  ID:"+customer.getUserId()%></option>
                        <% } %>
                    </select>
                </div>
                <div id="customerDetails" style="display: none;">
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Name</span></label>
                        <input type="text" name="name" class="input input-bordered" autocomplete="no" required>
                    </div>
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Email</span></label>
                        <input type="email" name="email" id="email" class="input input-bordered" autocomplete="no" required>
                        <span id="emailError" class="text-red-500"></span>
                    </div>
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Password</span></label>
                        <input type="password" name="password" class="input input-bordered" autocomplete="new-password">
                    </div>
                    <div class="form-control">
                        <label class="label"> <span class="label-text">Phone</span></label>
                        <input type="text" name="phone" class="input input-bordered" autocomplete="no" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Update Customer</button>
            </div>
        </form>
    </div>

    <script>
        function handleCustomerSelection(selectElement) {
            var customerDetails = document.getElementById("customerDetails");
            var nameInput = document.getElementsByName("name")[0];
            var emailInput = document.getElementById("email");
            var passwordInput = document.getElementsByName("password")[0];
            var phoneInput = document.getElementsByName("phone")[0];

            if (selectElement.value === "0") {
                customerDetails.style.display = "none";
                nameInput.value = "";
                emailInput.value = "";
                passwordInput.value = "";
                phoneInput.value = "";
            } else {
                customerDetails.style.display = "block";
                var customerId = selectElement.value;
                <% for (User customer : customers) { %>
                if ("<%=customer.getUserId()%>" === customerId) {
                    nameInput.value = "<%=customer.getName()%>";
                    emailInput.value = "<%=customer.getEmail()%>";
                    passwordInput.value = "";
                    phoneInput.value = "<%=customer.getPhone()%>";
                    return;
                }
                <% } %>
            }
        }

        function validateForm() {
            var emailInput = document.getElementById("email");
            var emailError = document.getElementById("emailError");

            var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            var email = emailInput.value.trim();

            if (!emailPattern.test(email)) {
                emailError.textContent = "Invalid email format";
                emailInput.classList.add("border-red-500");
                return false;
            } else {
                emailError.textContent = "";
                emailInput.classList.remove("border-red-500");
                return true;
            }
        }
    </script>
</body>
