package application.model;

import javafx.beans.property.*;

public class LeaderboardEntry {
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty value = new SimpleStringProperty();
    private final StringProperty rank = new SimpleStringProperty();

    public LeaderboardEntry(String username, String value) {
        this.username.set(username);
        this.value.set(value);
    }

    public void setRank(int r) {
        this.rank.set(String.valueOf(r));
    }

    public StringProperty rankProperty() { return rank; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty valueProperty() { return value; }
}
