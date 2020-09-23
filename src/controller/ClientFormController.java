package controller;

import bo.BoFactory;
import bo.custom.ClientBo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ClientDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import view.tm.ClientTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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
    ClientBo bo;

    public void initialize() throws Exception {
        bo = BoFactory.getInstance().getBo(BoFactory.BOType.CLIENT);
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
        txtClientId.setText(tm.getId());
        txtClientName.setText(tm.getName());
        txtClientAddress.setText(tm.getAddress());
        txtClientContact.setText(tm.getContact());
       // cmbClientType.requestFocus();
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

    public void btnAddOnAction(ActionEvent actionEvent)  {

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
                   new Alert(Alert.AlertType.CONFIRMATION, "Saved").showAndWait();
                   loadAllClient();
                   clear();
               } else {
                   new Alert(Alert.AlertType.CONFIRMATION, " Not Saved, Try Again").show();
               }
           } catch (SQLException se){
               new Alert(Alert.AlertType.ERROR, "SQL Syntax Error").show();
           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Error").show();
           }
       }else {
           new Alert(Alert.AlertType.WARNING, "Text Field is Empty").show();
       }

    }

    public void loadAllClient() throws Exception {
        ArrayList<ClientDTO> clientDTOS = bo.getAllClient();
        ObservableList<ClientTM> tmList = FXCollections.observableArrayList();
        for (ClientDTO dto : clientDTOS) {
            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-font-weight: bold ; -fx-background-color:  #D50000; ");
            btnDelete.setTextFill(Color.web("#FFFFFF"));
           // btnDelete.setStyle("-fx-background-image: url('/assert/bin.png')");
           // btnDelete.setStyle("-fx-font-size:20");
            //btnDelete.setStyle("-fx-font-weight: bold;");
            Button btnUpdate = new Button("Update");
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
    }

    public void clear() {
        txtClientId.setText(null);
        txtClientName.setText(null);
        txtClientAddress.setText(null);
        txtClientContact.setText(null);
      //  cmbClientType.requestFocus();
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {

    }
}
