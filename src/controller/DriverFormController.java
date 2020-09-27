package controller;

import bo.BoFactory;
import bo.custom.DriverBo;
import com.jfoenix.controls.JFXTextField;
import dto.ClientDTO;
import dto.DriverDTO;
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
import view.tm.DriverTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DriverFormController {
    public AnchorPane root;
    public JFXTextField txtDriverId;
    public JFXTextField txtDriverName;
    public JFXTextField txtDriverAddress;
    public JFXTextField txtDriverContact;
    public TextField txtSearch;
    public TableView<DriverTM> tblDriver;
    public TableColumn colDriverId;
    public TableColumn colDriverName;
    public TableColumn colDriverAddress;
    public TableColumn colDriverContact;
    public TableColumn colDeleteButton;
    public TableColumn colUpdateButton;
    DriverBo bo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.DRIVER);
        loadAllDriver();
        loadID();

        colDriverId.setCellValueFactory(new PropertyValueFactory("id"));
        colDriverName.setCellValueFactory(new PropertyValueFactory("name"));
        colDriverAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colDriverContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory("btnDelete"));
        colUpdateButton.setCellValueFactory(new PropertyValueFactory("btnUpdate"));

        tblDriver.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });
    }

    private void loadID() throws Exception {
        String id = bo.getDriverID();
        txtDriverId.setText(id);
    }

    private void setData(DriverTM tm) {
        txtDriverId.setText(tm.getId());
        txtDriverName.setText(tm.getName());
        txtDriverAddress.setText(tm.getAddress());
        txtDriverContact.setText(tm.getContact());
    }

    private void loadAllDriver() throws Exception {
        ArrayList<DriverDTO> driverDTOS = bo.getAllDriver();
        ObservableList<DriverTM> tmList = FXCollections.observableArrayList();
        for (DriverDTO dto : driverDTOS) {
            Button btnDelete = new Button("Delete");
            btnDelete.setMaxSize(100, 50);
            btnDelete.setCursor(Cursor.HAND);
            btnDelete.setStyle("-fx-font-weight: bold ; -fx-background-color:  #D50000; ");
            btnDelete.setTextFill(Color.web("#FFFFFF"));
            Button btnUpdate = new Button("Update");
            btnUpdate.setMaxSize(100, 50);
            btnUpdate.setCursor(Cursor.HAND);
            btnUpdate.setStyle("-fx-font-weight: bold ; -fx-background-color:  #00BFA5;");
            DriverTM tm = new DriverTM(dto.getId(), dto.getName(),dto.getAddress(),
                    dto.getContact(),btnDelete,btnUpdate);
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
                        if (bo.deleteDriver(tm.getId())) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Deleted", ButtonType.OK).show();
                            loadAllDriver();
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
                        if (bo.updateDriver(new DriverDTO(txtDriverId.getText().trim(), txtDriverName.getText().trim(),
                                txtDriverAddress.getText().trim(), txtDriverContact.getText().trim()))) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Updated", ButtonType.OK).show();
                            loadAllDriver();
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
        tblDriver.setItems(tmList);

        FilteredList<DriverTM> filteredData = new FilteredList<>(tmList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(driver -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (driver.getId().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (driver.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (driver.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (driver.getContact().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<DriverTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblDriver.comparatorProperty());
        tblDriver.setItems(sortedData);
    }

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtDriverId.getText().trim();
        String name = txtDriverName.getText().trim();
        String address = txtDriverAddress.getText().trim();
        String contact = txtDriverContact.getText().trim();

        if(id.length()>0 && name.length()>0 && address.length()>0 && contact.length()>0){
            try {
                boolean isAdded = bo.saveDriver(new DriverDTO(id,name,address,contact));
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved",ButtonType.OK).showAndWait();
                    loadAllDriver();
                    clear();
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

    public void txtSearchOnAction(ActionEvent actionEvent) throws Exception {
        DriverDTO dto = bo.getDriver(txtSearch.getText().trim());
        if(dto != null){
            txtDriverId.setText(dto.getId());
            txtDriverName.setText(dto.getName());
            txtDriverAddress.setText(dto.getAddress());
            txtDriverContact.setText(dto.getContact());
        }else{
            new Alert(Alert.AlertType.INFORMATION,
                    "Enter Valid Client Id", ButtonType.OK).show();
        }
    }


    public void clear() {
        txtDriverId.setText(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtDriverContact.setText(null);
        txtSearch.setText(null);
    }
}
