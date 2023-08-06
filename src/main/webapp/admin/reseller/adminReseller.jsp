<%@ page import="models.User"%>
<%@ page import="models.Address"%>
<%@ page import="java.util.List"%>
<%
@SuppressWarnings("unchecked")
List<User> users = (List<User>) request.getAttribute("users");
@SuppressWarnings("unchecked")
List<Address> addresses = (List<Address>) request.getAttribute("addresses");
%>
<%!int currentPage;
	String imageUrl;
	int totalPages;
	String search;%>
<%
try {
	search = request.getParameter("search");
	String pageNum = request.getParameter("page");
	String totalPageRaw = request.getAttribute("totalPages") != null
	? request.getAttribute("totalPages").toString()
	: "";
	currentPage = pageNum != null ? Integer.parseInt(pageNum) : 1;
	totalPages = totalPageRaw != null ? Integer.parseInt(totalPageRaw) : totalPages;
	if (totalPages == 0){
		totalPages = 1;
	}
} catch (Exception e) {
	totalPages = 1;
	currentPage = 1;
}
%>
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
	<div class="container-fluid mx-auto">
		<div class="flex flex-row">
			<%@include file="../adminSidePanel.jsp"%>
			<div class="w-10/12 grid">
				<h1 class="text-2xl font-bold mb-4 ms-5">View reseller</h1>
				<button class="btn" onclick="add_reseller_modal.showModal()">Add
					New reseller</button>
				<div class="flex ms-24 mt-10 w-1/2">

					<form action="<%=request.getContextPath() + "/admin/reseller"%>" method="get" class="w-2/3">
						<input type="text" name="search" placeholder="Search reseller"
							class="input input-bordered w-full max-w-xs" /> <input
							type="submit" value="Search" class="btn btn-primary mt-2">
					</form>
				</div>
				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/reseller?page=" + (currentPage - 1)%><%=search != null ? ("&search=" + search) : ""%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/reseller?page=" + (currentPage + 1)%><%=search != null ? ("&search=" + search) : ""%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
				<div class="container mx-auto mt-5">
					<table class="table table-zebra">
						<thead>
							<tr>
								<th class="text-center">Reseller ID</th>
								<th class="text-center">Reseller Name</th>
								<th class="text-center">Email</th>
								<th class="text-center">Phone</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (User user : users) {
							%>
							<tr>
								<td class="">

									<div class="font-bold"><%=user.getUserId()%></div>
								</td>
								<td class="text-center"><%=user.getName()%></td>
								<td class="text-center"><%=user.getEmail()%></td>
								<td class="text-center"><%=user.getPhone()%></td>
								<td class="text-center">
									<button class="btn"
										onclick="handleShowModal(<%=user.getUserId()%>)">Show</button>
									<button class="btn"
										onclick="handleEditModal(<%=user.getUserId()%>)">Edit</button>
									<button class="btn"
										onclick="handleDeleteModal(<%=user.getUserId()%>)">Delete</button>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%@include file="adminAddResellerModal.jspf"%>

				<%@include file="adminEditResellerModal.jspf"%>

				<%@include file="adminShowResellerModal.jspf"%>

				<%@include file="adminDeleteResellerModal.jspf"%>

				<div class="flex justify-center my-5">
					<div class="join">
						<a
							href="<%=request.getContextPath() + "/admin/reseller?page=" + (currentPage - 1)%><%=search != null ? ("&search=" + search) : ""%>"
							class="join-item btn <%=currentPage == 1 ? "btn-disabled" : ""%>">«</a>
						<a href="#" class="join-item btn">Page <%=currentPage%></a> <a
							href="<%=request.getContextPath() + "/admin/reseller?page=" + (currentPage + 1)%><%=search != null ? ("&search=" + search) : ""%>"
							class="join-item btn <%=currentPage == totalPages ? "btn-disabled" : ""%>">»</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../../errorHandler.jsp"%>
	<script>
	function handleDeleteModal(userId){
		const deleteUserId = document.getElementById("delete_user_id");
		<%for (User user : users) {%>
		if ("<%=user.getUserId()%>" === userId.toString()) {
			deleteUserId.value = <%=user.getUserId()%>
			delete_reseller_modal.showModal()
			return;
		}
<%}%>
		
	}
	function handleShowModal(userId) {
	    const showName = document.getElementById("show_name");
	    const showEmail = document.getElementById("show_email");
	    const showPhone = document.getElementById("show_phone");
	    const showAddressDiv = document.getElementById("show_address_div")
	    var show_address = document.getElementById('show_address');
	    var show_address2 = document.getElementById('show_address2');
	    var show_district = document.getElementById('show_district');
	    var show_country = document.getElementById('show_country');
	    var show_city = document.getElementById('show_city');
	    var show_postal_code = document.getElementById('show_postal_code');
	    var show_phone_address = document.getElementById('show_phone_address');

	    var addressMap = {};
	    <%for (Address address : addresses) {%>
	        addressMap["<%=address.getAddressId()%>"] = {
	            address: "<%=address.getAddress()%>",
	            address2: "<%=address.getAddress2()%>",
	            district: "<%=address.getDistrict()%>",
	            country: "<%=address.getCountry()%>",
	            city: "<%=address.getCity()%>",
	            postalCode: "<%=address.getPostalCode()%>",
	            phone: "<%=address.getPhone()%>"
	        }
	    <%}%>
	    showAddressDiv.style.display = "block"
	    <%for (User user : users) {%>
	        if ("<%=user.getUserId()%>" === userId.toString()) {
	            showName.innerHTML = "<%=user.getName()%>";
	            showEmail.innerHTML = "<%=user.getEmail()%>";
	            showPhone.innerHTML = "<%=user.getPhone()%>";
	            if ("<%=user.getAddressId()%>" == "null" || "<%=user.getAddressId()%>" == "0"){
	                showAddressDiv.style.display = "none"
	            } else {
	                var address = addressMap["<%=user.getAddressId()%>"];
	                if (address) {
	                    show_address.innerHTML = address.address;
	                    show_address2.innerHTML = address.address2;
	                    show_district.innerHTML = address.district;
	                    show_country.innerHTML = address.country;
	                    show_city.innerHTML = address.city;
	                    show_postal_code.innerHTML = address.postalCode;
	                    show_phone_address.innerHTML = address.phone;
	                }
	            }
	            show_reseller_modal.showModal()
	            
	            return;
	        }
	    <%}%>
	}

	function handleEditModal(userId){
		const editName = document.getElementById("edit_name");
		const editEmail = document.getElementById("edit_email");
		const editPhone = document.getElementById("edit_phone");
		const editUserId = document.getElementById("edit_user_id")
		const editAddressId = document.getElementById("edit_address_id")
		var edit_address = document.getElementById('edit_address');
	    var edit_address2 = document.getElementById('edit_address2');
	    var edit_district = document.getElementById('edit_district');
	    var edit_country = document.getElementById('edit_country');
	    var edit_city = document.getElementById('edit_city');
	    var edit_postal_code = document.getElementById('edit_postal_code');
	    var edit_phone = document.getElementById('edit_phone_address');
		<%for (User user : users) {%>
		if ("<%=user.getUserId()%>" === userId.toString()) {
			editName.value = "<%=user.getName()%>";
			editEmail.value = "<%=user.getEmail()%>";
			editPhone.value = "<%=user.getPhone()%>";
			editUserId.value = "<%=user.getUserId()%>";
			let addressId = "<%=user.getAddressId()%>"
			if (addressId == "null" || addressId == "0"){
				editAddressId.value = "-1"
				edit_address.value = '';
		        edit_address2.value = '';
		        edit_district.value = '';
		        edit_country.value = '';
		        edit_city.value = '';
		        edit_postal_code.value = '';
		        edit_phone.value = '';
			}else{
				editAddressId.value = addressId
			}
			
			edit_reseller_modal.showModal();
			return;
		}
<%}%>
	}
	
	function handleAddressSelection(selectElement) {
		var newAddressForm = document.getElementById("new_address_form");
		var add_address = document.getElementById('add_address');
		var add_address2 = document.getElementById('add_address2');
		var add_district = document.getElementById('add_district');
		var add_country = document.getElementById('add_country');
		var add_city = document.getElementById('add_city');
		var add_postal_code = document.getElementById('add_postal_code');
		var add_phone = document.getElementById('add_phone');
		if (selectElement.value === "0") {
			newAddressForm.style.display="block";
			add_address.removeAttribute('disabled');
			add_address2.removeAttribute('disabled');
			add_district.removeAttribute('disabled');
			add_country.removeAttribute('disabled');
			add_city.removeAttribute('disabled');
			add_postal_code.removeAttribute('disabled');
			add_phone.removeAttribute('disabled');
			add_address.value = '';
			add_address2.value = '';
			add_district.value = '';
			add_country.value = '';
			add_city.value = '';
			add_postal_code.value = '';
			add_phone.value = '';
		}else if (selectElement.value === "-1"){
			newAddressForm.style.display="none";
			add_address.value = '';
			add_address2.value = '';
			add_district.value = '';
			add_country.value = '';
			add_city.value = '';
			add_postal_code.value = '';
			add_phone.value = '';
			
		}else {
			newAddressForm.style.display="block";
			add_address.setAttribute('disabled', 'disabled');
			add_address2.setAttribute('disabled', 'disabled');
			add_district.setAttribute('disabled', 'disabled');
			add_country.setAttribute('disabled', 'disabled');
			add_city.setAttribute('disabled', 'disabled');
			add_postal_code.setAttribute('disabled', 'disabled');
			add_phone.setAttribute('disabled', 'disabled');
			<%for (Address address : addresses) {%>
			if ("<%=address.getAddressId()%>" === selectElement.value) {
				add_address.value = "<%=address.getAddress()%>";
				add_address2.value = "<%=address.getAddress2()%>";
				add_district.value = "<%=address.getDistrict()%>";
				add_country.value = "<%=address.getCountry()%>";
				add_city.value = "<%=address.getCity()%>";
				add_postal_code.value = "<%=address.getPostalCode()%>";
				add_phone.value = "<%=address.getPhone()%>";
				return;
			}
		<%}%>
		}
	}
	
	function handleEditAddressSelection(selectElement) {
	    var newAddressForm = document.getElementById("edit_address_form");
	    var edit_address = document.getElementById('edit_address');
	    var edit_address2 = document.getElementById('edit_address2');
	    var edit_district = document.getElementById('edit_district');
	    var edit_country = document.getElementById('edit_country');
	    var edit_city = document.getElementById('edit_city');
	    var edit_postal_code = document.getElementById('edit_postal_code');
	    var edit_phone = document.getElementById('edit_phone_address');
	    if (selectElement.value === "0") {
	        newAddressForm.style.display="block";
	        edit_address.removeAttribute('disabled');
	        edit_address2.removeAttribute('disabled');
	        edit_district.removeAttribute('disabled');
	        edit_country.removeAttribute('disabled');
	        edit_city.removeAttribute('disabled');
	        edit_postal_code.removeAttribute('disabled');
	        edit_phone.removeAttribute('disabled');
	        edit_address.value = '';
	        edit_address2.value = '';
	        edit_district.value = '';
	        edit_country.value = '';
	        edit_city.value = '';
	        edit_postal_code.value = '';
	        edit_phone.value = '';
	    } else if (selectElement.value === "-1") {
	        newAddressForm.style.display="none";
	        edit_address.value = '';
	        edit_address2.value = '';
	        edit_district.value = '';
	        edit_country.value = '';
	        edit_city.value = '';
	        edit_postal_code.value = '';
	        edit_phone.value = '';
	    } else {
	        newAddressForm.style.display="block";
	        edit_address.setAttribute('disabled', 'disabled');
	        edit_address2.setAttribute('disabled', 'disabled');
	        edit_district.setAttribute('disabled', 'disabled');
	        edit_country.setAttribute('disabled', 'disabled');
	        edit_city.setAttribute('disabled', 'disabled');
	        edit_postal_code.setAttribute('disabled', 'disabled');
	        edit_phone.setAttribute('disabled', 'disabled');
	        <%for (Address address : addresses) {%>
	        if ("<%=address.getAddressId()%>" === selectElement.value) {
	            edit_address.value = "<%=address.getAddress()%>";
	            edit_address2.value = "<%=address.getAddress2()%>";
	            edit_district.value = "<%=address.getDistrict()%>";
	            edit_country.value = "<%=address.getCountry()%>";
	            edit_city.value = "<%=address.getCity()%>";
	            edit_postal_code.value = "<%=address.getPostalCode()%>";
	            edit_phone.value = "<%=address.getPhone()%>";
	            return;
	        }
	        <%}%>
	    }
	}



	</script>
</body>