package application.model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    //add SHA-256 hash password to secure the password
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));  // convert byte to hex
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    //check valid register
   public String validateRegistration(String confirmPassword) {
        if (username == null || username.length() < 6) {
            return "Username must be at least 6 characters.";
        }

        if (passwordHash == null || passwordHash.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (!passwordHash.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|~`].*")) {
            return "Password must contain at least one special character.";
        }

        if (passwordHash.contains(" ")) {
            return "Password must not contain spaces.";
        }

        if (!passwordHash.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        return null; // ✅ Everything is valid
    }
}
