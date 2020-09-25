package controller;

import bo.BoFactory;
import bo.custom.DeliveryOrderBo;
import bo.custom.DriverBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.DriverDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderFormController {
    public AnchorPane root;
    public JFXTextField txtDriverContact;
    public JFXTextField txtOrderId;
    public JFXTextField txtDoId;
    public TextField txtDeparture;
    public TextField txtDeliveryFee;
    public TextField txtStatus;
    public TableView tblDo;
    public TextField txtArrival;
    public JFXComboBox cmbDriverId;
    public JFXTextField txtDriverName;
    public JFXTextField txtClientName;
    public JFXTextField txtClientAddress;
    public JFXTextField txtClientContact;


    DeliveryOrderBo bo;
    DriverBo driverBo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.DELIVERY);
        driverBo = BoFactory.getInstance().getBo(BoFactory.BOType.DRIVER);
        loadDriverCombo();
        loadId();
    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnAddtoTableOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
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
        }
    }

    public void btnSaveAllOnAction(ActionEvent actionEvent) {
    }

    private void loadId() throws Exception {
        String id = bo.getDoId();
        txtDoId.setText(id);
    }
}
