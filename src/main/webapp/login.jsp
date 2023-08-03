<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
    rel="stylesheet">
</head>
<body class="">
    <%@include file="header.jsp"%>
    	<!-- Error Handler -->
	<%
	String err = (String) request.getParameter("err");
	if (err != null) {
		int maxLength = 50;
		if (err.length() > maxLength) {
			err = err.substring(0, maxLength) + "...";
		}
	%>
	<!-- Error Message -->
	<div class="toast toast-top toast-center justify-center">
		<div class="alert alert-error">
			<span><%=err%></span>
		</div>
	</div>
	<%
	}
	%>
    <div class="bg-base flex items-center justify-center h-full">
        <div
            class="flex flex-col items-center w-80 p-10 rounded-xl space-y-6 shadow-xl">
            <div class="tabs w-full">
                <div class="tab tab-bordered tab-active" id="loginTab">Login</div>
                <div class="tab" id="registerTab">Register</div>
            </div>
            <form action="login" method="post" class="card w-full" id="loginForm">
                <h1 class="text-2xl font-bold">Login</h1>
                <input type="hidden" name="action" value="login">
                <div class="form-control">
                    <label class="label"> <span class="label-text">Email</span>
                    </label> <input type="text" name="email" class="input input-bordered"
                        required>
                </div>
                <div class="form-control">
                    <label class="label"> <span class="label-text">Password</span>
                    </label> <input type="password" name="password"
                        class="input input-bordered" required>
                </div>
                <button type="submit" class="btn btn-primary mt-5">Login</button>
            </form>
            <form action="register" method="post" class="card w-full hidden"
                id="registerForm">
                <h1 class="text-2xl font-bold">Register</h1>
                <input type="hidden" name="action" value="register">
                <div class="form-control">
                    <label class="label"> <span class="label-text">Name</span>
                    </label> <input type="text" name="name" class="input input-bordered"
                        required>
                </div>
                <div class="form-control">
                    <label class="label"> <span class="label-text">Email</span>
                    </label> <input type="text" name="email" class="input input-bordered"
                        required>
                </div>
                <div class="form-control">
                    <label class="label"> <span class="label-text">Phone</span>
                    </label> <input type="text" name="phone" class="input input-bordered"
                        required>
                </div>
                <div class="form-control">
                    <label class="label"> <span class="label-text">Password</span>
                    </label> <input type="password" name="password"
                        class="input input-bordered" required>
                </div>
                <button type="submit" class="btn btn-primary mt-5">Register</button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById('loginTab').addEventListener(
                'click',
                function() {
                    document.getElementById('loginForm').classList
                            .remove('hidden');
                    document.getElementById('registerForm').classList
                            .add('hidden');
                    document.getElementById('loginTab').classList
                            .add('tab-active');
                    document.getElementById('loginTab').classList
                            .add('tab-bordered');
                    document.getElementById('registerTab').classList
                            .remove('tab-active');
                    document.getElementById('registerTab').classList
                            .remove('tab-bordered');
                });

        document.getElementById('registerTab').addEventListener(
                'click',
                function() {
                    document.getElementById('registerForm').classList
                            .remove('hidden');
                    document.getElementById('loginForm').classList
                            .add('hidden');
                    document.getElementById('registerTab').classList
                            .add('tab-active');
                    document.getElementById('registerTab').classList
                            .add('tab-bordered');
                    document.getElementById('loginTab').classList
                            .remove('tab-active');
                    document.getElementById('loginTab').classList
                            .remove('tab-bordered');
                });
    </script>
</body>
</html>
