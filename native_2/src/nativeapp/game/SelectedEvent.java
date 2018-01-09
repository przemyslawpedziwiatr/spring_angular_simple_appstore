package nativeapp.game;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import nativeapp.game.Game;

/**
 * Created by panpr on 06.12.2017.
 */
public class SelectedEvent extends Event {
    private Game game;

    public SelectedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public SelectedEvent(EventType<? extends Event> eventType, Game game) {
        super(eventType);
        this.game = game;
    }

    public SelectedEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }

    public Game getGame() {
        return game;
    }
}
