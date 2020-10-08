package controller;

import bo.BoFactory;
import bo.custom.ClientBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ClientDTO;
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
import view.tm.ClientTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class ClientFormController {
    public AnchorPane root;
    public TableView<ClientTM> tblClient;
    public JFXTextField txtClientId;
    public JFXTextField txtClientName;
    public JFXTextField txtClientAddress;
    public JFXTextField txtClientContact;
    public JFXComboBox cmbClientType;
    public TableColumn colClientId;
    public TableColumn colClientName;
    public TableColumn colClientAddress;
    public TableColumn colClientContact;
    public TableColumn colClientType;
    public TableColumn colDeleteButton;
    public TableColumn colUpdateButton;
    public TextField txtSearch;
    public JFXButton btnAdd;

    ClientBo bo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.CLIENT);
        loadId();
        getClientType();
        loadAllClient();

        colClientId.setCellValueFactory(new PropertyValueFactory("id"));
        colClientName.setCellValueFactory(new PropertyValueFactory("name"));
        colClientAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colClientContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colClientType.setCellValueFactory(new PropertyValueFactory("type"));
        colDeleteButton.setCellValueFactory(new PropertyValueFactory("btnDelete"));
        colUpdateButton.setCellValueFactory(new PropertyValueFactory("btnUpdate"));

        tblClient.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData(newValue);
                });
    }

    private void setData(ClientTM tm) {
        if(tm != null){
            txtClientId.setText(tm.getId());
            txtClientName.setText(tm.getName());
            txtClientAddress.setText(tm.getAddress());
            txtClientContact.setText(tm.getContact());
            cmbClientType.setValue(tm.getType());
        }
    }

    public void getClientType()  {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("Hotel");
        observableList.add("Restaurant");
        observableList.add("Home");
        cmbClientType.setItems(observableList);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        String id = txtClientId.getText().trim();
        String name = txtClientName.getText().trim();
        String address = txtClientAddress.getText().trim();
        String contact = txtClientContact.getText().trim();
        String type = (String) cmbClientType.getValue();

       if(txtClientId.getText().trim().length()>0 && txtClientName.getText().trim().length()>0 && txtClientAddress.getText().trim().length()>0 && txtClientContact.getText().trim().length()>0 ){
           try {
               boolean isAdded = bo.saveClient(new ClientDTO(id,name,address,contact,type));
               if (isAdded) {
                   Notifications notificationBuilder = Notifications.create()
                           .title("Saved Successfully.!")
                           .text("You have Successfully save Client to the System.")
                           .graphic(new ImageView(new Image("/assert/done.png")))
                           .hideAfter(Duration.seconds(4))
                           .position(Pos.BOTTOM_RIGHT);
                   notificationBuilder.darkStyle();
                   notificationBuilder.show();
                   loadAllClient();
                   clear();
                   txtClientId.requestFocus();
                   loadId();
               } else {
                   Notifications notificationBuilder = Notifications.create()
                           .title("Saving UnSuccessful.!")
                           .text("Client Not Saved, Try Again.!")
                           .graphic(new ImageView(new Image("/assert/errorpng.png")))
                           .hideAfter(Duration.seconds(4))
                           .position(Pos.BOTTOM_RIGHT);
                   notificationBuilder.darkStyle();
                   notificationBuilder.show();
                   txtClientId.requestFocus();
                   loadId();
               }
           } catch (SQLException se){
               Notifications notificationBuilder = Notifications.create()
                       .title("Saving UnSuccessful.!")
                       .text("Client Not Saved, Something Wrong..!")
                       .graphic(new ImageView(new Image("/assert/errorpng.png")))
                       .hideAfter(Duration.seconds(4))
                       .position(Pos.BOTTOM_RIGHT);
               notificationBuilder.darkStyle();
               notificationBuilder.show();
               loadId();
               txtClientId.requestFocus();
           } catch (Exception e) {
               Notifications notificationBuilder = Notifications.create()
                       .title("Saving UnSuccessful.!")
                       .text("Client Not Saved, Something Wrong..!")
                       .graphic(new ImageView(new Image("/assert/errorpng.png")))
                       .hideAfter(Duration.seconds(4))
                       .position(Pos.BOTTOM_RIGHT);
               notificationBuilder.darkStyle();
               notificationBuilder.show();
               txtClientId.requestFocus();
               loadId();
           }
       }else {
           Notifications notificationBuilder = Notifications.create()
                   .title("Saving UnSuccessful.!")
                   .text("Client Not Saved, Some fields have been empty ..!")
                   .graphic(new ImageView(new Image("/assert/errorpng.png")))
                   .hideAfter(Duration.seconds(4))
                   .position(Pos.BOTTOM_RIGHT);
           notificationBuilder.darkStyle();
           notificationBuilder.show();
           txtClientId.requestFocus();
       }

    }

    public void loadAllClient()  {
        ArrayList<ClientDTO> clientDTOS = null;
        try {
            clientDTOS = bo.getAllClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<ClientTM> tmList = FXCollections.observableArrayList();
        for (ClientDTO dto : clientDTOS) {
            Button btnDelete = new Button("Delete");
            btnDelete.setMaxSize(100, 50);
            btnDelete.setCursor(Cursor.HAND);
            btnDelete.setStyle("-fx-font-weight: bold ; -fx-background-color:  #D50000; ");
            btnDelete.setTextFill(Color.web("#FFFFFF"));
            Button btnUpdate = new Button("Update");
            btnUpdate.setMaxSize(100, 50);
            btnUpdate.setCursor(Cursor.HAND);
            btnUpdate.setStyle("-fx-font-weight: bold ; -fx-background-color:  #00BFA5;");
           ClientTM tm = new ClientTM(dto.getId(), dto.getName(),dto.getAddress(),
                    dto.getContact(), dto.getType(),btnDelete,btnUpdate);
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
                        if (bo.deleteClient(tm.getId())) {
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Delete Successful.!")
                                    .text("You have Successfully Delete Client from the System.")
                                    .graphic(new ImageView(new Image("/assert/done.png")))
                                    .hideAfter(Duration.seconds(4))
                                    .position(Pos.BOTTOM_RIGHT);
                            notificationBuilder.darkStyle();
                            notificationBuilder.show();
                            loadAllClient();
                            clear();
                            loadId();
                            return;
                        }
                        Notifications notificationBuilder = Notifications.create()
                                .title("Delete UnSuccessful.!")
                                .text("Client Not Deleted, Please try Again..!")
                                .graphic(new ImageView(new Image("/assert/errorpng.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }

                } catch (Exception e1) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Delete UnSuccessful.!")
                            .text("Client Not Deleted, Please try Again..!")
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
                        if (bo.updateClient(new ClientDTO(txtClientId.getText(), txtClientName.getText(),
                                txtClientAddress.getText(), txtClientContact.getText(), (String) cmbClientType.getValue()))) {
                            Notifications notificationBuilder = Notifications.create()
                                    .title("Update Successful.!")
                                    .text("You have Successfully Update Client from the System.")
                                    .graphic(new ImageView(new Image("/assert/done.png")))
                                    .hideAfter(Duration.seconds(4))
                                    .position(Pos.BOTTOM_RIGHT);
                            notificationBuilder.darkStyle();
                            notificationBuilder.show();
                            loadAllClient();
                            clear();
                            loadId();
                            return;
                        }
                        Notifications notificationBuilder = Notifications.create()
                                .title("Update UnSuccessful.!")
                                .text("Client Not Updated, Please try Again..!")
                                .graphic(new ImageView(new Image("/assert/errorpng.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }

                } catch (Exception e1) {
                    Notifications notificationBuilder = Notifications.create()
                            .title("Update UnSuccessful.!")
                            .text("Client Not Updated, Please try Again..!")
                            .graphic(new ImageView(new Image("/assert/errorpng.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                }
            });
        }
        tblClient.setItems(tmList);

        FilteredList<ClientTM> filteredData = new FilteredList<>(tmList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getId().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (client.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (client.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (client.getContact().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (client.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<ClientTM> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblClient.comparatorProperty());
        tblClient.setItems(sortedData);
    }

    public void clear() {
        txtClientId.clear();
        txtClientName.clear();
        txtClientAddress.clear();
        txtClientContact.clear();
        txtSearch.clear();
        cmbClientType.setValue(null);
    }

    private void loadId()  {
        String id = null;
        try {
            id = bo.getClientID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtClientId.setText(id);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {

        ClientDTO dto = null;
        try {
            dto = bo.getClient(txtSearch.getText().trim());
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
            txtClientId.setText(dto.getId());
            txtClientName.setText(dto.getName());
            txtClientAddress.setText(dto.getAddress());
            txtClientContact.setText(dto.getContact());
            cmbClientType.setValue(dto.getType());
        }else{
            txtSearch.setStyle("-fx-border-color: #f53b57 ");
            txtSearch.requestFocus();
            Notifications notificationBuilder = Notifications.create()
                    .title("No Client Founded..!")
                    .text("Enter Valid Client Id.")
                    .graphic(new ImageView(new Image("/assert/errorpng.png")))
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }
    }

    public void txtClientIdOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(C)[0-9]{1,}$").matcher(txtClientId.getText().trim()).matches()){
                txtClientId.setFocusColor(Paint.valueOf("skyblue"));
                txtClientName.requestFocus();
        }else {
            txtClientId.setFocusColor(Paint.valueOf("red"));
            txtClientId.requestFocus();
        }
    }

    public void txtClientNameOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| ]{1,}$").matcher(txtClientName.getText().trim()).matches()){
            txtClientName.setFocusColor(Paint.valueOf("skyblue"));
            txtClientAddress.requestFocus();
        }else {
            txtClientName.setFocusColor(Paint.valueOf("red"));
            txtClientName.requestFocus();
        }
    }

    public void txtClientAddressOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^[A-z| |0-9|,]{1,}$").matcher(txtClientAddress.getText().trim()).matches()){
            txtClientAddress.setFocusColor(Paint.valueOf("skyblue"));
            txtClientContact.requestFocus();
        }else {
            txtClientAddress.setFocusColor(Paint.valueOf("red"));
            txtClientAddress.requestFocus();
        }
    }

    public void txtClientContactOnAction(ActionEvent actionEvent) {
        if(Pattern.compile("^(0)[0-9]{9}$").matcher(txtClientContact.getText().trim()).matches()){
            txtClientContact.setFocusColor(Paint.valueOf("skyblue"));
            cmbClientType.requestFocus();
        }else {
            txtClientContact.setFocusColor(Paint.valueOf("red"));
            txtClientContact.requestFocus();
        }
    }

}
