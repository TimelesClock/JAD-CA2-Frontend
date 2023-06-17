package classes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Base64;

public class Book {
    private int bookId;
    private String title;
    private int authorId;
    private BigDecimal price;
    private int quantity;
    private Timestamp publicationDate;
    private String ISBN;
    private int rating;
    private String description;
    private int publisherId;
    private int genreId;
    private byte[] image;

    // Constructor
    public Book() {
    }
    
    //Methods
    public String getImageBase64() {
    	if (image != null) {
    		return Base64.getEncoder().encodeToString(image);
    	}else {
    		return "";
    	}
        
    }

    // Setters
    

    public void setImage(byte[] image) {
        this.image = image;
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

    public void setPublicationDate(Timestamp publicationDate) {
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
    public byte[] getImage() {
        return image;
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

    public Timestamp getPublicationDate() {
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

    public int getGenreId() {
        return genreId;
    }
}

