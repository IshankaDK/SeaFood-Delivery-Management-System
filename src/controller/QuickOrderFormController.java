package controller;

import bo.BoFactory;
import bo.custom.QuickOrderBo;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import dto.*;
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
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import view.tm.OrderTM;
import view.tm.QuickOrderTM;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

    public void initialize()  {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.QUICKORDER);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        loadID();
        txtQuickDate.setText(LocalDate.now().toString());
        loadSeaFoodCombo();
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) {
        try {
            InputStream is = this.getClass().getResourceAsStream("/report/QuickOrder.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(is);

            HashMap hm = new HashMap();
            hm.put("allTotal", lblTotal.getText());
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
                    if(temp <= Double.parseDouble(txtQtyOnHand.getText())){
                        double tot = (temp * unitPrice)-discount; // get new total
                        observableList.get(i).setQty(temp); // set new qty to observableList
                        observableList.get(i).setTotal(tot); // set new total to observableList
                        getSubTotal();
                        tblQuickOrder.refresh(); // refresh table
                        return;
                    }else {
                        Notifications notificationBuilder = Notifications.create()
                                .title("Error..!")
                                .text("No Sufficient Items in the Stock..!")
                                .graphic(new ImageView(new Image("/assert/errorpng.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                        return;
                    }
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
            Notifications notificationBuilder = Notifications.create()
                    .title("No Row Selected.!")
                    .text("Please Select Row that You Want to Remove !")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            tblQuickOrder.requestFocus();
        }
    }

    private void loadID()  {
        String id = null;
        try {
            id = bo.getQuickOrderId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtQuickId.setText(id);
    }

    public void loadSeaFoodCombo()  {
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
        cmbCode.setItems(observableList);
    }

    public void cmbCodeOnAction(ActionEvent actionEvent)  {
        SeaFoodDTO dto = null;
        try {
            dto = seaFoodBo.getSeaFood(String.valueOf(cmbCode.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dto != null){
            txtDescription.setText(dto.getDescription());
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
            txtUnitPrice.setText(String.valueOf(dto.getSellingPrice()));

            txtQty.requestFocus();
        }
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[\\d|.]{1,4}$").matcher(txtQty.getText().trim()).matches()){
            if(Double.parseDouble(txtQty.getText()) <= Double.parseDouble(txtQtyOnHand.getText())){
                txtQty.setStyle("-fx-border-color: #0fbcf9 ");
                txtDiscount.requestFocus();
            }else {
                txtQty.setStyle("-fx-border-color: #f53b57 ");
                txtQty.requestFocus();
                new Alert(Alert.AlertType.WARNING,"No Sufficient Items in the Stock..! ",ButtonType.OK).show();
            }
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

    public void btnClearOnAction(ActionEvent actionEvent) {
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
