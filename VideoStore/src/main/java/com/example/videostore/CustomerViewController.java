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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.videostore.SaveData.saveCustomerData;
import static com.example.videostore.SaveData.saveItemData;

public class CustomerViewController implements Initializable {
    @FXML
    private Button additem;
    @FXML
    private Button returnitem;
    @FXML
    private Button updatelist;
    @FXML
    private TableColumn<Item, String> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableView<Item> rentTable;
    static ArrayList<Customer> customers;
    static ArrayList<Item> itemslist;
    static int itr;



    String test;

    public void receiveRentedItem(Item renteditem) throws IOException {
        if(customers.get(itr).checkAddRental(renteditem)) {
            customers.get(itr).addRental(renteditem);
            saveCustomerData(customers);
            saveItemData(itemslist);
            updateRental();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Guest accounts cannot rent 2-day items");
            alert.show();
        }

    }

    public static void setCustomerdata(ArrayList<Customer> customer,int i){
        customers = customer;
        itr = i;
    }

    public static void setItemdata(ArrayList<Item> item){
        itemslist = item;
    }




    @FXML
    public void updateRental(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Title"));



        rentTable.setItems(getItems());


    }

    @FXML
    public void deleteClick() throws IOException {

        Item selectItem = rentTable.getSelectionModel().getSelectedItem();

        rentTable.getItems().removeAll(
                rentTable.getSelectionModel().getSelectedItems()
        );
        customers.get(itr).removeRental(selectItem);
        customers.get(itr).autoPromote();
        customers.get(itr).printRental();
        saveCustomerData(customers);
        saveItemData(itemslist);

    }




    public ObservableList<Item>  getItems()
    {
        ObservableList<Item> item = FXCollections.observableArrayList();
        for(Item i:customers.get(itr).getRent_items()) {
            item.add(i);
        }

        return item;
    }

    @FXML
    public void addRental(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddRentalPopUp-view.fxml"));
        Parent AddRentview = loader.load();

        Scene AddRentviewscene = new Scene(AddRentview);

        AddRentalPopupController addrentalcontroller = loader.getController();
        addrentalcontroller.setItemdata(itemslist);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(AddRentviewscene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("ID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Title"));



            rentTable.setItems(getItems());
        });
    }


}
