package com.example.videostore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Item {
    private String ID;
    private String Title;
    private String Rent_type;
    private String Loan_type;
    private int No_of_copies;
    private float Rent_fee;
    private String Genre; //If item is a DVD or video record



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRent_type() {
        return Rent_type;
    }

    public void setRent_type(String rent_type) {
        Rent_type = rent_type;
    }

    public String getLoan_type() {
        return Loan_type;
    }

    public void setLoan_type(String loan_type) {
        Loan_type = loan_type;
    }

    public int getNo_of_copies() {
        return No_of_copies;
    }

    public void setNo_of_copies(int no_of_copies) {
        No_of_copies = no_of_copies;
    }

    public float getRent_fee() {
        return Rent_fee;
    }

    public void setRent_fee(float rent_fee) {
        Rent_fee = rent_fee;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public Item(String ID, String title, String rent_type, String loan_type, int no_of_copies, float rent_fee) {
        this.ID = ID;
        Title = title;
        Rent_type = rent_type;
        Loan_type = loan_type;
        No_of_copies = no_of_copies;
        Rent_fee = rent_fee;

    }

    public Item() {

    }

    public static ArrayList<Item> readItem(String filename) throws FileNotFoundException {
        File item = new File(filename);
        Scanner sc = new Scanner(item);

        ArrayList<Item> itemlist = new ArrayList<Item>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();

            String[] data = line.split(",");

            Item it;

            if(data.length < 7) {
                it = new Item(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]));
            }else {
                it = new Item(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]));
                it.setGenre(data[6]);
            }
            itemlist.add(it);
        }
//        for(int i = 0; i < itemlist.size(); i++){
//            itemlist.get(i).printItem();
//
//        }
        return itemlist;
    }

    public void printItem(){
        System.out.println(this.ID);
        System.out.println(this.Title);
        System.out.println(this.Rent_type);
        System.out.println(this.Loan_type);
        System.out.println(this.No_of_copies);
        System.out.println(this.Rent_fee);
        System.out.println(this.Genre);

    }

    public String printITextfile(){
        String text;
        if(this.getGenre() == null) {
            text = this.getID().trim() + "," + this.getTitle().trim() + "," + this.getRent_type().trim() + "," + this.getLoan_type().trim() + "," + this.getNo_of_copies() + "," + this.getRent_fee() ;
        }else{
            text = this.getID().trim() + "," + this.getTitle().trim() + "," + this.getRent_type().trim() + "," + this.getLoan_type().trim() + "," + this.getNo_of_copies() + "," + this.getRent_fee() + "," + this.getGenre().trim();
        }
        return text;
    }





}
