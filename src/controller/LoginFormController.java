package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane root;

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        if (Pattern.compile("^[A-z]{1,50}$").matcher(txtUserName.getText().trim()).matches()) {
            txtUserName.setStyle("-fx-border-color:  #45aaf2;");
            txtPassword.requestFocus();
        } else {
            txtUserName.setStyle("-fx-border-color:  #eb3b5a;");
            txtUserName.requestFocus();
        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        if (Pattern.compile("^[A-z]{1,50}$").matcher(txtPassword.getText().trim()).matches()) {
            txtPassword.setStyle("-fx-border-color:  #45aaf2;");
            this.root.getChildren().clear();
            this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml")));
        } else {
            txtPassword.setStyle("-fx-border-color:  #eb3b5a;");
            txtPassword.requestFocus();
        }

    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"))));

    }

    public void hyperNewAccountOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/RegistrationForm.fxml"))));
    }

    public void imgExitOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void imgMinimizeOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }
}


