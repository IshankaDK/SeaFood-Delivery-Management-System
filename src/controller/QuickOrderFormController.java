package controller;

import bo.BoFactory;
import bo.custom.QuickOrderBo;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.OrderTM;
import view.tm.QuickOrderTM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class QuickOrderFormController {
    public AnchorPane root;
    public TableView<QuickOrderTM> tblQuickOrder;
    public JFXTextField txtQuickId;
    public JFXTextField txtQuickDate;
    public JFXComboBox cmbCode;
    public TextField txtQty;
    public JFXButton btnAdd;
    public JFXButton btnPlaceOrder;
    public JFXButton btnPrintBill;
    public TextField txtDiscount;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnRemove;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public Label lblTotal;

    QuickOrderBo bo;
    SeaFoodBo seaFoodBo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.QUICKORDER);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        loadID();
        txtQuickDate.setText(LocalDate.now().toString());
        loadSeaFoodCombo();
    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = bo.saveOrder(getOrder(),getOrderDetail());
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved",ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Not Saved",ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,"error",ButtonType.OK).show();
        }
    }

    private ArrayList<QuickOrderDetailDTO> getOrderDetail() {
        String oId = txtQuickId.getText().trim();
        ArrayList<QuickOrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        for (int i = 0; i < tblQuickOrder.getItems().size(); i++) {
            QuickOrderTM orderTM = observableList.get(i);
            orderDetailDTOS.add(new QuickOrderDetailDTO(oId,
                    orderTM.getCode(),orderTM.getQty(),orderTM.getPrice(),orderTM.getDiscount()));
        }
        return orderDetailDTOS;

    }

    private QuickOrderDTO getOrder() {
        String oId = txtQuickId.getText().trim();
        String oDate = txtQuickDate.getText().trim();

        return new QuickOrderDTO(oId,oDate);
    }

    ObservableList<QuickOrderTM>observableList=FXCollections.observableArrayList();
    public void btnAddOnAction(ActionEvent actionEvent) {

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colDiscount.setCellValueFactory(new PropertyValueFactory("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        String code = String.valueOf(cmbCode.getValue());
        String desc = txtDescription.getText();
        double qty =Double.parseDouble(txtQty.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        if (!observableList.isEmpty()) { // check observableList is empty
            for (int i = 0; i < tblQuickOrder.getItems().size(); i++) { // check all rows in table
                if (colCode.getCellData(i).equals(code)) { // check  itemcode in table == itemcode we enter
                    double temp = (double) colQty.getCellData(i); // get qty in table for temp
                    temp += qty; // add new qty to old qty
                    double tot = (temp * unitPrice)-discount; // get new total
                    observableList.get(i).setQty(temp); // set new qty to observableList
                    observableList.get(i).setTotal(tot); // set new total to observableList
                    getSubTotal();
                    tblQuickOrder.refresh(); // refresh table
                    return;
                }
            }
        }

        observableList.add(new QuickOrderTM(code, desc, qty, unitPrice,discount, ((qty * unitPrice)-discount)));
        tblQuickOrder.setItems(observableList); // if their is no list or, no matched itemcode
        getSubTotal();
    }

    private void getSubTotal() {
        double tot = 0.0;
        for (QuickOrderTM orderTM : observableList) {
            tot += orderTM.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        QuickOrderTM selectedItem = tblQuickOrder.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            observableList.remove(selectedItem);
            tblQuickOrder.getItems().remove(selectedItem);
            getSubTotal();
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select Row that You Want to Remove !").show();
            tblQuickOrder.requestFocus();
        }
    }

    private void loadID() throws Exception {
        String id = bo.getQuickOrderId();
        txtQuickId.setText(id);
    }

    public void loadSeaFoodCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> arrayList = seaFoodBo.getAllSeaFood();
        for (SeaFoodDTO dto : arrayList) {
            observableList.add(dto.getCode());

        }
        cmbCode.setItems(observableList);
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

    public void txtQtyOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[\\d|.]{1,4}$").matcher(txtQty.getText().trim()).matches()){
            txtQty.setStyle("-fx-border-color: #0fbcf9 ");
            txtDiscount.requestFocus();
        }else {
            txtQty.setStyle("-fx-border-color: #f53b57 ");
            txtQty.requestFocus();
        }
    }

    public void txtDiscountOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[\\d|.]{1,4}$").matcher(txtDiscount.getText().trim()).matches()){
            txtDiscount.setStyle("-fx-border-color: #0fbcf9 ");
            btnAddOnAction(actionEvent);
            cmbCode.requestFocus();
        }else {
            txtDiscount.setStyle("-fx-border-color: #f53b57 ");
            txtDiscount.requestFocus();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) throws Exception {
        cmbCode.setValue(null);
        txtDescription.setText(null);
        txtQtyOnHand.setText(null);
        txtUnitPrice.setText(null);
        txtQty.setText(null);
        txtDiscount.setText(null);
        tblQuickOrder.getItems().clear();
        loadID();
    }
}
