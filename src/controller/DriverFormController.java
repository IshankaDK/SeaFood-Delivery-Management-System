package controller;

import bo.BoFactory;
import bo.custom.DriverBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.DriverDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import view.tm.DriverTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

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
    public JFXButton btnAdd;
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

    private void loadID()  {
        String id = null;
        try {
            id = bo.getDriverID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtDriverId.setText(id);
    }

    private void setData(DriverTM tm) {
        if(tm != null){
            txtDriverId.setText(tm.getId());
            txtDriverName.setText(tm.getName());
            txtDriverAddress.setText(tm.getAddress());
            txtDriverContact.setText(tm.getContact());
        }
    }

    private void loadAllDriver()  {
        ArrayList<DriverDTO> driverDTOS = null;
        try {
            driverDTOS = bo.getAllDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Delete Successful.!")
                                    .text("You have Successfully Delete Driver from the System.")
                                    .graphic(new ImageView(new Image("/assert/done.png")))
                                    .hideAfter(Duration.seconds(4))
                                    .position(Pos.BOTTOM_RIGHT);
                            notificationBuilder.darkStyle();
                            notificationBuilder.show();
                            loadAllDriver();
                            clear();
                            loadID();
                            return;
                        }
                        Notifications notificationBuilder = Notifications.create()
                                .title("Delete UnSuccessful.!")
                                .text("Driver Not Deleted, Please try Again..!")
                                .graphic(new ImageView(new Image("/assert/errorpng.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }

                } catch (Exception e1) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Delete UnSuccessful.!")
                            .text("Driver Not Deleted, Please try Again..!")
                            .graphic(new ImageView(new Image("/assert/errorpng.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
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
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Update Successful.!")
                                    .text("You have Successfully Update Driver from the System.")
                                    .graphic(new ImageView(new Image("/assert/done.png")))
                                    .hideAfter(Duration.seconds(4))
                                    .position(Pos.BOTTOM_RIGHT);
                            notificationBuilder.darkStyle();
                            notificationBuilder.show();
                            loadAllDriver();
                            clear();
                            loadID();
                            return;
                        }
                        Notifications notificationBuilder = Notifications.create()
                                .title("Update UnSuccessful.!")
                                .text("Driver Not Updated, Please try Again..!")
                                .graphic(new ImageView(new Image("/assert/errorpng.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }

                } catch (Exception e1) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Update UnSuccessful.!")
                            .text("Driver Not Updated, Please try Again..!")
                            .graphic(new ImageView(new Image("/assert/errorpng.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
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

    public void imgBackToHome(MouseEvent mouseEvent) {
        this.root.getChildren().clear();
        try {
            this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtDriverId.getText().trim();
        String name = txtDriverName.getText().trim();
        String address = txtDriverAddress.getText().trim();
        String contact = txtDriverContact.getText().trim();

        if(txtDriverId.getText().trim().length()>0 && txtDriverName.getText().trim().length()>0 && txtDriverAddress.getText().trim().length()>0 && txtDriverContact.getText().trim().length()>0){
            try {
                boolean isAdded = bo.saveDriver(new DriverDTO(id,name,address,contact));
                if (isAdded) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Saved Successfully.!")
                            .text("You have Successfully save Driver to the System.")
                            .graphic(new ImageView(new Image("/assert/done.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                    loadAllDriver();
                    clear();
                    loadID();
                    txtDriverId.requestFocus();
                } else {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Saving UnSuccessful.!")
                            .text("Driver Not Saved, Try Again.!")
                            .graphic(new ImageView(new Image("/assert/errorpng.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                    txtDriverId.requestFocus();
                    loadID();
                }
            } catch (SQLException se){
                Notifications notificationBuilder = Notifications.create()
                        .title("Saving UnSuccessful.!")
                        .text("Driver Not Saved, Try Again.!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                txtDriverId.requestFocus();
                loadID();
            } catch (Exception e) {
                Notifications notificationBuilder = Notifications.create()
                        .title("Saving UnSuccessful.!")
                        .text("Driver Not Saved, Try Again.!")
                        .graphic(new ImageView(new Image("/assert/errorpng.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.BOTTOM_RIGHT);
                notificationBuilder.darkStyle();
                notificationBuilder.show();
                txtDriverId.requestFocus();
                loadID();
            }
        }else {
            Notifications notificationBuilder = Notifications.create()
                    .title("Saving UnSuccessful.!")
                    .text("Driver Not Saved, Some fields have been empty ..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            txtDriverId.requestFocus();
            loadID();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        DriverDTO dto = null;
        try {
            dto = bo.getDriver(txtSearch.getText().trim());
        } catch (Exception e) {
            Notifications notificationBuilder = Notifications.create()
                    .title("Error..!")
                    .text("Something Wrong.Please try Again..!")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
        if(dto != null){
            txtDriverId.setText(dto.getId());
            txtDriverName.setText(dto.getName());
            txtDriverAddress.setText(dto.getAddress());
            txtDriverContact.setText(dto.getContact());
        }else{
            txtSearch.setStyle("-fx-border-color: #f53b57 ");
            txtSearch.requestFocus();
            Notifications notificationBuilder = Notifications.create()
                    .title("No Driver Founded..!")
                    .text("Enter Valid Driver Id.")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
    }

    public void clear() {
        txtDriverId.setText(null);
        txtDriverName.setText(null);
        txtDriverAddress.setText(null);
        txtDriverContact.setText(null);
        txtSearch.setText(null);
    }

    public void txtDriverIdOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(DR)[0-9]{1,}$").matcher(txtDriverId.getText().trim()).matches()){
            txtDriverId.setFocusColor(Paint.valueOf("skyblue"));
            txtDriverName.requestFocus();
        }else {
            txtDriverId.setFocusColor(Paint.valueOf("red"));
            txtDriverId.requestFocus();
        }
    }

    public void txtDriverNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtDriverName.getText().trim()).matches()){
            txtDriverName.setFocusColor(Paint.valueOf("skyblue"));
            txtDriverAddress.requestFocus();
        }else {
            txtDriverName.setFocusColor(Paint.valueOf("red"));
            txtDriverName.requestFocus();
        }
    }

    public void txtDriverAddressOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| |0-9|,]{1,}$").matcher(txtDriverAddress.getText().trim()).matches()){
            txtDriverAddress.setFocusColor(Paint.valueOf("skyblue"));
            txtDriverContact.requestFocus();
        }else {
            txtDriverAddress.setFocusColor(Paint.valueOf("red"));
            txtDriverAddress.requestFocus();
        }
    }

    public void txtDriverContactOnAction(ActionEvent actionEvent)  {
        if (Pattern.compile("^(0)[0-9]{9}$").matcher(txtDriverContact.getText().trim()).matches()) {
            txtDriverContact.setFocusColor(Paint.valueOf("skyblue"));
            btnAdd.requestFocus();
            try {
                btnAddOnAction(actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            txtDriverContact.setFocusColor(Paint.valueOf("red"));
            txtDriverContact.requestFocus();
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) throws Exception {
        clear();
        loadID();
    }
}
