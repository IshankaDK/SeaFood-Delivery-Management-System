package controller;

import bo.BoFactory;
import bo.custom.DeliveryOrderBo;
import bo.custom.DriverBo;
import com.jfoenix.controls.*;
import dto.ClientDTO;
import dto.DeliveryOrderDTO;
import dto.DriverDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String arrivalTime = ArrivalTimePicker.getValue().toString();
        String status = "Done";
        try {
            boolean isSaved = bo.updateDO(new DeliveryOrderDTO(txtDoId.getText().trim(),arrivalTime,status),
                    (txtOrderId.getText().trim()));
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Updated", ButtonType.OK).show();
                clear();
            }else {
                System.out.println(arrivalTime);
                new Alert(Alert.AlertType.WARNING,
                        "Not Updated", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDriverCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<DriverDTO> arrayList = driverBo.getAllDriver();
        for (DriverDTO s : arrayList) {
            observableList.add(s.getId());

        }
        cmbDriverId.setItems(observableList);
    }

    public void cmbDriverIdOnAction(ActionEvent actionEvent) throws Exception {
        DriverDTO dto = driverBo.getDriver(String.valueOf(cmbDriverId.getValue()));
        if(dto != null){
            txtDriverName.setText(dto.getName());
            txtDriverContact.setText(dto.getContact());
            txtDriverAddress.setText(dto.getAddress());
            DepartureTimePicker.requestFocus();
        }
    }

    private void loadId() throws Exception {
        String id = bo.getDoId();
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
            new Alert(Alert.AlertType.INFORMATION,
                    "No Order Found!, Check OrderId Again", ButtonType.OK).show();
                     txtOrderId.setStyle("-fx-border-color: #f53b57 ");
                     txtOrderId.requestFocus();
        }
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Error", ButtonType.OK).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        String doId = txtDoId.getText().trim();
        String orId = txtOrderId.getText().trim();
        String driverId = String.valueOf(cmbDriverId.getValue());
        String departure = String.valueOf(DepartureTimePicker.getValue());
        String arrival = "00:00:00";
        String fee = txtDeliveryFee.getText().trim();
        String status = "On the Way";

        try {
            boolean isSaved = bo.saveDO(new DeliveryOrderDTO(doId,orId,driverId,departure,arrival,fee,status));
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,
                        "Saved", ButtonType.OK).show();
                clear();
            }else {
                new Alert(Alert.AlertType.WARNING,
                        "Not Saved", ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,
                    "Not Saved", ButtonType.OK).show();
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
                new Alert(Alert.AlertType.WARNING,
                        "No Delivery Order Found, Please Check ID Again", ButtonType.OK).show();
                        txtDoId.setStyle("-fx-border-color: #f53b57 ");
                        txtDoId.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void clear() throws Exception {
        txtOrderId.setText(null);
        txtClientId.setText(null);
        txtClientName.setText(null);
        txtClientAddress.setText(null);
        txtClientContact.setText(null);
        cmbDriverId.setValue(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtDriverContact.setText(null);
        DepartureTimePicker.setValue(null);
        txtDeliveryFee.setText("250.00");
        txtStatus.setText("In Progress");
        ArrivalTimePicker.setValue(null);
        loadId();
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
}
