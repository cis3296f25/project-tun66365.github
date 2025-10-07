package model;

public class Building {
    private final String name;
    private final int x;
    private final int y;
    private final String hint;

    public Building(String name, int x, int y, String hint) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hint = hint;
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getHint() { return hint; }
}