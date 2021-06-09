/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author BradySheldon_219053715
 */
public class DisplayValues {

ObjectInputStream outputDisplay;
FileWriter writer;
BufferedWriter buffWriter;
ArrayList<Customer> Arraycustomer= new ArrayList<Customer>();
ArrayList<Supplier> Arraysupplier= new ArrayList<Supplier>();


//Question_2A)
    public void openFileRead(){
        try{
            outputDisplay = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file created and opened for reading  ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    public void readFile(){
        try{
           while(true){
               Object line = outputDisplay.readObject();
               String B ="Customer";
               String s = "Supplier";
               String name = line.getClass().getSimpleName();
               if ( name.equals(B)){
                   Arraycustomer.add((Customer)line);
               } else if ( name.equals(s)){
                   Arraysupplier.add((Supplier)line);
               } else {
                   System.out.println("It didn't work");
               }
           } 
        }
        catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Class error reading ser file: "+ ioe);
        }
        catch (IOException ioe) {
            System.out.println("Error reading ser file: "+ ioe);
        }
        
        sortCustomerDisplay();
        sortSuppliersDisplay();
    }
    public void readCloseFile(){
        try{
            outputDisplay.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    //Question_2B)
    public void sortCustomerDisplay(){
        String[] sortID = new String[Arraycustomer.size()];
        ArrayList<Customer> sortA= new ArrayList<Customer>();
        int count = Arraycustomer.size();
        for (int i = 0; i < count; i++) {
            sortID[i] = Arraycustomer.get(i).getStHolderId();
        }
        Arrays.sort(sortID);
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sortID[i].equals(Arraycustomer.get(j).getStHolderId())){
                    sortA.add(Arraycustomer.get(j));
                }
            }
        }
        Arraycustomer.clear();
        Arraycustomer = sortA;
    }
    
    //Question_2C)
    public int getAge(String dob){
        String[] seperation = dob.split("-");
        
        LocalDate birth = LocalDate.of(Integer.parseInt(seperation[0]), Integer.parseInt(seperation[1]), Integer.parseInt(seperation[2]));
        LocalDate current = LocalDate.now();
        Period difference = Period.between(birth, current);
        int age = difference.getYears();
        return age;
    }
    
    //Question_2D)
    public String formatDob(Customer dob){
        LocalDate dateOfBirthToFormat = LocalDate.parse(dob.getDateOfBirth());
        DateTimeFormatter changeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dateOfBirthToFormat.format(changeFormat);
    }
    
    
    
     //Question_2E)
    public void displayCustomersoutfile(){
        try{
            writer = new FileWriter("customerOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================CUSTOMERS========================================"));
            
            buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s\n", "ID","Name","Surname","Date of Birth","Age"));
             buffWriter.write(String.format("%s\n","================================================================================"));
            for (int i = 0; i < Arraycustomer.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", Arraycustomer.get(i).getStHolderId(), Arraycustomer.get(i).getFirstName(), Arraycustomer.get(i).getSurName(), formatDob(Arraycustomer.get(i)),getAge(Arraycustomer.get(i).getDateOfBirth())));
            }
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n",rent()));
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    
    
    
    // Question_2f)
    public String rent(){
        int count = Arraycustomer.size();
        int canRent = 0;
        int notRent = 0;
        for (int i = 0; i < count; i++) {
            if (Arraycustomer.get(i).getCanRent()){
                canRent++;
            }else {
                notRent++;
            }
        }
        String line = "Number of customers who can rent : "+ '\t' + canRent + '\n' + "Number of customers who cannot rent : "+ '\t' + notRent;
        return line;
    }
    
   
    
    //Question_3A)
    public void sortSuppliersDisplay(){
        String[] sortID = new String[Arraysupplier.size()];
        ArrayList<Supplier> sortA= new ArrayList<Supplier>();
        int count = Arraysupplier.size();
        for (int i = 0; i < count; i++) {
            sortID[i] = Arraysupplier.get(i).getName();
        }
        Arrays.sort(sortID);
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sortID[i].equals(Arraysupplier.get(j).getName())){
                    sortA.add(Arraysupplier.get(j));
                }
            }
        }
        Arraysupplier.clear();
        Arraysupplier = sortA;
    }
   
   public void displaySupplieroutfile(){
        try{
            writer = new FileWriter("supplierOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================SUPPLIERS=========================================="));
           
            buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", "ID","Name","Prod Type","Description"));
            buffWriter.write("==============================================================================\n");
            for (int i = 0; i < Arraysupplier.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", Arraysupplier.get(i).getStHolderId(), Arraysupplier.get(i).getName(), Arraysupplier.get(i).getProductType(),Arraysupplier.get(i).getProductDescription()));
            }
            System.out.println("Supplier Text file information displayed.");
            
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }  
    
      
    
public static void main(String args[])  {
DisplayValues obj=new DisplayValues(); 
obj.openFileRead();
obj.readFile();
obj.readCloseFile();
obj.displayCustomersoutfile();
obj.displaySupplieroutfile();

     }    
     
}
