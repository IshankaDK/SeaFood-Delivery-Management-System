package controller;

import bo.BoFactory;
import bo.custom.BoatBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.BoatDTO;
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
import javafx.scene.paint.Paint;
import view.tm.BoatTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class BoatFormController {
    public AnchorPane root;
    public TableView<BoatTM> tblBoats;
    public JFXTextField txtBoatId;
    public JFXTextField txtOwnerName;
    public JFXTextField txtOwnerContact;
    public TableColumn colBoatId;
    public TableColumn colOwnerName;
    public TableColumn colOwnerContact;
    public TableColumn colDeleteButton;
    public TableColumn colUpdateButton;
    public TextField txtSearch;
    public TableColumn colBoatName;
    public JFXTextField txtBoatName;
    public JFXButton btnAdd;
    BoatBo bo;

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.BOAT);
        loadId();
        loadAllBoat();

        colBoatId.setCellValueFactory(new PropertyValueFactory("boatId"));
        colBoatName.setCellValueFactory(new PropertyValueFactory("name"));
        colOwnerName.setCellValueFactory(new PropertyValueFactory("ownerName"));
        colOwnerContact.setCellValueFactory(new PropertyValueFactory("ownerContact"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory("btnDelete"));
        colUpdateButton.setCellValueFactory(new PropertyValueFactory("btnUpdate"));

        tblBoats.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });

    }

    private void setData(BoatTM tm) {
        txtBoatId.setText(tm.getBoatId());
        txtBoatName.setText(tm.getName());
        txtOwnerName.setText(tm.getOwnerName());
        txtOwnerContact.setText(tm.getOwnerContact());
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        String id = txtBoatId.getText().trim();
        String name = txtBoatName.getText().trim();
        String ownerName = txtOwnerName.getText().trim();
        String contact = txtOwnerContact.getText().trim();

        if(id.length()>0 && name.length()>0 && contact.length()>0){
            try {
                boolean isAdded = bo.saveBoat(new BoatDTO(id,name,ownerName,contact));
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved",ButtonType.OK).showAndWait();
                    loadAllBoat();
                    clear();
                    loadId();
                    txtBoatId.requestFocus();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, " Not Saved, Try Again",ButtonType.OK).show();
                    txtBoatId.requestFocus();
                }
            } catch (SQLException se){
                new Alert(Alert.AlertType.ERROR, "SQL Syntax Error",ButtonType.OK).show();
                txtBoatId.requestFocus();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error",ButtonType.OK).show();
                txtBoatId.requestFocus();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Text Field is Empty",ButtonType.OK).show();
            txtBoatId.requestFocus();
        }
    }

    public void loadAllBoat() throws Exception {
        ArrayList<BoatDTO> boatDTOS = bo.getAllBoat();
        ObservableList<BoatTM> tmList = FXCollections.observableArrayList();
        for (BoatDTO dto : boatDTOS) {
            Button btnDelete = new Button("Delete");
            btnDelete.setMaxSize(100, 50);
            btnDelete.setCursor(Cursor.HAND);
            btnDelete.setStyle("-fx-font-weight: bold ; -fx-background-color:  #D50000; ");
            btnDelete.setTextFill(Color.web("#FFFFFF"));
            // btnDelete.setStyle("-fx-background-image: url('/assert/bin.png')");
            // btnDelete.setStyle("-fx-font-size:20");
            //btnDelete.setStyle("-fx-font-weight: bold;");
            Button btnUpdate = new Button("Update");
            btnUpdate.setMaxSize(100, 50);
            btnUpdate.setCursor(Cursor.HAND);
            // btnUpdate.setStyle("-fx-background-color: #00BFA5");
            btnUpdate.setStyle("-fx-font-weight: bold ; -fx-background-color:  #00BFA5;");
            //btnUpdate.setStyle();
            BoatTM tm = new BoatTM(dto.getBoatId(),dto.getName(), dto.getOwnerName(),dto.getOwnerContact(),
                    btnDelete,btnUpdate);
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
                        if (bo.deleteBoat(tm.getBoatId())) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Deleted", ButtonType.OK).show();
                            loadAllBoat();
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
                        if (bo.updateBoat(new BoatDTO(txtBoatId.getText().trim(),txtBoatName.getText().trim(), txtOwnerName.getText().trim(),
                                txtOwnerContact.getText().trim()))) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Updated", ButtonType.OK).show();
                            loadAllBoat();
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
        tblBoats.setItems(tmList);

        FilteredList<BoatTM> filteredData = new FilteredList<>(tmList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(boat -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (boat.getBoatId().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (boat.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (boat.getOwnerName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (boat.getOwnerContact().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    return false;
            });
        });
        SortedList<BoatTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBoats.comparatorProperty());
        tblBoats.setItems(sortedData);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws Exception {
        BoatDTO dto = bo.getBoat(txtSearch.getText().trim());
        if(dto != null){
            txtBoatId.setText(dto.getBoatId());
            txtBoatName.setText(dto.getName());
            txtOwnerName.setText(dto.getOwnerName());
            txtOwnerContact.setText(dto.getOwnerContact());
        }else{
            txtSearch.setStyle("-fx-border-color: #f53b57 ");
            txtSearch.requestFocus();
            new Alert(Alert.AlertType.INFORMATION,
                    "Enter Valid Client Id", ButtonType.OK).show();

        }
    }

    private void clear() {
        txtBoatId.setText(null);
        txtBoatName.setText(null);
        txtOwnerName.setText(null);
        txtOwnerContact.setText(null);
        txtSearch.setText(null);
    }

    private void loadId() throws Exception {
        String id = bo.getBoatId();
        txtBoatId.setText(id);
    }

    public void txtBoatIdOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(B)[0-9]{1,}$").matcher(txtBoatId.getText().trim()).matches()){
            txtBoatId.setFocusColor(Paint.valueOf("skyblue"));
            txtBoatName.requestFocus();
        }else {
            txtBoatId.setFocusColor(Paint.valueOf("red"));
            txtBoatId.requestFocus();
        }
    }

    public void txtOwnerNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtOwnerName.getText().trim()).matches()){
            txtOwnerName.setFocusColor(Paint.valueOf("skyblue"));
            txtOwnerContact.requestFocus();
        }else {
            txtOwnerName.setFocusColor(Paint.valueOf("red"));
            txtOwnerName.requestFocus();
        }
    }

    public void txtOwnerContactOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(0)[0-9]{9}$").matcher(txtOwnerContact.getText().trim()).matches()){
            txtOwnerContact.setFocusColor(Paint.valueOf("skyblue"));
            btnAdd.requestFocus();
            btnAddOnAction(actionEvent);
        }else {
            txtOwnerContact.setFocusColor(Paint.valueOf("red"));
            txtOwnerContact.requestFocus();
        }
    }

    public void txtBoatNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtBoatName.getText().trim()).matches()){
            txtBoatName.setFocusColor(Paint.valueOf("skyblue"));
            txtOwnerName.requestFocus();
        }else {
            txtBoatName.setFocusColor(Paint.valueOf("red"));
            txtBoatName.requestFocus();
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws Exception {
        clear();
        loadId();
    }
}
