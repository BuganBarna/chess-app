package chess.item;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Stopperórát reprezentáló osztály
 */
public final class Stopwatch {

    private LongProperty seconds = new SimpleLongProperty();
    private StringProperty hhmmss = new SimpleStringProperty();
    private Timeline timeline;


    /**
     * {@code Stopwatch} stopperóra pédányosítása
     */
    public Stopwatch() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> seconds.set(seconds.get() + 1)),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        hhmmss.bind(Bindings.createStringBinding(() -> DurationFormatUtils.formatDuration(seconds.get() * 1000, "HH:mm:ss"), seconds));
    }


    /**
     * {@return tulajdonság az eltelt másodpercek számának eléréséhez}
     */
    public LongProperty secondsProperty() {
        return seconds;
    }


    /**
     * {@return eltetl idő vissza adása {@code hh:mm:ss} alakban}
     */
    public StringProperty hhmmssProperty() {
        return hhmmss;
    }


    /**
     * stopperóra indítása
     */
    public void start() {
        timeline.play();
    }


    /**
     * stopperóra megállítása
     */
    public void stop() {
        timeline.pause();
    }


    /**
     * Visszaállítja a stopperórát de előbb {@link #stop()} metódusssal megállítja
     *
     * @throws IllegalStateException ha nem áll meg a stopperóra
     */
    public void reset() {
        if (timeline.getStatus() != Animation.Status.PAUSED) {
            throw new IllegalStateException();
        }
        seconds.set(0);
    }


    /**
     * {@return stopperóra jelenlegi állapota}
     */
    public Animation.Status getStatus() {
        return timeline.getStatus();
    }

}
