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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRentalPopupController implements Initializable {

    @FXML
    private Button cancel;

    @FXML
    private Button confirm;

    @FXML
    private Button refresh;

    @FXML
    private TableColumn<Item, Integer> itemCopies;

    @FXML
    private TableColumn<Item, Float> itemFee;

    @FXML
    private TableColumn<Item, String> itemGenre;

    @FXML
    private TableColumn<Item, String> itemLoanType;

    @FXML
    private TableColumn<Item, String> itemRentType;

    @FXML
    private TableColumn<Item, String> itemTitle;

    @FXML
    private TableColumn<Item, String> itemid;

    @FXML
    private TableView<Item> itemTableView;

    static ArrayList<Item> itemslist;

    public ObservableList<Item> getItems()
    {
        ObservableList<Item> item = FXCollections.observableArrayList();
        for(Item i:itemslist) {
            item.add(i);
        }

        return item;
    }

    @FXML
    public void refreshItem(){
        itemid.setCellValueFactory(new PropertyValueFactory<Item, String>("ID"));
        itemTitle.setCellValueFactory(new PropertyValueFactory<Item, String>("Title"));
        itemRentType.setCellValueFactory(new PropertyValueFactory<Item, String>("Rent_type"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<Item, String>("Loan_type"));
        itemCopies.setCellValueFactory(new PropertyValueFactory<Item, Integer>("No_of_copies"));
        itemFee.setCellValueFactory(new PropertyValueFactory<Item, Float>("Rent_fee"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<Item, String>("Genre"));

        itemTableView.setItems(getItems());


    }
    public static void setItemdata(ArrayList<Item> item){
        itemslist = item;
    }

    public void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Customer-view.fxml"));
        Parent customerview = loader.load();

        Scene customerviewscene = new Scene(customerview);


        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(customerviewscene);
        window.show();
    }

    public void confirm(ActionEvent event) throws IOException {
        Item selectItem = itemTableView.getSelectionModel().getSelectedItem();
        if(selectItem.getNo_of_copies() > 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Customer-view.fxml"));
            Parent customerview = loader.load();

            Scene customerviewscene = new Scene(customerview);
            CustomerViewController customercontroller = loader.getController();
            customercontroller.receiveRentedItem(selectItem);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(customerviewscene);
            window.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Item is out of stock!");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            refreshItem();
        });
    }
}
