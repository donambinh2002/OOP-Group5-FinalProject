package com.example.videostore;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.videostore.Customer.readCustomers;
import static com.example.videostore.Item.readItem;

public class LoginController implements Initializable {

    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label alert;

    private Stage stage;
    private Scene scene;
    private Parent root;





    @FXML
    private void checkLogin(ActionEvent event) throws IOException {
        boolean userExist = false, pass = false;
        ArrayList<Item> thing = readItem("VideoStore/doc/items.txt");
        ArrayList<Customer> check = readCustomers("VideoStore/doc/customers.txt", thing);
        if(username.getText().isEmpty() && password.getText().isEmpty()){
            alert.setText("Please enter your login information");
        }
        for(int i = 0; i < check.size();i++){
            if(username.getText().contentEquals(check.get(i).getUsername())){
                userExist = true;
                if(password.getText().contentEquals(check.get(i).getPassword()) ) {
                    //Function to change scene or something
                    pass = true;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("Customer-view.fxml"));
                    Parent customerview = loader.load();

                    Scene customerviewscene = new Scene(customerview);

                    CustomerViewController customercontroller = loader.getController();
                    customercontroller.setCustomerdata(check, i);
                    customercontroller.setItemdata(thing);


                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(customerviewscene);
                    window.show();

                    alert.setText("login successful");
                }
            }
            if(username.getText().contentEquals("admin") && password.getText().contentEquals("password")){
                FXMLLoader loader2 = new FXMLLoader();
                loader2.setLocation(getClass().getResource("Admin-view.fxml"));
                Parent adminview = loader2.load();

                Scene adminviewscene = new Scene(adminview);
                AdminViewController adminviewcontroller = loader2.getController();
                adminviewcontroller.setItemdataA(thing);
                adminviewcontroller.setCustomerdataA(check);

                Stage window2 = (Stage)((Node)event.getSource()).getScene().getWindow();
                window2.setScene(adminviewscene);
                window2.show();
            }
        }
        if(!userExist && !username.getText().isEmpty()){
            alert.setText("User does not exist");
        } else if (userExist && !pass){
            alert.setText("Incorrect password");
        }



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}