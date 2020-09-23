package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    public ImageView minIcon;
    public JFXButton btnClient;
    public JFXButton btnSeaFood;
    public JFXButton btnPurchase;
    public JFXButton btnOrder;
    public JFXButton btnQuickOrder;
    public JFXButton btnDeliveryOrder;
    public JFXButton btnBoat;
    public JFXButton btnDriver;

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
    

    public void btnClientOnAction(ActionEvent actionEvent) throws IOException {
        initUI("ClientForm.fxml");
        DropShadow shadow = new DropShadow();
        btnClient.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnClient.setEffect(shadow);
                    }
                });

        btnClient.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        btnClient.setEffect(null);
                    }
                });
        btnClient.requestFocus();
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        initUI("SeaFoodForm.fxml");
        btnSeaFood.requestFocus();
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        initUI("OrderForm.fxml");
        btnOrder.requestFocus();
    }

    public void btnPurchaseOnAction(ActionEvent actionEvent) throws IOException {
        initUI("PurchaseForm.fxml");
        btnPurchase.requestFocus();
    }


    public void btnDOrderOnAction(ActionEvent actionEvent) throws IOException {
        initUI("DeliveryOrderForm.fxml");
        btnDeliveryOrder.requestFocus();
    }

    public void btnBoatOnAction(ActionEvent actionEvent) throws IOException {
        initUI("BoatForm.fxml");
        btnBoat.requestFocus();

    }

    public void imgExitOnAction(MouseEvent mouseEvent) { System.exit(0);
    }

    public void btnDriverOnAction(ActionEvent actionEvent) throws IOException {
        initUI("DriverForm.fxml");
        btnDriver.requestFocus();
    }

    public void btnQuickOrderOnAction(ActionEvent actionEvent) throws IOException {
        initUI("QuickOrderForm.fxml");
        btnQuickOrder.requestFocus();
    }

    public void imgMinimizeOnAction(MouseEvent mouseEvent) {
        Stage s = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

}
