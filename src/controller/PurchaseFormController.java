package controller;

import bo.BoFactory;
import bo.custom.BoatBo;
import bo.custom.PurchaseBo;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.BoatDTO;
import dto.PurchaseDTO;
import dto.PurchaseDetailDTO;
import dto.SeaFoodDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import view.tm.PurchaseTM;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
    public Label lblTotal;
    public JFXButton btnAdd;


    PurchaseBo bo;
    BoatBo boatBo;
    SeaFoodBo seaFoodBo;

    public void initialize()  {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.PURCHASE);
        boatBo = BoFactory.getInstance().getBo(BoFactory.BOType.BOAT);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        txtPurchaseDate.setText(LocalDate.now().toString());
        loadId();
        loadBoatCombo();
        loadItemCombo();
        cmbBoatId.requestFocus();

    }

    public void loadBoatCombo(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            ArrayList<BoatDTO>  arrayList = boatBo.getAllBoat();
            for (BoatDTO dto : arrayList) {
                observableList.add(dto.getBoatId());

            }
            cmbBoatId.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cmbBoatIdOnAction(ActionEvent actionEvent) {
        try {
            BoatDTO dto = boatBo.getBoat(String.valueOf(cmbBoatId.getValue()));
            if(dto != null){
                txtOwnerName.setText(dto.getOwnerName());
                txtBoatName.setText(dto.getName());
                txtOwnerContact.setText(dto.getOwnerContact());
                cmbSeaFoodItem.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadId()  {
        try {
            String id = bo.getPurchaseId();
            txtPurchaseId.setText(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadItemCombo() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> arrayList = null;
        try {
            arrayList = seaFoodBo.getAllSeaFood();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (SeaFoodDTO dto : arrayList) {
            observableList.add(dto.getCode());

        }
        cmbSeaFoodItem.setItems(observableList);
    }

    public void cmbSeaFoodItemOnAction(ActionEvent actionEvent)  {
        try {
          SeaFoodDTO dto = seaFoodBo.getSeaFood(String.valueOf(cmbSeaFoodItem.getValue()));
            if(dto != null){
                txtDescription.setText(dto.getDescription());
                txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
                txtPurchasedPrice.setText(String.valueOf(dto.getPurchasePrice()));
                txtSellingPrice.setText(String.valueOf(dto.getSellingPrice()));
                txtQTY.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = bo.savePurchase(getPurchased(),getPurchaseDetail());
            if(isSaved){
                Notifications notificationBuilder = Notifications.create()
                        .title("Saved Successfully.!")
                        .text("Purchase Successfully saved to the System.")
                        .graphic(new ImageView(new Image("/assert/done.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                txtPurchaseId.requestFocus();
            }else {
                Notifications notificationBuilder = Notifications.create()
                        .title("Saving UnSuccessful.!")
                        .text("Purchase Not saved. Please try again..!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle();
                notificationBuilder.show();
                tblPurchase.requestFocus();
            }
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Saving UnSuccessful.!")
                    .text("Purchase Not saved. Please try again..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle();
            notificationBuilder.show();
        }
    }

    private PurchaseDTO getPurchased() {

        String pId = txtPurchaseId.getText().trim();
        String pDate = txtPurchaseDate.getText().trim();
        String bId = String.valueOf(cmbBoatId.getValue());

        return new PurchaseDTO(pId,pDate,bId);
    }

    private ArrayList<PurchaseDetailDTO> getPurchaseDetail(){

        String pId = txtPurchaseId.getText().trim();
        ArrayList<PurchaseDetailDTO> purchaseDetailDTOS = new ArrayList<>();

        for (int i = 0; i < tblPurchase.getItems().size(); i++) {
            PurchaseTM purchaseTM = observableList.get(i);
            purchaseDetailDTOS.add(new PurchaseDetailDTO(pId,
                    purchaseTM.getCode(),purchaseTM.getQty(),purchaseTM.getPrice()));
        }
        return purchaseDetailDTOS;
    }

    ObservableList<PurchaseTM>observableList=FXCollections.observableArrayList();
    public void btnAddOnAction(ActionEvent actionEvent) {

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colPurchasedPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        String code = String.valueOf(cmbSeaFoodItem.getValue());
        String desc = txtDescription.getText();
        double qty = Double.parseDouble(txtQTY.getText());
        double purchasedPrice = Double.parseDouble(txtPurchasedPrice.getText());
        if (!observableList.isEmpty()) { // check observableList is not empty
            for (int i = 0; i < tblPurchase.getItems().size(); i++) { // check all rows in table
                if (colCode.getCellData(i).equals(code)) { // check  itemcode in table == itemcode we enter
                    double temp = (double) colQty.getCellData(i); // get qty in table for temp
                    temp += qty; // add new qty to old qty
                    double tot = temp * purchasedPrice; // get new total
                    observableList.get(i).setQty(temp); // set new qty to observableList
                    observableList.get(i).setTotal(tot); // set new total to observableList
                    getSubTotal();
                    tblPurchase.refresh(); // refresh table
                    return;
                }
            }
        }

        observableList.add(new PurchaseTM(code, desc, qty, purchasedPrice, (qty * purchasedPrice)));
        tblPurchase.setItems(observableList); // if their is no list or, no matched itemcode
        getSubTotal();

    }

    private void getSubTotal(){
        double tot = 0.0;
        for (PurchaseTM purchaseTM : observableList) {
            tot += purchaseTM.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        PurchaseTM selectedItem = tblPurchase.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            observableList.remove(selectedItem);
            tblPurchase.getItems().remove(selectedItem);
            getSubTotal();
        }else{
            Notifications notificationBuilder = Notifications.create()
                    .title("No Row Selected.!")
                    .text("Please Select Row that You Want to Remove !")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            tblPurchase.requestFocus();
        }
    }

    public void txtPurchaseIdOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(PU)[0-9]{1,}$").matcher(txtPurchaseId.getText().trim()).matches()){
            txtPurchaseId.setFocusColor(Paint.valueOf("skyblue"));
            cmbBoatId.requestFocus();
        }else {
            txtPurchaseId.setFocusColor(Paint.valueOf("red"));
            txtPurchaseId.requestFocus();
        }
    }

    public void txtPurchasedPriceOnAction(ActionEvent actionEvent) {
        if (Pattern.compile("^[\\d|.]{1,9}$").matcher(txtPurchasedPrice.getText().trim()).matches()) {
            txtPurchasedPrice.setStyle("-fx-border-color: #0fbcf9 ");
            txtSellingPrice.requestFocus();
        } else {
            txtPurchasedPrice.setStyle("-fx-border-color: #f53b57 ");
            txtPurchasedPrice.requestFocus();
        }
    }

    public void txtSellingPriceOnAction(ActionEvent actionEvent) {
        if (Pattern.compile("^[\\d|.]{1,9}$").matcher(txtSellingPrice.getText().trim()).matches()) {
            txtSellingPrice.setStyle("-fx-border-color: #0fbcf9 ");
            txtQTY.requestFocus();
        } else {
            txtSellingPrice.setStyle("-fx-border-color: #f53b57 ");
            txtSellingPrice.requestFocus();
        }
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[\\d|.]{1,4}$").matcher(txtQTY.getText().trim()).matches()){
            txtQTY.setStyle("-fx-border-color: #0fbcf9 ");
            btnAddOnAction(actionEvent);
            cmbSeaFoodItem.requestFocus();
        }else {
            txtQTY.setStyle("-fx-border-color: #f53b57 ");
            txtQTY.requestFocus();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        cmbBoatId.setValue(null);
        txtBoatName.setText(null);
        txtOwnerName.setText(null);
        txtOwnerContact.setText(null);
        cmbSeaFoodItem.setValue(null);
        txtDescription.setText(null);
        txtQtyOnHand.setText(null);
        txtPurchasedPrice.setText(null);
        txtSellingPrice.setText(null);
        txtQTY.setText(null);
        tblPurchase.getItems().clear();
        try {
            loadId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
