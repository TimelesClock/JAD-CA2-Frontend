<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
<script src="https://cdn.tailwindcss.com"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.0.20/dist/full.css"
	rel="stylesheet">
</head>
<body>
	<script>
        window.onload = function() {
            var toast = document.getElementById("toast");
            var alert = document.getElementById("alert");
            var toastTimeout;

            <%String success = (String) request.getParameter("success");
if (success != null) {%>

            toast.classList.remove("hidden");
            alert.classList.add("alert-success");
            toast.querySelector("span").textContent = "<%=success%>";


            toastTimeout = setTimeout(function() {
                toast.classList.add("hidden");
                history.replaceState({}, document.title, location.pathname + location.search.replace(/[?&](success|err)=[^&]+/gi, ''));
            }, 5000);
            <%} else {%>
            <%String err = (String) request.getParameter("err");
	if (err != null) {%>

            toast.classList.remove("hidden");
            alert.classList.add("alert-error");
            toast.querySelector("span").textContent = "<%=err%>
		";

			toastTimeout = setTimeout(function() {
				toast.classList.add("hidden");
				history.replaceState({}, document.title, location.pathname
						+ location.search.replace(/[?&](success|err)=[^&]+/gi,
								''));
			}, 5000);
	<%}%>
		
	<%}%>
		}
	</script>

	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="adminSidePanel.jsp"%>
			<div class="w-10/12 grid">
				<%
				String content = request.getParameter("p");
				if (content != null && content.equals("addBook")) {
				%><%@include file="adminAddBook.jsp"%>
				<%
				} else if (content != null && content.equals("editBook")) {
				%><%@include file="adminEditBook.jsp"%>
				<%
				} else if (content != null && content.equals("deleteBook")) {
				%><%@include file="adminDeleteBook.jsp"%>
				<%
				} else if (content != null && content.equals("viewInventory")) {
				%><%@include file="adminViewInventory.jsp"%>
				<%
				}else if (content != null && content.equals("viewCustomer")) { %><%@include
					file="adminViewCustomer.jsp"%>
				<%
				} else if (content != null && content.equals("addCustomer")) {
				%><%@include file="adminAddCustomer.jsp"%>
				<%
				} else if (content != null && content.equals("editCustomer")) {
				%><%@include file="adminEditCustomer.jsp"%>
				<%
				} else if (content != null && content.equals("deleteCustomer")) {
				%><%@include file="adminDeleteCustomer.jsp"%>
				<%
				} else {
				%><h1 class="text-2xl font-bold place-self-center">Admin Panel</h1>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<div id="toast" class="toast toast-top toast-center hidden">
		<div id="alert" class="alert">
			<span></span>
		</div>
	</div>

</body>
</html>
