package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFormController {

    public Label lblTime;
    public Label lblDate;
    public AnchorPane root;

    public void initialize() throws IOException {
        initUI("DefaultForm.fxml");
        generateDateTime();
    }

    private void generateDateTime() {
        lblDate.setText(LocalDate.now().toString());

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initUI(String location) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/"+location)));
    }

    public void imgHomeOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("DefaultForm.fxml");
    }

    public void imgClientOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("ClientForm.fxml");
    }

    public void imgOrderOnAction(MouseEvent mouseEvent) {
    }

    public void imgBoatOnAction(MouseEvent mouseEvent) {
    }

    public void imgPurchaseOnAction(MouseEvent mouseEvent) {
    }

    public void imgDOrderOnAction(MouseEvent mouseEvent) {
    }

    public void btnClientOnAction(ActionEvent actionEvent) throws IOException {
        initUI("ClientForm.fxml");
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnPurchaseOnAction(ActionEvent actionEvent) {
    }

    public void btnReportOnAction(ActionEvent actionEvent) {
    }

    public void btnDOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnBoatOnAction(ActionEvent actionEvent) {
    }

    public void imgExitOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void btnDriverOnAction(ActionEvent actionEvent) throws IOException {
        initUI("DriverForm.fxml");
    }

    public void imgDriverOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("DriverForm.fxml");
    }
}
