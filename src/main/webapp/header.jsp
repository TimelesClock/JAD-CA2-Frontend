<html>

<head>
<script src="https://cdn.jsdelivr.net/npm/theme-change@2.0.2/index.js"></script>
</head>
<body>
	<div class="navbar bg-base-100 shadow-xl">
		<div class="flex-1">
			<form action="home" method="get">
				<button type="submit" class="btn btn-ghost normal-case text-xl">SP
					Books</button>
			</form>

		</div>
		<div class="form-control">
			<label class="label cursor-pointer"> <span class="label-text">
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
						fill="currentColor" class="bi bi-moon-fill" viewBox="0 0 16 16">
  <path
							d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z" />
</svg>
			</span> <input type="checkbox" class="toggle mx-1"
				data-toggle-theme="dark,light" data-act-class="ACTIVECLASS" /> <span
				class="label-text"><svg xmlns="http://www.w3.org/2000/svg"
						width="16" height="16" fill="currentColor"
						class="bi bi-brightness-high-fill" viewBox="0 0 16 16">
  <path
							d="M12 8a4 4 0 1 1-8 0 4 4 0 0 1 8 0zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z" />
</svg> </span>
			</label>
		</div>

		<div class="flex-none">
			<div class="menu menu-horizontal px-1">
				<%
				HttpSession loginSession = request.getSession(false);
				if (loginSession.getAttribute("userid") != null &&request.getAttribute("role")!= null&& request.getAttribute("role").equals("admin")) {
				%>
				<form action="admin" method="get" class="mx-2">
					<input type="hidden" name="action" value="login">
					<button type="submit" class="btn btn-outline">Admin Panel</button>
				</form>
				<%
				} else {
				%>
				<%@include file="Customer/customerHeader.jsp"%>
				<%
				}
				%>
				<%
				if (loginSession != null && loginSession.getAttribute("userid") != null) {
				%>
				<!-- Logout button -->
				<form action="logout" method="post" class="mx-2">
					<input type="hidden" name="action" value="logout">
					<button type="submit" class="btn btn-outline btn-error">Logout</button>
				</form>
				<%
				} else {
				%>
				<!-- Login button -->
				<form action="login" method="get" class="mx-2">
					<input type="hidden" name="action" value="login">
					<button type="submit" class="btn btn-outline">Login</button>
				</form>
				<%
				}
				%>
			</div>
		</div>
	</div>

</body>
</html>