package com.example.videostore;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditCustomerAdminController implements Initializable {

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private ComboBox<String> customerType;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;


    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Label idCheck;
    @FXML
    private Label nameCheck;
    @FXML
    private Label addressCheck;
    @FXML
    private Label phoneCheck;
    @FXML
    private Label customerTypeCheck;
    @FXML
    private Label usernameCheck;
    @FXML
    private Label passwordCheck;



    static ArrayList<Item> customerA;
    static ArrayList<Customer> customersA;

    private Customer editCustomer;


    Pattern idpattern = Pattern.compile("^[C][0-9][0-9][0-9]$");
    Pattern phonepattern = Pattern.compile("^[0][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$");

    public void receiveCustomerToEdit(Customer customer){
        editCustomer = customer;
    }

    public static void setItemdataA(ArrayList<Item> item){
        customerA = item;
    }

    public static void setCustomerdataA(ArrayList<Customer> customer){
        customersA = customer;
    }

    @FXML
    public void confirm(ActionEvent event) throws IOException {
        Customer c = new Customer();
        boolean idValid = false;
        boolean nameValid = false;
        boolean addressValid = false;
        boolean phoneValid = false;
        boolean usernameValid = false;
        boolean passwordValid = false;

        //Check customerID
        if(checkData(id.getText(), idpattern)){
            idCheck.setText("");
            idValid = true;
        }else {
            idCheck.setText("Invalid customer ID");
        }

        if(id.getText().contentEquals(editCustomer.getID())){
            idValid = true;
        }else {
            for (Customer customer : customersA) {
                if (id.getText().contains(customer.getID())) {
                    idValid = false;
                    idCheck.setText("ID already exist");
                }
            }
        }


        //Check name
        if(!(name.getText() == null)) {
            nameCheck.setText("");
            nameValid = true;

        }else {
            nameCheck.setText("Name cannot be empty");
        }
        //Check address
        if(!(address.getText() == null)) {
            addressCheck.setText("");
            addressValid = true;

        }else {
            addressCheck.setText("Address cannot be empty");
        }
        //Check Phone
        if(checkData(phone.getText(), phonepattern)){
            phoneCheck.setText("");
            phoneValid = true;
        }else {
            phoneCheck.setText("Invalid phone number");
        }

        if(phone.getText().contentEquals(editCustomer.getPhone())){
            phoneValid = true;
        }else {
            for (Customer customer : customersA) {
                if (phone.getText().contains(customer.getPhone())) {
                    phoneValid = false;
                    phoneCheck.setText("Phone number already exist");
                }
            }
        }


        //Check username
        if(!(username.getText() == null)) {
            usernameCheck.setText("");
            usernameValid = true;

        }else {
            usernameCheck.setText("Username cannot be empty");
        }

        if(username.getText().contentEquals(editCustomer.getUsername())){
            usernameValid = true;
        }else {
            for (Customer customer : customersA) {
                if (username.getText().contains(customer.getUsername())) {
                    usernameValid = false;
                    usernameCheck.setText("Username already exist");
                }
            }
        }


        //Check password
        if(!(password.getText() == null)) {
            passwordCheck.setText("");
            passwordValid = true;

        }else {
            passwordCheck.setText("Password cannot be empty");
        }

        if(idValid && nameValid && addressValid && phoneValid && usernameValid && passwordValid){
            c.setID(id.getText());
            c.setName(name.getText());
            c.setAddress(address.getText());
            c.setPhone(phone.getText());
            c.setCustomer_type(customerType.getValue());
            c.setUsername(username.getText());
            c.setPassword(password.getText());
            c.setRent_items(editCustomer.getRent_items());

            for (int itr = 0; itr < customersA.size(); itr++){
                if(customersA.get(itr).equals(editCustomer)) {
                    customersA.set(itr, c);
                }
            }
            closeButtonAction();
        }


    }

    public boolean checkData(String id, Pattern pattern){
        if(pattern.matcher(id).find()){
            System.out.println("Match");
            return true;
        }else {
            System.out.println("does not match");
            return false;
        }
    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerType.getItems().addAll(
          "Guest",
          "Regular",
          "VIP"
        );

        Platform.runLater(() ->{
            id.setText(editCustomer.getID());
            name.setText(editCustomer.getName());
            address.setText(editCustomer.getAddress());
            phone.setText(editCustomer.getPhone());
            customerType.setValue((editCustomer.getCustomer_type()));
            username.setText((editCustomer.getUsername()));
            password.setText(editCustomer.getPassword());

        });

    }
}
