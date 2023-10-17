import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class InitializationController {
    @FXML
    private ComboBox<String> options;

    @FXML
    private void initialize(){
        ObservableList<String> optionsDropdown = FXCollections.observableArrayList(
                "", "Bot vs Bot", "Bot vs Human");
        this.options.setItems(optionsDropdown);
        this.options.getSelectionModel().select(0);
    }
    @FXML
    private void play() {
        String selectedOption = options.getSelectionModel().getSelectedItem();
        if ("Bot vs Human".equals(selectedOption)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InputFrame.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Input Frame");
                stage.setScene(new Scene(root));

                // Show the InputFrame.fxml
                stage.show();

                // Close the current stage (if needed)
                Stage currentStage = (Stage) options.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InputFrameBotVsBot.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Input Frame");
                stage.setScene(new Scene(root));

                // Show the InputFrame.fxml
                stage.show();

                // Close the current stage (if needed)
                Stage currentStage = (Stage) options.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
