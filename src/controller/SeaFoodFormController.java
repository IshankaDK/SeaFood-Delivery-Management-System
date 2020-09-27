package controller;

import bo.BoFactory;
import bo.custom.SeaFoodBo;
import com.jfoenix.controls.JFXTextField;
import dto.ClientDTO;
import dto.SeaFoodDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import view.tm.ClientTM;
import view.tm.SeaFoodTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SeaFoodFormController {
    public AnchorPane root;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtPurchasePrice;
    public JFXTextField txtSellPrice;
    public TextField txtSearch;
    public TableView<SeaFoodTM> tblSeaFood;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colPurchasePrice;
    public TableColumn colSellingPrice;
    public TableColumn colDeleteButton;
    public TableColumn colUpdateButton;

    SeaFoodBo bo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.SEAFOOD);

        loadAllSeaFood();
        loadCode();

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        colPurchasePrice.setCellValueFactory(new PropertyValueFactory("purchasePrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory("sellingPrice"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory("btnDelete"));
        colUpdateButton.setCellValueFactory(new PropertyValueFactory("btnUpdate"));

        tblSeaFood.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });

    }

    private void setData(SeaFoodTM tm) {
        txtCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        txtPurchasePrice.setText(String.valueOf(tm.getPurchasePrice()));
        txtSellPrice.setText(String.valueOf(tm.getSellingPrice()));
    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String code = txtCode.getText().trim();
        String description = txtDescription.getText().trim();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText().trim());
        double purchasePrice = Double.parseDouble(txtPurchasePrice.getText().trim());
        double sellingPrice = Double.parseDouble(txtSellPrice.getText().trim());

        if(code.length()>0 && description.length()>0 && txtQtyOnHand.getText().trim().length()>0 &&
                txtPurchasePrice.getText().trim().length()>0 && txtSellPrice.getText().trim().length()>0){
            try {
                boolean isAdded = bo.saveSeaFood(new SeaFoodDTO(code,description,qtyOnHand,purchasePrice,sellingPrice));
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved",ButtonType.OK).showAndWait();
                    loadAllSeaFood();
                    clear();
                    loadCode();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, " Not Saved, Try Again",ButtonType.OK).show();
                }
            } catch (SQLException se){
                new Alert(Alert.AlertType.ERROR, "SQL Syntax Error",ButtonType.OK).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error",ButtonType.OK).show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Text Field is Empty",ButtonType.OK).show();
        }
    }

    private void loadAllSeaFood() throws Exception {
        ArrayList<SeaFoodDTO> seaFoodDTOS = bo.getAllSeaFood();
        ObservableList<SeaFoodTM> tmList = FXCollections.observableArrayList();
        for (SeaFoodDTO dto : seaFoodDTOS) {
            Button btnDelete = new Button("Delete");
            btnDelete.setMaxSize(100, 50);
            btnDelete.setCursor(Cursor.HAND);
            btnDelete.setStyle("-fx-font-weight: bold ; -fx-background-color:  #D50000; ");
            btnDelete.setTextFill(Color.web("#FFFFFF"));
            Button btnUpdate = new Button("Update");
            btnUpdate.setMaxSize(100, 50);
            btnUpdate.setCursor(Cursor.HAND);
            btnUpdate.setStyle("-fx-font-weight: bold ; -fx-background-color:  #00BFA5;");
            SeaFoodTM tm = new SeaFoodTM(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),
                    dto.getPurchasePrice(),dto.getSellingPrice(),btnDelete,btnUpdate);
            tmList.add(tm);
            btnDelete.setOnAction((e) -> {
                try {
                    ButtonType ok = new ButtonType("OK",
                            ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO",
                            ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are You Want to Delete ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        if (bo.deleteSeaFood(tm.getCode())) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Deleted", ButtonType.OK).show();
                            loadAllSeaFood();
                            clear();
                            return;
                        }
                        new Alert(Alert.AlertType.WARNING,
                                "Try Again", ButtonType.OK).show();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });

            btnUpdate.setOnAction((e) -> {
                try {
                    ButtonType ok = new ButtonType("OK",
                            ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO",
                            ButtonBar.ButtonData.CANCEL_CLOSE);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are You Want to Update ?", ok, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.orElse(no) == ok) {
                        if (bo.updateSeaFood(new SeaFoodDTO(txtCode.getText().trim(), txtDescription.getText().trim(),
                                Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtPurchasePrice.getText()),
                                Double.parseDouble(txtSellPrice.getText())))) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Updated", ButtonType.OK).show();
                            loadAllSeaFood();
                            clear();
                            return;
                        }
                        new Alert(Alert.AlertType.WARNING,
                                "Try Again", ButtonType.OK).show();
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        }
        tblSeaFood.setItems(tmList);

        FilteredList<SeaFoodTM> filteredData = new FilteredList<>(tmList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(seaFood -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (seaFood.getCode().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (seaFood.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(seaFood.getQtyOnHand()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(seaFood.getPurchasePrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(seaFood.getSellingPrice()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<SeaFoodTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblSeaFood.comparatorProperty());
        tblSeaFood.setItems(sortedData);
    }

    public void clear() {
        txtCode.setText(null);
        txtDescription.setText(null);
        txtQtyOnHand.setText(null);
        txtPurchasePrice.setText(null);
        txtSellPrice.setText(null);
        txtSearch.setText(null);

    }

    private void loadCode() throws Exception {
        String code = bo.getCode();
        txtCode.setText(code);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws Exception {
        SeaFoodDTO dto = bo.getSeaFood(txtSearch.getText().trim());
        if(dto != null){
            txtCode.setText(dto.getCode());
            txtDescription.setText(dto.getDescription());
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
            txtPurchasePrice.setText(String.valueOf(dto.getPurchasePrice()));
            txtSellPrice.setText(String.valueOf(dto.getSellingPrice()));
        }else{
            new Alert(Alert.AlertType.INFORMATION,
                    "Enter Valid Client Id", ButtonType.OK).show();
        }
    }
}
