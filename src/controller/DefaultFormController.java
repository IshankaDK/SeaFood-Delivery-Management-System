package controller;

import bo.BoFactory;
import bo.custom.DefaultBo;
import dto.SeaFoodDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.DefaultLeastTM;
import view.tm.DefaultMostTM;

import java.util.ArrayList;

public class DefaultFormController {
    public TableView tblMostItem;
    public TableView tblLeastItem;
    public Label lblTotalOrder;
    public TableColumn colMostCode;
    public TableColumn colMostDesc;
    public TableColumn colMostQtyOnHand;
    public TableColumn colMostPurchPrice;
    public TableColumn colMostSellPrice;
    public TableColumn colLeastCode;
    public TableColumn colLeastDesc;
    public TableColumn colLeastQtyOnHand;
    public TableColumn colLeastPurchPrice;
    public TableColumn colLeastSellPrice;
    public Label lblTotalSeaFood;
    public Label lblFinishedDO;
    public Label lblPendingDelivery;

    DefaultBo bo = BoFactory.getInstance().getBo(BoFactory.BOType.DEFAULT);

    public void initialize() throws Exception {
        lblTotalOrder.setText(String.valueOf(bo.getTotalOrders()));
        lblFinishedDO.setText(String.valueOf(bo.getFinishedDO()));
        lblPendingDelivery.setText(String.valueOf(bo.getPendingDO()));
        lblTotalSeaFood.setText(String.valueOf(bo.getTotalSeafood()));

        colMostCode.setCellValueFactory(new PropertyValueFactory("code"));
        colMostDesc.setCellValueFactory(new PropertyValueFactory("description"));
        colMostQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        colMostPurchPrice.setCellValueFactory(new PropertyValueFactory("purchasePrice"));
        colMostSellPrice.setCellValueFactory(new PropertyValueFactory("sellingPrice"));

        getMostMovableItem();

        colLeastCode.setCellValueFactory(new PropertyValueFactory("code"));
        colLeastDesc.setCellValueFactory(new PropertyValueFactory("description"));
        colLeastQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        colLeastPurchPrice.setCellValueFactory(new PropertyValueFactory("purchasePrice"));
        colLeastSellPrice.setCellValueFactory(new PropertyValueFactory("sellingPrice"));

        getLeastMovableItem();
    }

    private void getMostMovableItem() throws Exception {
        ObservableList<DefaultMostTM>observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> foodDTOS = bo.getMostMovable();
        for (SeaFoodDTO dto : foodDTOS) {
            DefaultMostTM tm = new DefaultMostTM(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getPurchasePrice(),dto.getSellingPrice());
            observableList.add(tm);
        }
        tblMostItem.setItems(observableList);
    }
    private void getLeastMovableItem() throws Exception {
        ObservableList<DefaultLeastTM>observableList = FXCollections.observableArrayList();
        ArrayList<SeaFoodDTO> foodDTOS = bo.getLeastMovable();
        for (SeaFoodDTO dto : foodDTOS) {
            DefaultLeastTM tm = new DefaultLeastTM(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getPurchasePrice(),dto.getSellingPrice());
            observableList.add(tm);
        }
        tblLeastItem.setItems(observableList);
    }
}
