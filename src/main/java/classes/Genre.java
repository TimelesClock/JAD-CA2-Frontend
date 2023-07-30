package classes;

public class Genre {
    private int id;
    private String name;

    public Genre() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String genreName) {
        this.name = genreName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}