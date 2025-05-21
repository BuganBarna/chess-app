package chess.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Nézet váltást segítő osztály
 */
public final class SceneSwitchHelper {
    /**
     * Adott nézet betöltése
     * @param fxmlLoader  fxml fájlok betöltésére és inicializálására szolgál.
     * @param resourceName fxml fájlneve
     * @param stage főablak
     * @throws IOException ha fxml file nem található
     */
    public static void ShowFxml(FXMLLoader fxmlLoader, String resourceName, Stage stage) throws IOException {
        Logger.trace("Loading FXML resource {}", resourceName);
        fxmlLoader.setLocation(fxmlLoader.getClass().getResource(resourceName));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}