import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.fxml.FXML;


 // Controller class handles the user interactions in the Connect Four game.
public class Controller {

    @FXML
    private GridPane boardPane;

    private final Board board = new Board();
    private boolean playerOneTurn = true;


      // Handles the click event for the player.
    @FXML
    public void handlePlayerClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String text = btn.getText();

        if (text.equals("CLEAR")) {
            cleanPane();
            return;
        }

        int col = Integer.parseInt(text);

        if (board.isFull(col)) {
            showFullWarning(col);
            return;
        }

        Board.Player currentPlayer = getCurrentPlayer();
        int row = board.placePiece(col, currentPlayer);
        addPieceToBoard(row, col, currentPlayer);

        if (board.checkWin(row, col, currentPlayer)) {
            showWinMessage(currentPlayer);
            cleanPane();
        } else {
            switchPlayer();
        }
    }


    // Clears the game board.
    @FXML
    public void cleanPane() {
        boardPane.getChildren().removeIf(node -> "gamePiece".equals(node.getUserData()));
        board.reset();
        resetPlayerTurn();
    }


      // Displays a warning message when the selected column is full.
    private void showFullWarning(int col) {
        showAlert(AlertType.WARNING, "Column Full", "Column " + col + " is full! Please select a different column.");
    }


    // Displays a win message when a player wins.
    private void showWinMessage(Board.Player player) {
        showAlert(AlertType.INFORMATION, "Game Over", player + " wins!");
    }


      // Adds a piece to the board visually.
    private void addPieceToBoard(int row, int col, Board.Player player) {
        Circle circle = new Circle(20, player == Board.Player.PLAYER_ONE ? Color.BLUE : Color.RED);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(circle);
        stackPane.setUserData("gamePiece");
        boardPane.add(stackPane, col - 1, row);  // Adjust col to 0-based index
    }


     // Switches the turn to the next player.
    private void switchPlayer() {
        playerOneTurn = !playerOneTurn;
    }


    // Resets the turn to Player One.
    private void resetPlayerTurn() {
        playerOneTurn = true;
    }


      // Gets the current player based on the turn.
    private Board.Player getCurrentPlayer() {
        return playerOneTurn ? Board.Player.PLAYER_ONE : Board.Player.PLAYER_TWO;
    }


     // Displays an alert with the specified type, title, and message.
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
