/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.6
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.io.*;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OrderForm {

    private int num1;
    private int num2;
    private int num3;
    private int num4;
    private int number;
    private ArrayList <Foods> finalHamp = new ArrayList<Foods>();

    private final String MEM1 = "Child under 8";
    private final String MEM2 = "Child over 8";
    private final String MEM3 = "Adult Female";
    private final String MEM4 = "Adult Male";

    private GUIFoodBank gui = new GUIFoodBank();
    private Hamper hamper = new Hamper();

    // ctor recieves values through arguments
    public OrderForm(int num1, int num2, int num3, int num4, int number, ArrayList <Foods> finalHamp){
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.number = number;
        this.finalHamp = finalHamp;
    }

    // empty ctor recieves value from gui through user inputs 
    public OrderForm(){
        this.num1 = gui.getUn8();
        this.num2 = gui.getOv8();
        this.num3 = gui.getFemAd();
        this.num4 = gui.getMalAd();
        this.number = gui.getNumber();
    }

    // creates text file that recieves order information for every hamper per gui entry
    public void printOrder(){
        // try creating text file
        try {
            // ensures new text is written after previous text in file
            Writer output = new BufferedWriter(new FileWriter("Form.txt",true));
            
            // prints how many members in age category onto text file
            output.append(hamper.OgRequest(this.num1, this.MEM1, this.num2, this.MEM2, this.num3, this.MEM3, this.num4, this.MEM4, this.number)
                + "\nHamper Items:\n");
            // prints food in hamper onto text file    
            for(int i = 0; i < this.finalHamp.size(); i++){
                Foods currItem = this.finalHamp.get(i);
                output.append(currItem.getID() + "\t" + currItem.getFoodName() + "\n");
            }
                
            output.close();     
        } 

        catch(IOException e){
            System.out.println("Error! The order form cannot be written.");
        }
    }

    // check if form exists
    public boolean Exist(){
        File file = new File("Form.txt");
        if (file.exists()){
            return true;
        }
        return false;
    }

    // creates final orderForm and clears any previous orderForms
        // point of orderForm: so that for every new gui input, old form can be deleted
            // and does not collide with previous orders 
    public void createFinal(){

        // clear contents of any previous orderForms so there can be fresh order form :)
        boolean fileExists = new File("OrderForm.txt").exists();
        if(fileExists == true){
            clear("OrderForm.txt");
        }

        try {
            File output = new File("OrderForm.txt");
            FileWriter writer = new FileWriter(output);
            BufferedWriter buffer = new BufferedWriter(writer);
            // buffer.append("Hamper Order Form\n" + "Original Request\n");
            buffer.close();
        } 

        catch(Exception e){
            System.out.println("Form can not be created.");
        }
    }

    // writes in orderForm
        // copies content from form to orderForm
    public void finalOrder() {
        InputStream is = null;
        OutputStream os = null;
        try {
            File output = new File("OrderForm.txt");
            FileWriter writer = new FileWriter(output);
            BufferedWriter buf = new BufferedWriter(writer);

            is = new FileInputStream("Form.txt");
            os = new FileOutputStream("OrderForm.txt");
            byte[] buffer = new byte[1024];
            int length;
            
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            buf.close();
        }

        catch(FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }

        catch(Exception e){
            System.out.println("Unexcepted Exception");
            e.printStackTrace();
        }
    }

    public void close(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch(IOException e) {
        }
    }

    // clears contents of files when called
    public void clear(String output){
        try {
            FileWriter myWriter = new FileWriter(output);
            myWriter.write("");
            myWriter.close();
            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    
    // deletes form, so that only orderForm exists after closing gui
    public void deleteFile() {
        File file = new File("Form.txt"); 

        try{
            file.delete();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }
}