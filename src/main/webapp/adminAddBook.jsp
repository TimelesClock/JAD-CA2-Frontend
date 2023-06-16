<%@ page import="classes.Author"%>
<%@ page import="classes.Publisher"%>
<%@ page import="classes.Genre"%>
<%@ page import="java.util.*"%>
<%
@SuppressWarnings("unchecked")
List<Author> authors = (List<Author>) request.getAttribute("authors");
@SuppressWarnings("unchecked")
List<Publisher> publishers = (List<Publisher>) request.getAttribute("publishers");
@SuppressWarnings("unchecked")
List<Genre> genres = (List<Genre>) request.getAttribute("genres");
%>
<body>
	<h1 class="text-2xl font-bold mb-4 ms-5">Add New Book</h1>
	<div class="container mx-auto mt-5">
		<form action="AdminPanelServlet" method="post" class="card">
			<input type="hidden" name="action" value="addBook">
			<div class="card-body space-y-6">
				<div class="form-control">
					<label class="label"> <span class="label-text">Title</span>
					</label> <input type="text" name="title" class="input input-bordered"
						required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Author</span>
					</label> <select name="author_id" class="select select-bordered"
						onchange="handleAuthorSelection(this)" required>
						<option value="" disabled selected>Select the author</option>
						<option value="0">+ Add new Author</option>
						<%
						for (Author author : authors) {
						%>
						<option value="<%=author.getId()%>"><%=author.getName()%></option>
						<%
						}
						%>
					</select>
				</div>
				<div id="newAuthorForm" style="display: none;">
					<div class="form-control">
						<label class="label"> <span class="label-text">New
								Author Name</span>
						</label> <input type="text" name="new_author_name"
							class="input input-bordered">
					</div>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Price</span>
					</label> <input type="number" step="0.01" name="price"
						class="input input-bordered" required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Quantity</span>
					</label> <input type="number" name="quantity" class="input input-bordered"
						required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Publication
							Date</span>
					</label> <input type="date" name="publication_date"
						class="input input-bordered" required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">ISBN</span>
					</label> <input type="text" name="ISBN" class="input input-bordered"
						required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Rating</span>
					</label> <input type="number" name="rating" class="input input-bordered"
						required>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Description</span>
					</label>
					<textarea name="description" class="textarea textarea-bordered"
						required></textarea>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Publisher</span>
					</label> <select name="publisher_id" class="select select-bordered"
						onchange="handlePublisherSelection(this)" required>
						<option value="" disabled selected>Select the publisher</option>
						<option value="0">+ Add new Publisher</option>
						<%
						for (Publisher publisher : publishers) {
						%>
						<option value="<%=publisher.getId()%>"><%=publisher.getName()%></option>
						<%
						}
						%>
					</select>
				</div>
				<div id="newPublisherForm" style="display: none;">
					<div class="form-control">
						<label class="label"> <span class="label-text">New
								Publisher Name</span>
						</label> <input type="text" name="new_publisher_name"
							class="input input-bordered">
					</div>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Genre</span>
					</label> <select name="genre_id" class="select select-bordered"
						onchange="handleGenreSelection(this)" required>
						<option value="" disabled selected>Select the genre</option>
						<option value="0">+ Add new Genre</option>
						<%
						for (Genre genre : genres) {
						%>
						<option value="<%=genre.getId()%>"><%=genre.getName()%></option>
						<%
						}
						%>
					</select>
				</div>
				<div id="newGenreForm" style="display: none;">
					<div class="form-control">
						<label class="label"> <span class="label-text">New
								Genre Name</span>
						</label> <input type="text" name="new_genre_name"
							class="input input-bordered">
					</div>
				</div>
				<div class="form-control">
					<label class="label"> <span class="label-text">Image</span>
					</label> <input type="file" class="file-input file-input-bordered w-full">
				</div>
				<button type="submit" class="btn btn-primary">Insert</button>
			</div>
		</form>
	</div>
	<script>
		function handleAuthorSelection(selectElement) {
			var newAuthorForm = document.getElementById("newAuthorForm");
			if (selectElement.value === "0") {
				newAuthorForm.style.display = "block";
				document.getElementsByName("new_author_name")[0].required = true;
			} else {
				newAuthorForm.style.display = "none";
				document.getElementsByName("new_author_name")[0].required = false;
				document.getElementsByName("new_author_name")[0].value = "";
			}
		}

		function handlePublisherSelection(selectElement) {
			var newPublisherForm = document.getElementById("newPublisherForm");
			if (selectElement.value === "0") {
				newPublisherForm.style.display = "block";
				document.getElementsByName("new_publisher_name")[0].required = true;
			} else {
				newPublisherForm.style.display = "none";
				document.getElementsByName("new_publisher_name")[0].required = false;
				document.getElementsByName("new_publisher_name")[0].value = "";
			}
		}

		function handleGenreSelection(selectElement) {
			var newGenreForm = document.getElementById("newGenreForm");
			if (selectElement.value === "0") {
				newGenreForm.style.display = "block";
				document.getElementsByName("new_genre_name")[0].required = true;
			} else {
				newGenreForm.style.display = "none";
				document.getElementsByName("new_genre_name")[0].required = false;
				document.getElementsByName("new_genre_name")[0].value = "";
			}
		}
	</script>

</body>
