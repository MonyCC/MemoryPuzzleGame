package application.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import application.model.LevelConfig;

import java.util.List;

public class JSONUtil {

    private static InputStream getLevelsJsonStream() throws IOException {
        String rootPath = System.getProperty("user.dir"); // project root or working directory
        Path jsonPath = Paths.get(rootPath, "src","resources", "config", "levels.json");
        System.out.println("Loading levels from: " + jsonPath.toAbsolutePath());
        return Files.newInputStream(jsonPath);
    }

    /**
     * Load list of LevelConfig objects from levels.json.
     */
    public static List<LevelConfig> loadLevelConfigs() {
        try (InputStream is = getLevelsJsonStream();
             Reader reader = new InputStreamReader(is)) {
            Gson gson = new Gson();
            // Parse JSON array into List<LevelConfig>
            List<LevelConfig> levels = gson.fromJson(reader, new TypeToken<List<LevelConfig>>(){}.getType());
            return levels;
        } catch (IOException e) {
            System.err.println("Failed to load level configs. Check JSON file path.");
            e.printStackTrace();
            return null;
        }
    }
    public static LevelConfig getLevelConfig(int level) {
        List<LevelConfig> levels = loadLevelConfigs();
        if (levels == null) {
            System.err.println("Failed to load level configs. Check JSON file path.");
            return null;
        }
        return levels.stream().filter(l -> l.level == level).findFirst().orElse(null);
    }

}
