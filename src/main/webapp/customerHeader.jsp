<body>
	<%
	try {
		String username = (String) session.getAttribute("username");
	%>
	<div class="flex flex-row items-center">

		<form action="CustomerPanelServlet" method="get">
			<input type="hidden" name="p" value="myCart">
			<button type="submit">
				<svg xmlns="http://www.w3.org/2000/svg"
					class="w-10 h-10 mx-2 stroke-slate-800 fill-none"
					viewBox="0 0 24 24" stroke-width="1.5">
					<path stroke-linecap="round" stroke-linejoin="round"
						d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
				</svg>
			</button>
		</form>

		<div class="dropdown dropdown-end">
			<label tabindex="0" class="btn btn-ghost rounded-btn p-0"> <svg
					xmlns="http://www.w3.org/2000/svg"
					class="w-10 h-10 mx-2 stroke-slate-800 fill-none"
					viewBox="0 0 24 24" stroke-width="1.5">
						  <path stroke-linecap="round" stroke-linejoin="round"
						d="M17.982 18.725A7.488 7.488 0 0012 15.75a7.488 7.488 0 00-5.982 2.975m11.963 0a9 9 0 10-11.963 0m11.963 0A8.966 8.966 0 0112 21a8.966 8.966 0 01-5.982-2.275M15 9.75a3 3 0 11-6 0 3 3 0 016 0z" />
						</svg>
			</label>
			<ul tabindex="0"
				class="menu dropdown-content p-2 shadow bg-base-100 rounded-box w-52 mt-4 z-40">
				<li class="font-bold items-center mt-2">
					<%
					if (username != null) {
					%> <%=username%> <%
 } else {
 %>
					<button type="button" onclick="window.location.href='login.jsp';">Sign
						In / Register</button> <%
 }
 %>
					<div class="divider m-0 px-0"></div>
				</li>
				<li>
					<form action="CustomerPanelServlet" method="get">
						<input type="hidden" name="p" value="myProfile">
						<button type="submit">My Profile</button>
					</form>
				</li>
				<li>
					<form action="CustomerPanelServlet" method="get">
						<input type="hidden" name="p" value="changePasswordForm">
						<button type="submit">Change Password</button>
					</form>
				</li>
			</ul>
		</div>
	</div>
	<%
	} catch (Exception e) {
	String err = e.getMessage();
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
</body>