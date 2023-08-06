package models;

import java.math.BigDecimal;
public class Book {
    private int bookId;
    private String title;
    private int authorId;
    private String authorName;
    private BigDecimal price;
    private int quantity;
    private String publicationDate;
    private String ISBN;
    private int rating;
    private String description;
    private int publisherId;
    private String publisherName;
    private int genreId;
    private String genreName;
    private String imageUrl;
    private int totalQuantitySold;

    public int getTotalQuantitySold() {
		return totalQuantitySold;
	}

	public void setTotalQuantitySold(int totalQuanatitySold) {
		this.totalQuantitySold = totalQuanatitySold;
	}

	// Constructor
    public Book() {
    }
    
    //Methods
    
    //Wow, such empty

    // Setters
    

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    // Getters
    public String getImageUrl() {
        return imageUrl;
    }
    
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getGenreId() {
        return genreId;
    }
}

