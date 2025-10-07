
# Campus Navigation POC (Java Swing) — Advanced Demo

This demo adds:
- JSON-driven campus buildings (`assets/campus_buildings.json`)
- Click to select "From" and "To" then draw a route line
- Predefined demo: one-click route Library → Engineering
- A 5-second reminder (simulates class reminder)

## Run (IntelliJ)
- JDK: 17+
- Open this folder in IntelliJ (File → Open)
- Run `Main.main()`

## Run (CLI)
```bash
javac -d out -cp src src/Main.java src/model/Building.java src/data/DataLoader.java src/ui/MapPanel.java src/util/Reminder.java
java -cp out Main
```

## Files
```
src/
  Main.java
  model/Building.java
  data/DataLoader.java
  ui/MapPanel.java
  util/Reminder.java
assets/
  campus_buildings.json
```

## Demo steps
1) Click **Route: Library → Engineering** to show a red route line.
2) Click **Set 5s Reminder** to see a reminder pop up after 5 seconds.
3) Click on markers to manually select From / To; click **Clear Route** to reset.

## Notes
- Coordinates are simple panel positions (not GPS). Replace with your own data or extend for scaling.
- Uses `javax.json` for parsing. On some JDK setups, `javax.json` may be unavailable by default. If you hit a compile error, replace the loader with a minimal parser or add a JSON implementation dependency. For the simplest path, you can hardcode buildings in code.
