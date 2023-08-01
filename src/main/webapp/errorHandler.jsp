<body>
	<div id="toast" class="toast toast-top toast-center hidden">
		<div id="alert" class="alert">
			<span></span>
		</div>
	</div>

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

</body>