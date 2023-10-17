import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InputFrameBotVsBotController {
    public CheckBox isBotFirst;

    @FXML
    private ComboBox<String> bot1_algo;

    @FXML
    private ComboBox<String> bot2_algo;

    @FXML
    private TextField player1;

    @FXML
    private TextField player2;
    @FXML
    private ComboBox<String> numberOfRounds;
    @FXML
    private void initialize(){
        ObservableList<String> optionsDropdown = FXCollections.observableArrayList(
                "", "Local Search", "Minimax", "Genetic Algorithm");
        this.bot1_algo.setItems(optionsDropdown);
        this.bot1_algo.getSelectionModel().select(0);

        this.bot2_algo.setItems(optionsDropdown);
        this.bot2_algo.getSelectionModel().select(0);

        ObservableList<String> numberOfRoundsDropdown = FXCollections.observableArrayList(
                "", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
        this.numberOfRounds.setItems(numberOfRoundsDropdown);
        this.numberOfRounds.getSelectionModel().select(0);
    }
    @FXML
    private void reset(){
        this.player1.setText("");
        this.player2.setText("");
        this.numberOfRounds.getSelectionModel().select(0);
    }
    @FXML
    private void play() throws IOException {
        if (this.isInputFieldValidated()){
            // Close primary stage/input frame.
            Stage primaryStage = (Stage) this.player1.getScene().getWindow();
            primaryStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OutputFrame.fxml"));
            Parent root = loader.load();

            // Get controller of output frame and pass input including player names and number of rounds chosen.
            OutputFrameController outputFC = loader.getController();
            outputFC.getInput(this.player1.getText(), this.player2.getText(), this.numberOfRounds.getValue(), this.isBotFirst.isSelected(), this.bot1_algo.getValue(), this.bot2_algo.getValue(),false);

            // Open the new frame.
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Game Board Display");
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setResizable(true);
            secondaryStage.show();
            outputFC.start();
        }
    }
    /**
     * Return whether all input fields have been successfully validated or not.
     *
     * @return boolean
     *
     */
    private boolean isInputFieldValidated() {
        String playerX = this.player1.getText();
        String playerO = this.player2.getText();
        String roundNumber = this.numberOfRounds.getValue();

        if (playerX.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Player 1 name is blank.").showAndWait();
            return false;
        }

        if (playerO.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Player 2 name is blank.").showAndWait();
            return false;
        }

        if (playerX.equals(playerO)){
            new Alert(Alert.AlertType.ERROR, "Player 1 and Player 2 cannot have the same name.").showAndWait();
            return false;
        }

        if (roundNumber.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Number of rounds dropdown menu is blank.").showAndWait();
            return false;
        }

        return true;
    }
}
