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
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    public void imgBackToHome(MouseEvent mouseEvent) throws IOException {
        this.root.getChildren().clear();
        this.root.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/DefaultForm.fxml")));
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws Exception {
        String id = txtClientId.getText().trim();
        txtClientName.requestFocus();
        String name = txtClientName.getText().trim();
        String address = txtClientAddress.getText().trim();
        String contact = txtClientContact.getText().trim();
        String type = (String) cmbClientType.getValue();

       if(id.length()>0 && name.length()>0 && address.length()>0 && contact.length()>0 && type != null){
           try {
               boolean isAdded = bo.saveClient(new ClientDTO(id,name,address,contact,type));
               if (isAdded) {
                   new Alert(Alert.AlertType.CONFIRMATION, "Saved",ButtonType.OK).showAndWait();
                   loadAllClient();
                   clear();
                   txtClientId.requestFocus();
                   loadId();
               } else {
                   new Alert(Alert.AlertType.CONFIRMATION, " Not Saved, Try Again",ButtonType.OK).show();
                   txtClientId.requestFocus();
                   loadId();
               }
           } catch (SQLException se){
               new Alert(Alert.AlertType.ERROR, "SQL Syntax Error",ButtonType.OK).show();
               loadId();
               txtClientId.requestFocus();
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Error",ButtonType.OK).show();
               txtClientId.requestFocus();
               loadId();
           }
       }else {
           new Alert(Alert.AlertType.WARNING, "Some Field is Empty",ButtonType.OK).show();
           txtClientId.requestFocus();
       }

    }

    public void loadAllClient() throws Exception {
        ArrayList<ClientDTO> clientDTOS = bo.getAllClient();
        ObservableList<ClientTM> tmList = FXCollections.observableArrayList();
        for (ClientDTO dto : clientDTOS) {
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
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Deleted", ButtonType.OK).show();
                            loadAllClient();
                            clear();
                            loadId();
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
                        if (bo.updateClient(new ClientDTO(txtClientId.getText(), txtClientName.getText(),
                                txtClientAddress.getText(), txtClientContact.getText(), (String) cmbClientType.getValue()))) {
                            new Alert(Alert.AlertType.CONFIRMATION,
                                    "Updated", ButtonType.OK).show();
                            loadAllClient();
                            clear();
                            loadId();
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
        txtClientId.setText(null);
        txtClientName.setText(null);
        txtClientAddress.setText(null);
        txtClientContact.setText(null);
        txtSearch.setText(null);
        cmbClientType.setValue(null);
    }

    private void loadId() throws Exception {
        String id = bo.getClientID();
        txtClientId.setText(id);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws Exception {

        ClientDTO dto = bo.getClient(txtSearch.getText().trim());
        if(dto != null){
            txtClientId.setText(dto.getId());
            txtClientName.setText(dto.getName());
            txtClientAddress.setText(dto.getAddress());
            txtClientContact.setText(dto.getContact());
            cmbClientType.setValue(dto.getType());
        }else{
            txtSearch.setStyle("-fx-border-color: #f53b57 ");
            txtSearch.requestFocus();
            new Alert(Alert.AlertType.INFORMATION,
                    "Enter Valid Client Id", ButtonType.OK).show();
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
