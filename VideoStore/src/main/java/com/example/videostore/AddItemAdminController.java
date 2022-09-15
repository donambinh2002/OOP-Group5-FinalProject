package com.example.videostore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddItemAdminController implements Initializable {

    @FXML
    private TextField copies;

    @FXML
    private TextField fee;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private TextField id;

    @FXML
    private ComboBox<String> loanType;

    @FXML
    private ComboBox<String> rentType;

    @FXML
    private TextField title;

    @FXML
    private Button confirm;
    @FXML
    private Button cancel;

    static ArrayList<Item> itemslistA;
    static ArrayList<Customer> customersA;

    Pattern idpattern = Pattern.compile("^[I][0-9][0-9][0-9][-][0-9][0-9][0-9][0-9]$");

    Pattern rentTypepattern = Pattern.compile("^[Record|DVD|Game]$");

    Pattern loanTypepattern = Pattern.compile("^[2-day|1-week]$");

    Pattern genrepattern = Pattern.compile("^[Action|Horror|Drama|Comedy]$");
    public static void setItemdataA(ArrayList<Item> item){
        itemslistA = item;
    }

    public static void setCustomerdataA(ArrayList<Customer> customer){
        customersA = customer;
    }

    public void confirm(){
        Item i = null;
        if(checkID(id.getText())){
            i.setID(id.getText());
        }
        i.setTitle(title.getText());

    }

    public boolean checkID(String id){
        if(idpattern.matcher(id).find()){
            System.out.println("Match");
            return true;
        }else {
            System.out.println("does not match");
            return false;
        }
    }

    public boolean checkRentType(String id){
        if(rentTypepattern.matcher(id).find()){
            System.out.println("Match");
            return true;
        }else {
            System.out.println("does not match");
            return false;
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
