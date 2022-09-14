package com.example.videostore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static com.example.videostore.Customer.*;

public class SaveData {
    public static void saveCustomerData(ArrayList<Customer> customers) throws IOException {
        File file = new File("customertest.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        for(int i = 0; i < customers.size(); i++) {
            pw.println(customers.get(i).printTextfile());
            for(Item item: customers.get(i).getRent_items()){
                pw.println(item.getID());
            }
        }

        pw.close();
    }

    public static void saveItemData(ArrayList<Item> item) throws IOException {
        File file = new File("itemtest.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        for(int i = 0; i < item.size(); i++) {
            pw.println(item.get(i).printITextfile());

        }

        pw.close();
    }
}
