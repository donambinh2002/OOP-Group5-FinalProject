package com.example.videostore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    private Label idCheck;
    @FXML
    private Label titleCheck;
    @FXML
    private Label rentTypeCheck;
    @FXML
    private Label loanTypeCheck;
    @FXML
    private Label copiesCheck;
    @FXML
    private Label feeCheck;
    @FXML
    private Label genreCheck;
    @FXML
    private Label genreLabel;


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
        boolean copiesValid = false;
        boolean feeValid = false;
        boolean idValid = false;
        boolean titleValid = false;
        boolean rentTypeValid = false;
        boolean loanTypeValid = false;

        //Check id format
        if(checkData(id.getText(), idpattern)){
            idValid = true;
        }else {
            idCheck.setText("Invalid id");
        }
        //Check title
        if(!title.getText().trim().isEmpty()) {
            titleValid = true;
        }else {
            titleCheck.setText("Title cannot be empty");
        }
        //Check rent type
        if(!(rentType.getValue() == null)) {
            if (checkData(rentType.getValue(), rentTypepattern)) {
                rentTypeValid = true;
            } else {
                rentTypeCheck.setText("Invalid Rent Type");
            }
        }else {
            rentTypeCheck.setText("Rent Type cannot be empty");
        }
        //Check loan type
        if(!(loanType.getValue() == null)) {
            if (checkData(loanType.getValue(), loanTypepattern)) {
                loanTypeValid = true;
            } else {
                loanTypeCheck.setText("Invalid Loan Type");
            }
        }else {
            loanTypeCheck.setText("Loan Type cannot be empty");
        }
        //Check copies
        try{
            Integer.parseInt(copies.getText());
            copiesValid = true;
        }catch (Exception e){
            copiesCheck.setText("Invalid number of copies");
        }
        if(copiesValid){
            if(Integer.parseInt(copies.getText()) > 0){
                i.setNo_of_copies(Integer.parseInt(copies.getText()));
            }else {
                copiesValid = false;
                copiesCheck.setText("Copies must be greater than 0");
            }
        }
        //Check fee
        try{
            Float.parseFloat(fee.getText());
            feeValid = true;
        }catch (Exception e){
            feeCheck.setText("Invalid fee");
        }
        if(feeValid){
            if(Float.parseFloat(fee.getText()) > 0){
                i.setRent_fee(Float.parseFloat(fee.getText()));
            }else {
                feeValid = false;
                feeCheck.setText("Fee must be greater than 0");
            }
        }
        //Check if id is unique
        for(Item item:itemslistA){
            if(item.getID().contentEquals(id.getText())){
                idValid = false;
                idCheck.setText("ID already exist");
            }
        }



        if(idValid && titleValid && rentTypeValid && loanTypeValid && copiesValid && feeValid){
            i.setID(id.getText());
            i.setTitle(title.getText());
            i.setRent_type(rentType.getValue());
            i.setLoan_type(loanType.getValue());
            i.setNo_of_copies(Integer.parseInt(copies.getText()));
            i.setRent_fee(Float.parseFloat(fee.getText()));
            if(rentType.getValue().contentEquals("Game")){
                i.setGenre(null);
            }else {
                i.setGenre(genre.getValue());
            }
            i.printItem();
            itemslistA.add(i);
        }

    }

    public boolean checkData(String id, Pattern pattern){
        if(idpattern.matcher(id).find()){
            System.out.println("Match");
            return true;
        }else {
            System.out.println("does not match");
            return false;
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rentType.getItems().addAll(
          "Record",
          "DVD",
          "Game"
        );
        rentType.setOnAction(e ->{
            if(rentType.getValue().contentEquals("Game")) {
                genreLabel.setVisible(false);
                genre.setVisible(false);
            }else {
                genreLabel.setVisible(true);
                genre.setVisible(true);
            }
        });
        loanType.getItems().addAll(
                "2-day",
                "1-week"
        );
        genre.getItems().addAll(
                "Action",
                "Horror",
                "Drama",
                "Comedy"
        );
    }
}
