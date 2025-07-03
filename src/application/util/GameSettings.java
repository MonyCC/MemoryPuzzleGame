package application.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

import application.engine.ScoreManager;
import application.model.User;

public class GameSettings {
    public static String selectedCategory ;  
    public static int selectedLevel = 1;               
    public static int rows;
    public static int cols;
    public static final int MAX_LEVEL = 9;
    public static ScoreManager lastScore;
    public static boolean lastWin;
    public static User loggedInUser;
    public static boolean pvpSwap;
    public static int score_add ;
    public static int score_punish;
    public static int coins_gains;
    public static String PvPOrLevelChoice;

    public static Map<Integer, String> LEVEL_SCENES = Map.of(
        1, "/application/fxml/Level1.fxml",
        2, "/application/fxml/Level2.fxml",
        3, "/application/fxml/Level3.fxml",
        4,"/application/fxml/Level4.fxml",
        5, "/application/fxml/Level5.fxml",
        6, "/application/fxml/Level6.fxml",
        7, "/application/fxml/Level7.fxml",
        8,"/application/fxml/Level8.fxml",
        9,"/application/fxml/Level8.fxml"
    );


    public static final String LOGIN = "/application/fxml/login.fxml";
    public static final String GAME = "/application/fxml/GameView.fxml";
    public static final String WIN = "/application/fxml/WinScreen.fxml";
    public static final String LOSE = "/application/fxml/LoseScreen.fxml";
    public static final String SETTINGS = "/application/fxml/Setting.fxml";
    public static final String REGISTER = "/application/fxml/register.fxml";
    public static final String HOME = "/application/fxml/HomeView.fxml";
    public static final String LEVELSCREEN = "/application/fxml/LevelScreen.fxml";
    public static final String OPTION = "/application/fxml/OptionView.fxml";
    public static final String USERINFO = "/application/fxml/UserInfo.fxml";
    public static final String LEADERBOARD = "/application/fxml/LeaderboardView.fxml";
    public static final String PVPOPTION = "/application/fxml/PvPOptionView.fxml";
    public static final String PVPGAME = "/application/fxml/PvPGame.fxml";
    public static final String SHOP = "/application/fxml/Shop.fxml";
    // public static final String ONLINE = "/application/fxml/PlayOnline.fxml";

     private static String logoPath;
    private static String audioPath;

    public static String getLogoPath() {
        if (logoPath == null) {
            logoPath = getResourcePath("src/resources/assets/images/logo.png");
        }
        return logoPath;
    }

    public static String getAudioPath() {
        if (audioPath == null) {
            audioPath = getResourcePath("src/resources/assets/audio/");
        }
        return audioPath;
    }

    public static String getResourcePath(String relativeResourcePath) {
        if (relativeResourcePath.startsWith("file:/")) {
            return relativeResourcePath; // already a URI
        }

        String path = Paths.get(System.getProperty("user.dir"), relativeResourcePath).toString();
        File file = new File(path);
        if (file.exists()) {
            return file.toURI().toString();
        }

        throw new IllegalArgumentException("Resource not found in classpath or file system: " + relativeResourcePath);
    
    }



}

