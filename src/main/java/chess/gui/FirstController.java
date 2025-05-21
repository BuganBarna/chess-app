package chess.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;


public class FirstController {
    @FXML
    private TextField playerNameTextField;

    @FXML
    private Label errorLabel;

    private FXMLLoader fxmlLoader;

    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        fxmlLoader = new FXMLLoader();
        if (playerNameTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter your name!");
        }
        else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            fxmlLoader.setLocation(getClass().getResource("/Fxml/ui.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<ChessController>getController().setPlayerName(playerNameTextField.getText());
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("The user's name is set to {}, loading game scene", playerNameTextField.getText());
        }
    }
}
