package com.example.videostore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.videostore.SaveData.saveCustomerData;
import static com.example.videostore.SaveData.saveItemData;

public class AdminViewController implements Initializable {

    @FXML
    private TableColumn<Item, Integer > copies;

    @FXML
    private Button edit;

    @FXML
    private TableColumn<Item, String> genre;

    @FXML
    private TableColumn<Item, String> id;

    @FXML
    private TableView<Item> itemTableviewA;

    @FXML
    private TableColumn<Item, String> loan_type;

    @FXML
    private TableColumn<Item, Float> rent_fee;

    @FXML
    private TableColumn<Item, String> rent_type;

    @FXML
    private Button restock;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<Item, String> title;

    static ArrayList<Item> itemslistA;

    static ArrayList<Customer> customersA;

    private ObservableList<Item> getItems()
    {
        ObservableList<Item> item = FXCollections.observableArrayList();
        for(Item i:itemslistA) {
            item.add(i);
        }

        return item;
    }

    @FXML
    private void refreshItem(){
        id.setCellValueFactory(new PropertyValueFactory<Item, String>("ID"));
        title.setCellValueFactory(new PropertyValueFactory<Item, String>("Title"));
        rent_type.setCellValueFactory(new PropertyValueFactory<Item, String>("Rent_type"));
        loan_type.setCellValueFactory(new PropertyValueFactory<Item, String>("Loan_type"));
        copies.setCellValueFactory(new PropertyValueFactory<Item, Integer>("No_of_copies"));
        rent_fee.setCellValueFactory(new PropertyValueFactory<Item, Float>("Rent_fee"));
        genre.setCellValueFactory(new PropertyValueFactory<Item, String>("Genre"));

        itemTableviewA.setItems(getItems());


    }


    public static void setItemdataA(ArrayList<Item> item){
        itemslistA = item;
    }

    public static void setCustomerdataA(ArrayList<Customer> customer){
        customersA = customer;
    }

    @FXML
    private void addItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddItem-view.fxml"));
        DialogPane addItem = fxmlLoader.load();

        AddItemAdminController addItemController = fxmlLoader.getController();
        addItemController.setItemdataA(itemslistA);
        addItemController.setCustomerdataA(customersA);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addItem);
        dialog.setTitle("Add item");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        refreshItem();
        saveItemData(itemslistA);
    }

//    @FXML
//    private void editItem() throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("EditItem-view.fxml"));
//        DialogPane addItem = fxmlLoader.load();
//
//        EditItemAdminController editItemController = fxmlLoader.getController();
//        editItemController.setItemdataA(itemslistA);
//        editItemController.setCustomerdataA(customersA);
//        editItemController.receiveItemToEdit(itemTableviewA.getSelectionModel().getSelectedItem());
//
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setDialogPane(addItem);
//        dialog.setTitle("Edit item");
//
//        Optional<ButtonType> clickedButton = dialog.showAndWait();
//        refreshItem();
//        saveItemData(itemslistA);
//    }

    @FXML
    private void deleteItem() throws IOException {
        Item selectItem = itemTableviewA.getSelectionModel().getSelectedItem();

        for (int i =0; i < itemslistA.size(); i++){
            if(itemslistA.get(i).equals(selectItem)){
                itemslistA.remove(i);
            }
        }

        itemTableviewA.getItems().removeAll(
                itemTableviewA.getSelectionModel().getSelectedItems()
        );


        saveItemData(itemslistA);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            refreshItem();
        });
    }
}
