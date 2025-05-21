package chess.gui;

import chess.item.Stopwatch;
import chess.state.ChessState;
import chess.util.SceneSwitchHelper;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;
import chess.state.Position;
import org.tinylog.Logger;

public class ChessController {

    private int chessPiece = -1;
    @FXML
    private Label messageLabel;

    @FXML
    private GridPane grid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopwatchLabel;

    private FXMLLoader fxmlLoader;

    @Setter
    private String playerName;

    private BooleanProperty isSolved = new SimpleBooleanProperty();

    private IntegerProperty countOfSteps = new SimpleIntegerProperty();

    private ImageView[] pieceViews;

    private ChessState state;

    private Stopwatch stopwatch = new Stopwatch();

    private Instant startTime;

    @FXML
    private void initialize() {

        stepsLabel.textProperty().bind(countOfSteps.asString());
        stopwatchLabel.textProperty().bind(stopwatch.hhmmssProperty());
        isSolved.addListener(this::handleSolved);

        pieceViews = Stream.of("KING.png", "ROOK.png", "ROOK_TWO.png", "BISHOP.png", "BISHOP_TWO.png")
                .map(s -> "/images/" + s)
                .peek(s -> Logger.debug("Loading image resource {}", s))
                .map(Image::new)
                .map(ImageView::new)
                .toArray(ImageView[]::new);

        populateGrid();
        fxmlLoader = new FXMLLoader();


        Platform.runLater(() -> messageLabel.setText(String.format("Good luck, %s!", playerName)));
        resetGame();
        Logger.info("Loading Game");
    }

    private void populateGrid() {
        for (int row = 0; row < grid.getRowCount(); row++) {
            for (int col = 0; col < grid.getColumnCount(); col++) {
                final var square = new StackPane();
                square.getStyleClass().add("square");
                square.getStyleClass().add((row + col) % 2 == 0 ? "light" : "dark");
                square.setOnMouseClicked(this::handleMouseClick);
                grid.add(square, col, row);
            }
        }
    }

    private void resetGame() {

        state = new ChessState();
        countOfSteps.set(0);

        startTime = Instant.now();
        if (stopwatch.getStatus() == Animation.Status.PAUSED) {
            stopwatch.reset();
        }
        stopwatch.start();
        messageLabel.setText(String.format(""));
        clearState();
        showState();
    }

    private void clearState() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                getGridNodeAtPosition(grid, row, col)
                        .ifPresent(node -> ((StackPane) node).getChildren().clear());
            }
        }
    }
    private void showState() {
        clearState();
        for (int i = 0; i < 5; i++) {
            final var position = state.getPosition(i);
            final var pieceView = pieceViews[i];
            getGridNodeAtPosition(grid, position)
                    .ifPresent(node -> ((StackPane) node).getChildren().add(pieceView));
        }
    }

    private static Optional<Node> getGridNodeAtPosition(
            @NonNull final GridPane gridPane,
            @NonNull final Position pos) {

        return getGridNodeAtPosition(gridPane, pos.getRow(), pos.getCol());
    }

    private static Optional<Node> getGridNodeAtPosition(
            @NonNull final GridPane gridPane,
            final int row,
            final int col) {

        return gridPane.getChildren().stream()
                .filter(child -> GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col)
                .findFirst();
    }

    private void handleSolved(ObservableValue<? extends Boolean> observableValue, boolean oldValue, boolean newValue) {
        if (newValue) {
            Logger.info("Player {} has solved the game in {} steps", playerName, countOfSteps.get());
            stopwatch.stop();
            messageLabel.setText(String.format("Won, %s!", playerName));
        }
    }

    @FXML
    private void handleMouseClick(@NonNull final MouseEvent event) throws NoSuchElementException {

        final var source = (Node) event.getSource();
        final var row = GridPane.getRowIndex(source);
        final var col = GridPane.getColumnIndex(source);

        Logger.debug("Click on square ({},{})", row, col);

        if(!state.getChessStateListPosition().contains(new Position(row, col))) {
            if(chessPiece == -1) {
                Logger.warn("Is Empty!");
                return;}
            
            if(getDirectionFromClickPosition(row, col,chessPiece).isPresent()){
            performMove(getDirectionFromClickPosition(row, col,chessPiece).get(),chessPiece);
            }
            chessPiece = -1;
        }
        else if(state.getChessStateListPosition().contains(new Position(row, col)))
        {
            chessPiece= getPiece(row,col);
        }



    }

    private int getPiece(int row, int col) {
        return state.getChessStateListPosition().indexOf(new Position(row, col));

    }

    private Optional<Position.Direction> getDirectionFromClickPosition(
            final int row,
            final int col, final int piece) {
        final var blockPosition = state.getPosition(piece);
        return Position.Direction.of(row - blockPosition.getRow(), col - blockPosition.getCol());
    }

    private void performMove(
            @NonNull final Position.Direction direction, final int piece) {

        if (state.checkMove(direction,piece)) {
            Logger.info("Move: {}", direction);

            state = state.doMove(direction,piece);
            Logger.trace("New state: {}", state);

            countOfSteps.set(countOfSteps.get() + 1);

            showState();
            if (state.checkWin()) {
                isSolved.set(true);
                Logger.info("Won");
            }
        } else {
            Logger.warn("Invalid move: {}", direction);
        }
    }

    public void handleResetButton(ActionEvent actionEvent) {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Logger.info("Restart game");
        stopwatch.stop();
        resetGame();
    }

    public void handleFinishButton(ActionEvent actionEvent) throws IOException {
        final var buttonText = ((Button) actionEvent.getSource()).getText();
        Logger.debug("{} is pressed", buttonText);
        Logger.debug("Opening FirstController");
        SceneSwitchHelper.ShowFxml(fxmlLoader,"/Fxml/first.fxml",(Stage) ((Node) actionEvent.getSource()).getScene().getWindow());

    }


}
