<body>
    <h1 class="text-2xl font-bold mb-4 ms-5">Add New Customer</h1>
    <div class="container mx-auto mt-5">
        <form action="AdminPanelServlet" method="post" class="card" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="addCustomer">
            <div class="card-body space-y-6">
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
                    <input type="password" name="password" class="input input-bordered" autocomplete="new-password" required>
                </div>
                <div class="form-control">
                    <label class="label"> <span class="label-text">Phone</span></label>
                    <input type="text" name="phone" class="input input-bordered" autocomplete="no" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Customer</button>
            </div>
        </form>
    </div>

    <script>
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
