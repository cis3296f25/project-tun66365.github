# Campus Navigation POC (Java Swing)

**What this demo shows**
- Simple "campus map" panel with two building markers
- A button that schedules a 5-second reminder (simulating a class reminder)

## How to run

### IntelliJ IDEA (recommended)
- JDK: 17+
- Open the project folder in IntelliJ
- Ensure `src/Main.java` exists
- Run `Main.main()`

### Command line
```bash
# compile
javac -d out src/Main.java

# run
java -cp out Main
```

## OS / Compiler (example used for testing)
- macOS / Windows 11
- Temurin/OpenJDK 17

## Next steps
- Load buildings from a JSON file
- Draw a route line between two points
- Optional: migrate to Android (Google Maps + notifications)