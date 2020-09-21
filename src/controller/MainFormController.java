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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainFormController {

    public Label lblTime;
    public Label lblDate;
    public AnchorPane root;

    public void initialize() throws IOException {
        initUI("DefaultForm.fxml");
        generateDateTime();
    }

    private void generateDateTime() {
        /*lblDate.setText(LocalDate.now().toString());*/
        Date date = Calendar.getInstance().getTime(); // OR new Date()

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");

        String formatStr = dateFormat.format(date);

        lblDate.setText(formatStr);

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

    public void imgBoatOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("BoatForm.fxml");
    }

    public void imgDOrderOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("DeliveryOrderForm.fxml");
    }

    public void btnClientOnAction(ActionEvent actionEvent) throws IOException {
        initUI("ClientForm.fxml");
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        initUI("SeaFoodForm.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        initUI("OrderForm.fxml");
    }

    public void btnPurchaseOnAction(ActionEvent actionEvent) throws IOException {
        initUI("PurchaseForm.fxml");
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        initUI("ReportForm.fxml");
    }

    public void btnDOrderOnAction(ActionEvent actionEvent) throws IOException {
        initUI("DeliveryOrderForm.fxml");
    }

    public void btnBoatOnAction(ActionEvent actionEvent) throws IOException {
        initUI("BoatForm.fxml");
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

    public void imgSeaFoodOnAction(MouseEvent mouseEvent) throws IOException {
        initUI("SeaFoodForm.fxml");
    }
}