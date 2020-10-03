package controller;

import bo.BoFactory;
import bo.custom.DefaultBo;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    public Label lblTotalClient;
    public Label lblTotalDO;
    public Label lblTotalSeaFood;

    DefaultBo bo = BoFactory.getInstance().getBo(BoFactory.BOType.DEFAULT);

    public void initialize() throws Exception {
        lblTotalOrder.setText(String.valueOf(bo.getTotalOrders()));
        lblTotalClient.setText(String.valueOf(bo.getTotalClient()));
        lblTotalDO.setText(String.valueOf(bo.getTotalDelivery()));
        lblTotalSeaFood.setText(String.valueOf(bo.getTotalSeafood()));

    }

}
