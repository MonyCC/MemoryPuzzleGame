package application.model;
public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String photoPath;

    public User(String username, String passwordHash, String photoPath) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.photoPath = photoPath;
    }

    public User(int id, String username, String passwordHash, String photoPath) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
