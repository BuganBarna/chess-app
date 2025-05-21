package chess.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import org.tinylog.Logger;
import java.io.IOException;
import java.util.Objects;

/**
 * Grafikus felület indítását végző osztály
 */
public class GameApplication extends Application {
    /**
     * Grafikus felület indítását végző metódus
     * @param stage asztali alkalmazás ablakja
     * @throws IOException bemenet,kimenet kivétel
     */
    @Override
    public void start(
            @NonNull final Stage stage) throws IOException {
        Logger.info("Starting Mini Chess");
        stage.setTitle("Mini Chess");
        stage.setResizable(false);
        final var scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/first.fxml"))));
        stage.setScene(scene);
        stage.show();
    }



}
