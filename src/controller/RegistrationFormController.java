package controller;

import bo.BoFactory;
import bo.custom.LoginBo;
import com.jfoenix.controls.JFXButton;
import dto.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegistrationFormController {
    public AnchorPane root;
    public JFXButton btnSingUp;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtName;

    LoginBo bo;

    public void initialize(){
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.LOGIN);
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        String name = txtName.getText().trim();
        String userName = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        try {
            boolean isSaved = bo.saveRegistration(new LoginDTO(name,userName,password));
            if(isSaved){
                Notifications notificationBuilder = Notifications.create()
                        .title("Sign Up Successfully.!")
                        .text("You have Successfully Sign Up to the System.")
                        .graphic(new ImageView(new Image("/assert/done.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"))));
            }else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Sign Up Fail..!")
                        .text("Sign Up Fail.!, Please Try Again..!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Sign Up Fail..!")
                    .text("User Name Already Exist, Try Another User Name..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
    }

    public void hyperSingInOnAction(ActionEvent actionEvent)  {
        Stage stage = (Stage) root.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void imgExitOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void imgMinimizeOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z|0-9]{1,}$").matcher(txtUserName.getText().trim()).matches()){
            txtUserName.setStyle("-fx-border-color: #0fbcf9; -fx-border-width: 3");
            txtPassword.requestFocus();
        }else {
            txtUserName.setStyle("-fx-border-color: #f53b57; -fx-border-width: 3 ");
            txtUserName.requestFocus();
        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        if(Pattern.compile("^[A-z|1-9]{1,}$").matcher(txtPassword.getText().trim()).matches()){
            txtPassword.setStyle("-fx-border-color: #0fbcf9; -fx-border-width: 3 ");
            btnSingUp.requestFocus();
            btnSignUpOnAction(actionEvent);
        }else {
            txtPassword.setStyle("-fx-border-color: #f53b57; -fx-border-width: 3 ");
            txtPassword.requestFocus();
        }
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtName.getText().trim()).matches()){
            txtName.setStyle("-fx-border-color: #0fbcf9; -fx-border-width: 3 ");
            txtUserName.requestFocus();
        }else {
            txtName.setStyle("-fx-border-color: #f53b57; -fx-border-width: 3 ");
            txtName.requestFocus();
        }
    }
}
