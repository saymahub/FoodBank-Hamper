/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.9
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GUIFoodBank extends JFrame implements ActionListener, MouseListener{
    
    private int under8;
    private int over8;
    private int femAd;
    private int malAd;
    private int number;

    private JLabel instr;
    private JLabel un8;
    private JLabel ov8;
    private JLabel fAd;
    private JLabel mAd;
    private JLabel num;

    private JTextField un8in;
    private JTextField ov8in;
    private JTextField fAdin;
    private JTextField mAdin;
    private JTextField numin;

    public static void main (String[] args) {
        EventQueue.invokeLater(() -> {
            new GUIFoodBank().setVisible(true);
        });

        int mobilityAccom = JOptionPane.showConfirmDialog(null, "Do you need a mobility accomodation?");
        System.out.println(mobilityAccom);
        
    }

    public GUIFoodBank(){
        super("Place an Order");
        setupGUI();
        setSize(450, 300); 
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupGUI(){ 

        instr = new JLabel("Please enter how many members there are in each category.");
        un8 = new JLabel("Children under 8:");
        ov8 = new JLabel("Children over 8:");
        fAd = new JLabel("Female adults:");
        mAd = new JLabel("Male adults:");
        num = new JLabel("Hamper Number:");

        un8in = new JTextField("", 10);
        ov8in = new JTextField("", 10);
        fAdin = new JTextField("", 10);
        mAdin = new JTextField("", 10);
        numin = new JTextField("", 10);

        un8in.addMouseListener(this);
        ov8in.addMouseListener(this);
        fAdin.addMouseListener(this);
        mAdin.addMouseListener(this);
        numin.addMouseListener(this);

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());
        
        
        headerPanel.add(instr);
        clientPanel.add(un8);
        clientPanel.add(un8in);
        clientPanel.add(ov8);
        clientPanel.add(ov8in);
        clientPanel.add(fAd);
        clientPanel.add(fAdin);
        clientPanel.add(mAd);
        clientPanel.add(mAdin);
        clientPanel.add(num);
        clientPanel.add(numin);
        submitPanel.add(submit);
        
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(clientPanel, BorderLayout.CENTER);
        this.add(submitPanel, BorderLayout.PAGE_END);

    }

    public boolean validateInput() throws NumberFormatException{

        // if invalid input, return false and prompt user again
        try{
            // convert input from String to int
            this.under8 = Integer.parseInt(un8in.getText());
            
            // if negative number, return false and allow user to re input
            if(this.under8 < 0){
                JOptionPane.showMessageDialog(this, "For child under 8, " + under8 + " is an invalid input. Please enter a number from 0 or more.");
                return false;
            }
        }

        // if input is not a number, return false and allow user to re input
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number in all fields.");
            return false;
        }

        try{
            this.over8 = Integer.parseInt(ov8in.getText());
            if(this.over8 < 0){
                JOptionPane.showMessageDialog(this, "For child over 8, " + over8 + " is an invalid input. Please enter a number from 0 or more.");
                return false;
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number in all fields.");
            return false;
        }

        try{
            this.femAd= Integer.parseInt(fAdin.getText());
            if(this.femAd < 0){
                JOptionPane.showMessageDialog(this, "For adult female, " + femAd + " is an invalid input. Please enter a number from 0 or more.");
                return false;
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number in all fields.");
            return false;
        }

        try{
            this.malAd = Integer.parseInt(mAdin.getText());
            if(this.malAd < 0){
                return false;
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number in all fields.");
            return false; 
        }
      
        if(this.under8 == 0 && this.over8 == 0 && this.femAd == 0 && this.malAd == 0){
            JOptionPane.showMessageDialog(this, "Error! A hamper cannot be created for a family with no members.");
            return false;
        }
    
        try{
            this.number = Integer.parseInt(numin.getText());
            OrderForm form = new OrderForm();
            if (form.Exist() == false){
                if (this.number != 1){
                JOptionPane.showMessageDialog(this, "For first Hamper, please enter 1 in hamper number.");
                return false; 
                }
            }

            else{
                if (this.number == 1){
                    JOptionPane.showMessageDialog(this, "Update the hamper number.");
                    return false; 
                    }
            }

            if(this.number < 0){
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
                return false;
            }
        }

        catch (NumberFormatException ex) {
            return false;
        }
        return true;
        
    }

    public int getUn8(){
        return this.under8;
    }

    public int getOv8(){
        return this.over8;
    }

    public int getFemAd(){
        return this.femAd;
    }

    public int getMalAd(){
        return this.malAd;
    }

    public int getNumber(){
        return this.number;
    }

    public void setUn8(int under8){
        this.under8 = under8;
    }

    public void setOv8(int over8){
        this.over8 = over8;
    }

    public void setAdFem(int femAd){
        this.femAd = femAd;
    }

    public void setMalAd(int malAd){
        this.malAd = malAd;
    }

    public void setNum(int num){
        this.number = num;
    }

    public void actionPerformed(ActionEvent event) {

        // show successful msg if validateInput is true
        if (validateInput() == true) {
            JOptionPane.showMessageDialog(this, "The order for this family has been placed successfully.");
            createHamper();

            JPanel panel = new JPanel();
            final JLabel label = new JLabel();
            OrderForm form = new OrderForm();
            
            // ask user if they want to create multiple hampers
            int result = JOptionPane.showConfirmDialog(this,"Please select if you would like to create another hamper.", "Create Hamper",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                // if yes, allows user to enter new family and continue
                if(result == JOptionPane.YES_OPTION){}
                
                // if no, create final file, delete previous file, and automatically close window
                else if (result == JOptionPane.NO_OPTION){
                    form.createFinal();
                    form.finalOrder();

                    if(form.Exist()){
                        form.deleteFile();
                    }

                    JOptionPane.showMessageDialog(this, "Thank you!");
                    System.exit(0); // stop program
                    this.dispose(); // close window

                }
                
                else {
                label.setText("None selected");
                }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please try again.");
        }
    
    }

    // builds hamper based on user input
    public void createHamper(){
        int under = getUn8();
        int over = getOv8();
        int femAd = getFemAd();
        int malAd = getMalAd();
      
        double fruitsVeg = 0;
        double wholeGrain = 0;
        double protein = 0;
        double oth = 0; 
        double cal = 0;

        DailyNeeds needs = new DailyNeeds("jdbc:mysql://localhost:3306/food_inventory","student","ensf");
        needs.initializeConnection();

        NutritionalInfo insert = new NutritionalInfo("jdbc:mysql://localhost/food_inventory", "student", "ensf");
        insert.initializeConnection();

        Inventory inventory = new Inventory();
        inventory.setInven(insert.List());

        Family happyFamily = new Family(malAd, femAd, under, over);

        // make sub hampers (and add all the sub hampers to create one GIANT HAMPA)
        if (under >= 1){
            double calories = needs.Calories("Child under 8", under);        //total amount of calories for this age category
            // get content requirement from SQL
            double FV = needs.FVcontent("Child under 8");
            double WG = needs.WGcontent("Child under 8");
            double Pro = needs.Procontent("Child under 8");
            double Other = needs.Othercontent("Child under 8");
            
            //add values to hampers to collect total needs for family           
            wholeGrain = happyFamily.WGtotal(WG);
            fruitsVeg = happyFamily.FVtotal(FV);
            protein = happyFamily.Prototal(Pro);
            oth = happyFamily.Othertotal(Other);
            cal = happyFamily.calories(calories);
        }
    
        if (over >= 1){
            double calories = needs.Calories("Child over 8", over);
            double FV = needs.FVcontent("Child over 8");
            double WG = needs.WGcontent("Child over 8");
            double Pro = needs.Procontent("Child over 8");
            double Other = needs.Othercontent("Child over 8");
    
            wholeGrain = happyFamily.WGtotal(WG);
            fruitsVeg = happyFamily.FVtotal(FV);
            protein = happyFamily.Prototal(Pro);
            oth = happyFamily.Othertotal(Other);
            cal = happyFamily.calories(calories);
        }
    
        if (femAd >= 1){
            double calories = needs.Calories("Adult Female", femAd);
            double FV = needs.FVcontent("Adult Female");
            double WG = needs.WGcontent("Adult Female");
            double Pro = needs.Procontent("Adult Female");
            double Other = needs.Othercontent("Adult Female");
    
            wholeGrain = happyFamily.WGtotal(WG);
            fruitsVeg = happyFamily.FVtotal(FV);
            protein = happyFamily.Prototal(Pro);
            oth = happyFamily.Othertotal(Other);
            cal = happyFamily.calories(calories);
        }

        if (malAd >= 1){
            double calories = needs.Calories("Adult Male", malAd);
            double FV = needs.FVcontent("Adult Male");
            double WG = needs.WGcontent("Adult Male");
            double Pro = needs.Procontent("Adult Male");
            double Other = needs.Othercontent("Adult Male");
    
            wholeGrain = happyFamily.WGtotal(WG);
            fruitsVeg = happyFamily.FVtotal(FV);
            protein = happyFamily.Prototal(Pro);
            oth = happyFamily.Othertotal(Other);
            cal = happyFamily.calories(calories);
        }

        double WGweek = happyFamily.Wgweek(wholeGrain);
        double FVweek = happyFamily.FVweek(fruitsVeg);
        double PROweek = happyFamily.Proweek(protein);
        double OTHweek = happyFamily.Otherweek(oth);

        Hamper hamper = new Hamper(WGweek, FVweek, PROweek, OTHweek, cal); 
        hamper.makeHamp(WGweek, FVweek, PROweek, OTHweek);
        OrderForm form = new OrderForm();

        if(hamper.minimumReached()){
            ArrayList<Foods> finalHamper = hamper.getHamp();
            if(finalHamper.isEmpty()){
                // form.printOrder();
                form.createFinal();
                form.finalOrder();
                if(form.Exist()){
                    form.deleteFile();
                }
                
                JOptionPane.showMessageDialog(this, "Sorry! Not enough food in inventory :(. ");
                JOptionPane.showMessageDialog(this, "Thank you!");
                System.exit(0); // stop program
                this.dispose(); // close window
            }
            // create form
            form = new OrderForm(getUn8(), getOv8(), getFemAd(), getMalAd(), getNumber(), finalHamper);
            form.printOrder();
            // for(int i = 0; i < finalHamper.size(); i++){
            //     System.out.println(finalHamper.get(i).getID() + " " + finalHamper.get(i).getFoodName());
            //     }   
        }
        
        // if no hamper that can fulfill requirements are found, show out of food message and close gui
        else{
            // form.printOrder();
            form.createFinal();
            form.finalOrder();
            if(form.Exist()){
                form.deleteFile();
            }
            JOptionPane.showMessageDialog(this, "Sorry! Not enough food in inventory :(. ");
            JOptionPane.showMessageDialog(this, "Thank you!");
            System.exit(0); // stop program
            this.dispose(); // close window
        }
    }
    public void mouseClicked(MouseEvent event){}

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){}

    public void mousePressed(MouseEvent event){}

    public void mouseReleased(MouseEvent event){}
}