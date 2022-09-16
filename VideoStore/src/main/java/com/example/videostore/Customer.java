package com.example.videostore;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.example.videostore.Item.*;

public class Customer {
    private String ID;
    private String Name;
    private String Address;
    private String Phone;
    private int Number_of_rentals;
    private String Customer_type;
    private String Username;
    private String Password;
    private ArrayList<Item> Rent_items = new ArrayList<>();

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getNumber_of_rentals() {
        return Number_of_rentals;
    }

    public void setNumber_of_rentals(int number_of_rentals) {
        Number_of_rentals = number_of_rentals;
    }

    public String getCustomer_type() {
        return Customer_type;
    }

    public void setCustomer_type(String customer_type) {
        Customer_type = customer_type;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ArrayList<Item> getRent_items() {
        return Rent_items;
    }

    public void setRent_items(ArrayList<Item> rent_items) {
        Rent_items = rent_items;
    }

    public Customer(){
    }

    public Customer(String ID, String name, String address, String phone, int number_of_rentals, String customer_type, String username, String password) {
        this.ID = ID;
        Name = name;
        Address = address;
        Phone = phone;
        Number_of_rentals = number_of_rentals;
        Customer_type = customer_type;
        Username = username;
        Password = password;

    }


    public static ArrayList<Customer> readCustomers(String filename, ArrayList<Item> list_items) throws FileNotFoundException {



        int customer_pos = -1;
        File customer = new File(filename);
        Scanner sc = new Scanner(customer);

        ArrayList<Customer> customerlist = new ArrayList<Customer>();

        while(sc.hasNextLine()){
            String line = sc.nextLine();

            String[] data = line.split(",");

            if(data[0].startsWith("C")) {
                Customer c = new Customer(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), Integer.parseInt(data[4].trim()), data[5].trim(), data[6].trim(), data[7].trim());
                customerlist.add(c);
                customer_pos++;
            }
            if(data[0].startsWith("I")){
                for(int j = 0; j < list_items.size(); j++){
                    if(list_items.get(j).getID().contains(data[0])){ //If statement is the problem
                        customerlist.get(customer_pos).addRental(list_items.get(j)); // Add items into customer rent if match
                    }
                }
            }


        }
        for(int i = 0; i < customerlist.size(); i++){
            customerlist.get(i).printCustomer();
            customerlist.get(i).printRental();
        }
        return customerlist;
    }

    public void printCustomer(){
        System.out.println(this.ID);
        System.out.println(this.Name);
        System.out.println(this.Address);
        System.out.println(this.Phone);
        System.out.println(this.Number_of_rentals);
        System.out.println(this.Customer_type);
        System.out.println(this.Username);
        System.out.println(this.Password);
    }

    public boolean addRental(Item item){
        if(item.getNo_of_copies() > 0) {
            this.Rent_items.add(item);
            item.setNo_of_copies(item.getNo_of_copies() - 1);
            return true;
        }else {
            System.out.println("Out of stock");
            return false;
        }
    }

    public void removeRental(Item item){
        if(this.Rent_items.contains(item)){
            item.setNo_of_copies(item.getNo_of_copies() + 1);
            this.Rent_items.remove(item);
        }else {
            System.out.println("Customer doesn't have that item");
        }
    }


    public void printRental() {
        for(Item item: this.Rent_items) {
            System.out.println(item.getID());
        }
    }

    public String printTextfile(){
        String text;
        text = this.getID() + ","+ this.getName() + ","+ this.getAddress() + "," + this.getPhone() + "," + this.getNumber_of_rentals() + "," + this.getCustomer_type() + "," + this.getUsername() + "," + this.getPassword();
        return text;
    }


}
