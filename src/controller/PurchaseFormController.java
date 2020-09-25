package controller;

import bo.BoFactory;
import bo.custom.BoatBo;
import bo.custom.PurchaseBo;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import entity.Boat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import view.tm.PurchaseTM;
import javafx.scene.control.TableColumn.CellEditEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PurchaseFormController {
    public AnchorPane root;
    public JFXTextField txtPurchaseId;
    public JFXTextField txtPurchaseDate;
    public JFXComboBox cmbBoatId;
    public JFXTextField txtOwnerName;
    public JFXTextField txtOwnerContact;
    public TextField txtPurchasedPrice;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXComboBox cmbSeaFoodItem;
    public TextField txtQTY;
    public TableView<PurchaseTM> tblPurchase;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPurchasedPrice;
    public TableColumn colTotal;
    public TableColumn colQty;
    public TextField txtSellingPrice;
    public JFXTextField txtBoatName;


    PurchaseBo bo;
    BoatBo boatBo;
    SeaFoodBo seaFoodBo;
    public void initialize() throws Exception {
        cmbBoatId.requestFocus();
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.PURCHASE);
        boatBo = BoFactory.getInstance().getBo(BoFactory.BOType.BOAT);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        txtPurchaseDate.setText(LocalDate.now().toString());
        loadId();
        loadBoatCombo();
        loadItemCombo();

    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void loadBoatCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<BoatDTO> arrayList = boatBo.getAllBoat();
        for (BoatDTO dto : arrayList) {
            observableList.add(dto.getBoatId());

        }
        cmbBoatId.setItems(observableList);
    }


    public void cmbBoatIdOnAction(ActionEvent actionEvent) throws Exception {
        BoatDTO dto = boatBo.getBoat(String.valueOf(cmbBoatId.getValue()));
        if(dto != null){
            txtOwnerName.setText(dto.getOwnerName());
            txtBoatName.setText(dto.getName());
            txtOwnerContact.setText(dto.getOwnerContact());
            cmbSeaFoodItem.requestFocus();
        }
    }

    private void loadId() throws Exception {
        String id = bo.getPurchaseId();
        txtPurchaseId.setText(id);
    }

    public void loadItemCombo() throws Exception {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> arrayList = seaFoodBo.getAllSeaFood();
        for (SeaFoodDTO dto : arrayList) {
            observableList.add(dto.getCode());

        }
        cmbSeaFoodItem.setItems(observableList);
    }

    public void cmbSeaFoodItemOnAction(ActionEvent actionEvent) throws Exception {
        SeaFoodDTO dto = seaFoodBo.getSeaFood(String.valueOf(cmbSeaFoodItem.getValue()));
        if(dto != null){
            txtDescription.setText(dto.getDescription());
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
            txtPurchasedPrice.setText(String.valueOf(dto.getPurchasePrice()));
            txtSellingPrice.setText(String.valueOf(dto.getSellingPrice()));


            txtQTY.requestFocus();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws Exception {

        String pId = txtPurchaseId.getText().trim();
        String pDate = txtPurchaseDate.getText().trim();
        String bId = String.valueOf(cmbBoatId.getValue());
        String code = String.valueOf(cmbSeaFoodItem.getValue());
        String description = txtDescription.getText().trim();
        int qty = Integer.parseInt(txtQTY.getText());
        double purchasedPrice = Double.parseDouble(txtPurchasedPrice.getText());
        double sellingPrice = Double.parseDouble(txtSellingPrice.getText());


      /*  ArrayList<PurchaseDetailDTO> purchaseDetailDTOS = new ArrayList<>();
        purchaseDetailDTOS.add(new PurchaseDetailDTO(pId,code,qty,purchasedPrice));*/


        boolean isSaved = bo.savePurchase(getPurchased(),getPurchasedDetail(),getItemDetail());
        if(isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
        }
    }

    private SeaFoodDTO getItemDetail() {
        return null;
    }

    private ArrayList<PurchaseDetailDTO> getPurchasedDetail() {
        String code = String.valueOf(cmbSeaFoodItem.getValue());
        String pId = txtPurchaseId.getText().trim();
        int qty = Integer.parseInt(txtQTY.getText());
        double purchasedPrice = Double.parseDouble(txtPurchasedPrice.getText());

        ArrayList<PurchaseDetailDTO> purchaseDetailDTOS = new ArrayList<>();
        purchaseDetailDTOS.add(new PurchaseDetailDTO(pId,code,qty,purchasedPrice));
        return purchaseDetailDTOS;
    }

    private PurchaseDTO getPurchased() {
        return null;
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbSeaFoodItem.getValue());
        String desc = txtDescription.getText();
        int qty = Integer.parseInt(txtQTY.getText());
        double purchasedPrice = Double.parseDouble(txtPurchasedPrice.getText());

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colPurchasedPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        if(qty > 0){
            PurchaseTM tm = new PurchaseTM(code,desc,qty,purchasedPrice,(qty*purchasedPrice));

            boolean checkItemAdded = tblPurchase.getItems().stream().anyMatch(itemCheck->code.equals(itemCheck.getCode()));
            if(!checkItemAdded) {
                tblPurchase.getItems().add(tm);
            }
            else {
                /*colQty.setCellFactory(TextFieldTableCell.forTableColumn());
                colQty.setOnEditCommit(event -> {*/
//                    int newQ = Integer.parseInt(txtQTY.getText())+qty;
//                    colQty.setCellValueFactory(new PropertyValueFactory("newQ"));
//                    System.out.println(newQ);
//                });
            }

       }else {
            new Alert(Alert.AlertType.WARNING,"Enter Valid Quantity..!").show();
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        PurchaseTM selectedItem = tblPurchase.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            tblPurchase.getItems().remove(selectedItem);
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select Row that You Want to Remove !").show();
        }
    }
}
