package controller;

import bo.BoFactory;
import bo.custom.DeliveryOrderBo;
import bo.custom.DriverBo;
import com.jfoenix.controls.*;
import db.DBConnection;
import dto.ClientDTO;
import dto.DeliveryOrderDTO;
import dto.DriverDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class DeliveryOrderFormController {
    public AnchorPane root;
    public JFXTextField txtDriverContact;
    public  TextField txtOrderId;
    public JFXTextField txtDoId;
    public TextField txtDeliveryFee;
    public TextField txtStatus;
    public JFXComboBox cmbDriverId;
    public JFXTextField txtDriverName;
    public JFXTextField txtClientName;
    public JFXTextField txtClientAddress;
    public JFXTextField txtClientContact;
    public JFXTextField txtDriverAddress;
    public JFXTextField txtClientId;
    public JFXButton btnSave;
    public JFXTimePicker ArrivalTimePicker;
    public JFXTimePicker DepartureTimePicker;


    DeliveryOrderBo bo;
    DriverBo driverBo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.DELIVERY);
        driverBo = BoFactory.getInstance().getBo(BoFactory.BOType.DRIVER);
        loadDriverCombo();
        loadId();

        DepartureTimePicker.setValue(LocalTime.now());
        txtDeliveryFee.setText("250.00");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            String arrivalTime = ArrivalTimePicker.getValue().toString();
            String status = "Done";

            boolean isSaved = bo.updateDO(new DeliveryOrderDTO(txtDoId.getText().trim(),arrivalTime,status),
                    (txtOrderId.getText().trim()));
            if(isSaved){
                Notifications notificationBuilder = Notifications.create()
                        .title("Update Successful.!")
                        .text("You have Successfully Update Delivery Order.")
                        .graphic(new ImageView(new Image("/assert/done.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Update UnSuccessful.!")
                        .text("May be order was finished, or Please try Again.!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Update UnSuccessful.!")
                    .text("Delivery Order Not Updated, Please try Again..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
    }

    public void loadDriverCombo()  {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<DriverDTO> arrayList = null;
        try {
            arrayList = driverBo.getAllDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (DriverDTO s : arrayList) {
            observableList.add(s.getId());

        }
        cmbDriverId.setItems(observableList);
    }

    public void cmbDriverIdOnAction(ActionEvent actionEvent) {
        DriverDTO dto = null;
        try {
            dto = driverBo.getDriver(String.valueOf(cmbDriverId.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dto != null){
            txtDriverName.setText(dto.getName());
            txtDriverContact.setText(dto.getContact());
            txtDriverAddress.setText(dto.getAddress());
            DepartureTimePicker.requestFocus();
        }
    }

    private void loadId()  {
        String id = null;
        try {
            id = bo.getDoId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtDoId.setText(id);
    }

    public void txtOrderIdOnAction(ActionEvent actionEvent) {
        try {
            ClientDTO dto = bo.getClient(txtOrderId.getText().trim());
            if(dto != null) {
                txtClientId.setText(dto.getId());
                txtClientName.setText(dto.getName());
                txtClientAddress.setText(dto.getAddress());
                txtClientContact.setText(dto.getContact());
                txtOrderId.setStyle("-fx-border-color: #0fbcf9 ");
            } else{
                Notifications notificationBuilder = Notifications.create()
                        .title("No Order Found!")
                        .text("Order may be Done.!, If not Check OrderId Again")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                txtOrderId.setStyle("-fx-border-color: #f53b57 ");
                txtOrderId.requestFocus();
        }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Error..!")
                    .text("No Order Found!, Check OrderId Again")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            txtOrderId.setStyle("-fx-border-color: #f53b57 ");
            txtOrderId.requestFocus();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
        String doId = txtDoId.getText().trim();
        String orId = txtOrderId.getText().trim();
        String driverId = String.valueOf(cmbDriverId.getValue());
        String departure = String.valueOf(DepartureTimePicker.getValue());
        String arrival = "00:00:00";
        String fee = txtDeliveryFee.getText().trim();
        String status = "On the Way";

            boolean isSaved = bo.saveDO(new DeliveryOrderDTO(doId,orId,driverId,departure,arrival,fee,status));
            if(isSaved){
                Notifications notificationBuilder = Notifications.create()
                        .title("Saved Successfully.!")
                        .text("Delivery Order Successfully saved to the System.")
                        .graphic(new ImageView(new Image("/assert/done.png")))
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                btnPrintOnAction(actionEvent);
            }else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Saving UnSuccessful.!")
                        .text("Delivery Order Not saved. Please try again..!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle();
                        notificationBuilder.show();
            }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Something Wrong.!")
                    .text("Something went Wrong.!Please try again..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle();
            notificationBuilder.show();
           // e.printStackTrace();
        }
    }

    public void txtDoIdOnAction(ActionEvent actionEvent) {
        try {
            DeliveryOrderDTO dto = bo.searchDO(txtDoId.getText().trim());
            if(dto != null) {
                txtOrderId.setText(dto.getOrderID());
                cmbDriverId.setValue(dto.getDriverID());
                DepartureTimePicker.setValue(LocalTime.parse(dto.getDeparture()));
                ArrivalTimePicker.setValue(LocalTime.parse(dto.getArrival()));
                txtDeliveryFee.setText(dto.getFee());
                txtStatus.setText(dto.getStatus());
                txtDoId.setStyle("-fx-border-color: #0fbcf9 ");
            }else {
                Notifications notificationBuilder = Notifications.create()
                    .title("No Delivery Order Found.!")
                    .text("No Delivery Order Found, Please Check ID Again")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle();
                notificationBuilder.show();
                        txtDoId.setStyle("-fx-border-color: #f53b57 ");
                        txtDoId.requestFocus();
            }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Error..!")
                    .text("Error..!, Something Wrong..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle();
            notificationBuilder.show();
        }
    }

    public void txtDeliveryFeeOnAction(ActionEvent actionEvent) {
        if (Pattern.compile("^[\\d|.]{1,9}$").matcher(txtDeliveryFee.getText().trim()).matches()) {
            txtDeliveryFee.setStyle("-fx-border-color: #0fbcf9 ");
            txtStatus.requestFocus();
        } else {
            txtDeliveryFee.setStyle("-fx-border-color: #f53b57 ");
            txtDeliveryFee.requestFocus();
        }
    }

    public void txtStatusOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtStatus.getText().trim()).matches()){
            txtStatus.setStyle("-fx-border-color: #0fbcf9 ");
            btnSave.requestFocus();
            btnSaveOnAction(actionEvent);
        }else {
            txtStatus.setStyle("-fx-border-color: #f53b57 ");
            txtStatus.requestFocus();
        }
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        try {
            InputStream is = this.getClass().getResourceAsStream("/report/DeliverOrder.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(is);

            HashMap hm = new HashMap();
            hm.put("clientId", txtClientId.getText());
            hm.put("clientName", txtClientName.getText());
            hm.put("clientAddress", txtClientAddress.getText());
            hm.put("clientContact", txtClientContact.getText());
            hm.put("doId", txtDoId.getText());
            hm.put("orderId", txtOrderId.getText());
            hm.put("diparture", DepartureTimePicker.getValue().toString());
            hm.put("fee", txtDeliveryFee.getText());
            hm.put("driverId", cmbDriverId.getValue().toString());
            hm.put("driverName", txtDriverName.getText());
            hm.put("arrival", "");
            hm.put("accepted", "");
            hm.put("handed", "");

            JasperPrint jp = JasperFillManager.fillReport(jr, hm, DBConnection.getInstance().getConnection());

            JasperViewer.viewReport(jp, false);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtOrderId.setText(null);
        txtClientId.setText(null);
        txtClientName.setText(null);
        txtClientAddress.setText(null);
        txtClientContact.setText(null);
        cmbDriverId.setValue(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtDriverContact.setText(null);
        DepartureTimePicker.setValue(LocalTime.now());
        txtDeliveryFee.setText("250.00");
        txtStatus.setText("In Progress");
        ArrivalTimePicker.setValue(null);
        try {
            loadId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
