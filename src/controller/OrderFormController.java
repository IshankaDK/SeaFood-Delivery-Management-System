package controller;

import bo.BoFactory;
import bo.custom.ClientBo;
import bo.custom.OrderBo;
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
import view.tm.PurchaseTM;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

public class OrderFormController {
    public AnchorPane root;
    public TableView<OrderTM> tblOrder;
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
    public void initialize()  {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.ORDER);
        clientBo = BoFactory.getInstance().getBo(BoFactory.BOType.CLIENT);
        seaFoodBo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);
        loadId();
        txtOrderDate.setText(LocalDate.now().toString());
        loadClientCombo();
        loadSeaFoodCombo();
    }

    public void loadClientCombo() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        ArrayList<ClientDTO> arrayList = null;
        try {
            arrayList = clientBo.getAllClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ClientDTO dto : arrayList) {
            observableList.add(dto.getId());

        }
        cmbClientId.setItems(observableList);
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

    ObservableList<OrderTM>observableList=FXCollections.observableArrayList();
    public void btnAddOnAction(ActionEvent actionEvent)  {

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
            for (int i = 0; i < tblOrder.getItems().size(); i++) { // check all rows in table
                if (colCode.getCellData(i).equals(code)) { // check  itemcode in table == itemcode we enter
                    double temp = (double) colQty.getCellData(i); // get qty in table for temp
                    temp += qty; // add new qty to old qty
                    if(temp <= Double.parseDouble(txtQtyOnHand.getText())){
                        double tot = (temp * unitPrice)-discount; // get new total
                        observableList.get(i).setQty(temp); // set new qty to observableList
                        observableList.get(i).setTotal(tot); // set new total to observableList
                        getSubTotal();
                        tblOrder.refresh(); // refresh table
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
        observableList.add(new OrderTM(code, desc, qty, unitPrice,discount, ((qty * unitPrice)-discount)));
        tblOrder.setItems(observableList); // if their is no list or, no matched itemcode
        getSubTotal();
    }

    private void getSubTotal() {
        double tot = 0.0;
        for (OrderTM orderTM : observableList) {
            tot += orderTM.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        OrderTM selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        if(selectedItem!=null) {
            observableList.remove(selectedItem);
            tblOrder.getItems().remove(selectedItem);
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
            tblOrder.requestFocus();
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try{
            if(tblOrder.getItems().size()==0){
                Notifications notificationBuilder = Notifications.create()
                        .title("No Item Selected.!")
                        .text("No Item Selected.!, Select Item and Try Again.")
                        .graphic(new ImageView(new Image("/assert/done.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }else {
                boolean isSaved = bo.saveOrder(getOrder(), getOrderDetail());
                if (isSaved) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Saved Successfully.!")
                            .text("Order Successfully saved to the System.")
                            .graphic(new ImageView(new Image("/assert/done.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                } else {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Saving UnSuccessful.!")
                            .text("Order Not saved. Please try again..!")
                            .graphic(new ImageView(new Image("/assert/errorpng.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT)
                            .darkStyle();
                    notificationBuilder.show();
                }
            }
            } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Error, Saving UnSuccessful.!")
                    .text("Order Not saved. Please try again..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .darkStyle();
            notificationBuilder.show();
            }
    }

    private ArrayList<OrderDetailDTO> getOrderDetail() {
        String oId = txtOrderId.getText().trim();
        ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTM orderTM = observableList.get(i);
            orderDetailDTOS.add(new OrderDetailDTO(oId,
                    orderTM.getCode(),orderTM.getQty(),orderTM.getPrice(),orderTM.getDiscount()));
        }
        return orderDetailDTOS;
    }

    private OrderDTO getOrder() {

        String oId = txtOrderId.getText().trim();
        String oDate = txtOrderDate.getText().trim();
        String cId = String.valueOf(cmbClientId.getValue());
        String status = "Pending";

        return new OrderDTO(oId,oDate,cId,status);
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) {

        try {
            InputStream is = this.getClass().getResourceAsStream("/report/orderReport01.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(is);

            HashMap hm = new HashMap();
            hm.put("customer", txtClientName.getText());
            hm.put("allTtotal", lblTotal.getText());
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

    private void loadId()  {
        String id = null;
        try {
            id = bo.getOrderId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtOrderId.setText(id);

    }

    public void cmbClientIdOnAction(ActionEvent actionEvent)  {
        ClientDTO dto = null;
        try {
            dto = clientBo.getClient(String.valueOf(cmbClientId.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dto != null){
            txtClientName.setText(dto.getName());
            txtClientAddress.setText(dto.getAddress());

            cmbCode.requestFocus();
        }
    }

    public void cmbCodeOnAction(ActionEvent actionEvent) {
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
                Notifications notificationBuilder = Notifications.create()
                        .title("Error.!, No Items.!")
                        .text("No Sufficient Items in the Stock..!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle();
                notificationBuilder.show();
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

    public void btnClearOnAction(ActionEvent actionEvent)  {
        cmbClientId.setValue(null);
        txtClientName.setText(null);
        txtClientAddress.setText(null);
        cmbCode.setValue(null);
        txtDescription.setText(null);
        txtQtyOnHand.setText(null);
        txtUnitPrice.setText(null);
        txtQty.setText(null);
        txtDiscount.setText(null);
        tblOrder.getItems().clear();
        lblTotal.setText("0.00");
        loadId();
    }
}
