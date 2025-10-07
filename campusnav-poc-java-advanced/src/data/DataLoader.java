package data;

import model.Building;
import java.nio.file.*;
import java.util.*;
import java.io.*;
import javax.json.*;

public class DataLoader {

    public static List<Building> loadBuildings(String path) throws Exception {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            JsonReader reader = Json.createReader(is);
            JsonArray arr = reader.readArray();
            List<Building> list = new ArrayList<>();
            for (JsonValue v : arr) {
                JsonObject o = v.asJsonObject();
                String name = o.getString("name", "Unknown");
                int x = o.getInt("x", 50);
                int y = o.getInt("y", 50);
                String hint = o.getString("hint", "");
                list.add(new Building(name, x, y, hint));
            }
            return list;
        }
    }
}