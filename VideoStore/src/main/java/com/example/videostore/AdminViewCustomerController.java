package com.example.videostore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.videostore.SaveData.saveCustomerData;
import static com.example.videostore.SaveData.saveItemData;

public class AdminViewCustomerController implements Initializable {


    @FXML
    private Button edit;


    @FXML
    private TableColumn<Customer, String> id;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> phone;

    @FXML
    private TableColumn<Customer, String> customerType;

    @FXML
    private TableColumn<Customer, String> address;

    @FXML
    private Button restock;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> username;

    @FXML
    private TableColumn<Customer, String> password;


    static ArrayList<Item> itemslistA;

    static ArrayList<Customer> customersA;

    private ObservableList<Customer> getCustomer()
    {
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        for(Customer c:customersA) {
            customer.add(c);
        }

        return customer;
    }

    @FXML
    private void refreshCustomer(){
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerType.setCellValueFactory(new PropertyValueFactory<>("Customer_type"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        password.setCellValueFactory(new PropertyValueFactory<>("Password"));

        customerTableView.setItems(getCustomer());


    }


    public static void setItemdataA(ArrayList<Item> item){
        itemslistA = item;
    }

    public static void setCustomerdataA(ArrayList<Customer> customer){
        customersA = customer;
    }

    @FXML
    private void addCustomer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddCustomer-view.fxml"));
        DialogPane addItem = fxmlLoader.load();

        AddCustomerAdminController addcustomercontroller = fxmlLoader.getController();
        addcustomercontroller.setItemdataA(itemslistA);
        addcustomercontroller.setCustomerdataA(customersA);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addItem);
        dialog.setTitle("Add item");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        refreshCustomer();
        saveCustomerData(customersA);
    }

    @FXML
    private void editCustomer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("EditItem-view.fxml"));
        DialogPane addItem = fxmlLoader.load();

        EditItemAdminController editItemController = fxmlLoader.getController();
        editItemController.setItemdataA(itemslistA);
        editItemController.setCustomerdataA(customersA);


        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addItem);
        dialog.setTitle("Edit item");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        refreshCustomer();
        saveItemData(itemslistA);
    }

    @FXML
    private void deleteCustomer() throws IOException {
        Customer selectCustomer = customerTableView.getSelectionModel().getSelectedItem();

        for (int i =0; i < customersA.size(); i++){
            if(customersA.get(i).equals(selectCustomer)){
                customersA.remove(i);
            }
        }

        customerTableView.getItems().removeAll(
                customerTableView.getSelectionModel().getSelectedItems()
        );


        saveItemData(itemslistA);
    }


    @FXML
    public void switchItem(ActionEvent event) throws IOException {
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("Admin-view.fxml"));
        Parent adminview = loader2.load();

        Scene adminviewscene = new Scene(adminview);
        AdminViewController adminviewcontroller = loader2.getController();
        adminviewcontroller.setItemdataA(itemslistA);
        adminviewcontroller.setCustomerdataA(customersA);

        Stage window2 = (Stage)((Node)event.getSource()).getScene().getWindow();
        window2.setScene(adminviewscene);
        window2.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            refreshCustomer();
        });
    }
}
