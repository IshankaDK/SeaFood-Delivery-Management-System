package controller;

import bo.BoFactory;
import bo.custom.ClientBo;
import bo.custom.OrderBo;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ClientDTO;
import dto.SeaFoodDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class OrderFormController {
    public AnchorPane root;
    public TableView tblOrder;
    public JFXTextField txtOrderId;
    public JFXTextField txtOrderDate;
    public JFXComboBox cmbClientId;
    public JFXTextField txtClientName;
    public JFXTextField txtClientAddress;
    public JFXComboBox cmbCode;
    public TextField txtQty;
    public Label lblTotal;
    public JFXButton btnAdd;
    public JFXButton btnPlaceOrder;
    public JFXButton btnPrintBill;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnRemove;
    public TextField txtDiscount;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;

    OrderBo bo;
    ClientBo clientBo;
    SeaFoodBo seaFoodBo;
    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.ORDER);
        clientBo = BoFactory.getInstance().getBo(BoFactory.BOType.CLIENT);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        loadId();
        txtOrderDate.setText(LocalDate.now().toString());
        loadClientCombo();
        loadSeaFoodCombo();
    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void loadClientCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<ClientDTO> arrayList = clientBo.getAllClient();
        for (ClientDTO dto : arrayList) {
            observableList.add(dto.getId());

        }
        cmbClientId.setItems(observableList);
    }

    public void loadSeaFoodCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> arrayList = seaFoodBo.getAllSeaFood();
        for (SeaFoodDTO dto : arrayList) {
            observableList.add(dto.getCode());

        }
        cmbCode.setItems(observableList);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) {
    }

    private void loadId() throws Exception {
        String id = bo.getOrderId();
        txtOrderId.setText(id);

    }

    public void cmbClientIdOnAction(ActionEvent actionEvent) throws Exception {
        ClientDTO dto = clientBo.getClient(String.valueOf(cmbClientId.getValue()));
        if(dto != null){
            txtClientName.setText(dto.getName());
            txtClientAddress.setText(dto.getAddress());

            cmbCode.requestFocus();
        }
    }

    public void cmbCodeOnAction(ActionEvent actionEvent) throws Exception {
        SeaFoodDTO dto = seaFoodBo.getSeaFood(String.valueOf(cmbCode.getValue()));
        if(dto != null){
            txtDescription.setText(dto.getDescription());
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(dto.getSellingPrice()));


            txtQty.requestFocus();
        }
    }
}
