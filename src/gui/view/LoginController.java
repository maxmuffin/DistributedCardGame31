package gui.view;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import distributedLogic.game.PlayerLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField serverIP;

    @FXML
    private Label statusLabel;

    @FXML
    private Button startButton;

    private Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.startButton.setDefaultButton(true);

        this.username.setText("Player");

        try {
            this.serverIP.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.alert = new Alert(Alert.AlertType.ERROR);
        this.alert.setTitle("Information Dialog");
        this.alert.setHeaderText(null);
    }


    @FXML
    private void buttonPlay(ActionEvent event) {
        if (this.username.getText().isEmpty() || this.serverIP.getText().isEmpty()) {
            this.alert.setContentText("You don't add a username or serverIP!");
            this.alert.showAndWait();
        } else {
            setDisable();
            String playerUsername = username.getText();
            String serverAddress = serverIP.getText();

            PlayerLogic playerLogic = new PlayerLogic(playerUsername, serverAddress, this);
            playerLogic.startClient(event);
        }
    }


    private void setDisable() {
        this.username.setDisable(true);
        this.serverIP.setDisable(true);
        this.startButton.setDisable(true);
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Alert getAlert() {
        return alert;
    }

}
