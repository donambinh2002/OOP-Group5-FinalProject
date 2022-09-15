package com.example.videostore;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditItemAdminController implements Initializable {

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

    private Item editItem = new Item();

    Pattern idpattern = Pattern.compile("^[I][0-9][0-9][0-9][-][0-9][0-9][0-9][0-9]$");


    Pattern rentTypepattern = Pattern.compile("^[Record|DVD|Game]$");

    Pattern loanTypepattern = Pattern.compile("^[2-day|1-week]$");



    public void receiveItemToEdit(Item item){
        editItem = item;
    }
    public static void setItemdataA(ArrayList<Item> item){
        itemslistA = item;
    }

    public static void setCustomerdataA(ArrayList<Customer> customer){
        customersA = customer;
    }

    @FXML
    public void confirm(ActionEvent event) throws IOException {
        Item i = new Item();
        boolean copiesValid = false;
        boolean feeValid = false;
        boolean idValid = false;
        boolean titleValid = false;
        boolean rentTypeValid = false;
        boolean loanTypeValid = false;

        //Check id format
        if(checkData(id.getText(), idpattern)){
            idCheck.setText("");
            idValid = true;
        }else {
            idCheck.setText("Invalid id");
        }
        //Check title
        if(!title.getText().trim().isEmpty()) {
            titleCheck.setText("");
            titleValid = true;
        }else {
            titleCheck.setText("Title cannot be empty");
        }
        //Check rent type
        if(!(rentType.getValue() == null)) {

                rentTypeCheck.setText("");
                rentTypeValid = true;


        }else {
            rentTypeCheck.setText("Rent Type cannot be empty");
        }
        //Check loan type
        if(!(loanType.getValue() == null)) {

                loanTypeCheck.setText("");
                loanTypeValid = true;

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
                copiesCheck.setText("");
                copiesValid = true;
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
                feeCheck.setText("");
                feeValid = true;
            }else {
                feeValid = false;
                feeCheck.setText("Fee must be greater than 0");
            }
        }
        //Check if id is unique
        for(Item item:itemslistA){
            if(id.getText().contains(item.getID().substring(0,4))){
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
            for (int itr = 0; itr < itemslistA.size(); itr++){
                if(itemslistA.get(itr).equals(editItem)) {
                    itemslistA.set(itr, i);
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
        Platform.runLater(() ->{
            id.setText(editItem.getID());
            title.setText(editItem.getTitle());
            rentType.setValue(editItem.getRent_type());
            loanType.setValue(editItem.getLoan_type());
            copies.setText(Integer.toString(editItem.getNo_of_copies()));
            fee.setText(Float.toString(editItem.getRent_fee()));
            genre.setValue(editItem.getGenre());

        });
    }
}
