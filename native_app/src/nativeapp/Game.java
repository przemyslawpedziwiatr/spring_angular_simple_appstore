package nativeapp;

/**
 * Created by panpr on 19.11.2017.
 */
public class Game {
    public String title, description, version, icon, screenshot;

    public Game(String title, String description, String version, String icon, String screenshot) {
        this.title = title;
        this.description = description;
        this.version = version;
        this.icon = icon;
        this.screenshot = screenshot;
    }
}
