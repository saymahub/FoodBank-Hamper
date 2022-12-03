/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.7
@since 1.0
*/


package edu.ucalgary.ensf409;

import java.util.ArrayList;
import org.xml.sax.ErrorHandler;

public class Hamper {
    private double wholeGrain;
    private double fruitsVeg;
    private double protein;
    private double other;
    private double calories;
    private int number;
    private double addGrain = 0;
    private double addVeg = 0;
    private double addPro = 0;
    private double addOth = 0;   
    private boolean flag = false;

    private ArrayList<Foods> hamper = new ArrayList<Foods>();

    private NutritionalInfo info = new NutritionalInfo("jdbc:mysql://localhost/food_inventory","student","ensf"); 

    // make an empty string
    private String OgReq = "";   

    // ctor recieves values from arg
    public Hamper(double grains, double fv, double protein, double other, double calories){
        this.wholeGrain = grains;
        this.fruitsVeg = fv;
        this.protein = protein;
        this.other = other;
        this.calories = calories;
    }

    // empty ctor
    public Hamper(){
    }

    public double getWG(){
        return this.addGrain;
    }
    public double getFV(){
        return this.addVeg;
    }
    public double getPro(){
        return this.addPro;
    }
    public double getOther(){
        return this.addOth;
    }
    public double getCal(){
        return this.calories;
    }

    public int getNumber(){
        return this.number;
    }

    // creates string order form prints to show info of family
    public String OgRequest(int num1, String mem1, int num2, String mem2, int num3, String mem3, int num4, String mem4, int number){
        String Hamper = "";

        // if first hamper, add title of form
        if (number == 1){
            Hamper += "Hamper Order Form\n" + "Original Request\n";
        }

        Hamper += "\nHamper " + number + ": ";

        // check if there are people under this age category
        if (num1 > 0){
            // add how many in category to String
            Hamper += num1 + " " + mem1;
            
            // if more members in other age categories exist, add comma
            if (num2 > 0 || num3 > 0 || num4 > 0){
                Hamper += ", "; 
            }

            else{
                Hamper += "\n";
            }
        }

        if (num2 > 0){
            Hamper += num2 + " " + mem2;
            
            if (num3 > 0 || num4 > 0){
                Hamper += ", "; 
            }

            else{
                Hamper += "\n";
            }
        }

        if (num3 > 0){
            Hamper += num3 + " " + mem3;

            if (num4 > 0){
                Hamper += ", "; 
            }

            else{
                Hamper += "\n";
            }
        }

        if (num4 > 0){
            Hamper += num4 + " " + mem4 + "\n"; 
        }

        this.OgReq += Hamper;
        return this.OgReq;
    }

    public boolean minimumReached(){
        info.initializeConnection();
        // contains current inventory
        ArrayList<Foods> invent = info.List();
        
        // return true if minimum requirements are reached in hamper
        if (addGrain >= wholeGrain && addVeg >= fruitsVeg && addPro >= protein && addOth >= other){
            return true;
        }

        // if inventory is empty
            // requirements can't be reached return false
        else if (invent.isEmpty()){
            return false;
        }

        return false;
    }

    // create hamper
    public void makeHamp(double grain, double veg, double protein, double other){
        info.initializeConnection();
        // contains entire inventory
        ArrayList<Foods> invent = info.List();

        // find item with least amount of calories in inventory
        Foods item = leastCals(invent);
        
        // check if inventory is empty
        if(invent.isEmpty()){

            // put items that were deleted from inventory while creating hamper back into inventory
            for (int i = 0; i < hamper.size(); i ++){
                // values in sql are int
                    // double in code for percision
                    // convert back in order to add back to sql
                int wgint = (int)hamper.get(i).getGrain();
                int fvint = (int)hamper.get(i).getFV();
                int proint = (int)hamper.get(i).getPro();
                int othint = (int)hamper.get(i).getOther();
                int calint = (int)hamper.get(i).getCalories();
                info.addFood(hamper.get(i).getFoodName(), wgint, fvint, proint, othint, calint);
            }

            // clear hamper 
            hamper.clear();

            // end recursion 
            flag = true;
            return;
        }

        // delete item from inventory
        info.updateInventory(item.getID());

        // can not enter more than once
        if (flag == false){
            if(addGrain >= grain && addVeg >=veg && addPro >= protein && addOth >= other){
                // change flag to true so recursion method stops adding things to hamper
                    // and returns in other parts of code to end recursion
                flag = true;
                return;
            }
        }

        // when 3 of the food categories' minimum requirements are met
        if(addGrain >= grain && addVeg >=veg && addPro >= protein){
            // find food item from each least function
            Foods item1 = leastGrain(invent);
            Foods item2 = leastFV(invent);
            Foods item3 = leastPro(invent);

            // create and add items into a list
                // so it can be used in leastCals method if needed
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            check.add(item3);

            // check which item has the greatest content of the missing category
            if (item1.getOther() > item2.getOther() && item1.getOther() > item3.getOther() ){
                item = item1;
            }

            else if (item2.getOther() > item1.getOther() && item2.getOther() > item3.getOther() ){
                item = item2;
            }

            else if (item3.getOther() > item1.getOther() && item3.getOther() > item2.getOther() ){
                item = item1;
            }

            // if none of them pass, choose item with the least calories
            else{
                item = leastCals(check);
            }

            // ensure hamper doesn't add things after final statement reached
            if (flag == false){

                // add item to hamper
                this.hamper.add(item);

                // get content values of item
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            // delete item from inventory
            info.updateInventory(item.getID());

            // if statement reached, return to stop recursion
            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);  
        }

        if(addGrain >= grain && addVeg >=veg && addOth >= other){
            Foods item1 = leastGrain(invent);
            Foods item2 = leastFV(invent);
            Foods item3 = leastOther(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            check.add(item3);

            // check which item has the greatest content of the missing category
            if (item1.getPro() > item2.getPro() && item1.getPro() > item3.getPro() ){
                item = item1;
            }

            else if (item2.getPro() > item1.getPro() && item2.getPro() > item3.getPro() ){
                item = item2;
            }

            else if (item3.getPro() > item1.getPro() && item3.getPro() > item2.getPro() ){
                item = item1;
            }

            else{
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addGrain >= grain && addPro >= protein && addOth >= other){
            Foods item1 = leastGrain(invent);
            Foods item2 = leastOther(invent);
            Foods item3 = leastPro(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            check.add(item3);

           // check which item has the greatest content of the missing category
            if (item1.getFV() > item2.getFV() && item1.getFV() > item3.getFV() ){
                item = item1;
            }

            else if (item2.getFV() > item1.getFV() && item2.getFV() > item3.getFV() ){
                item = item2;
            }

            else if (item3.getFV() > item1.getFV() && item3.getFV() > item2.getFV() ){
                item = item1;
            }

            else{
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }
            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addVeg >=veg && addPro >= protein && addOth >= other){
            Foods item1 = leastOther(invent);
            Foods item2 = leastFV(invent);
            Foods item3 = leastPro(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            check.add(item3);

            // check which item has the greatest content of the missing category
            if (item1.getGrain() > item2.getGrain() && item1.getGrain() > item3.getGrain() ){
                item = item1;
            }

            else if (item2.getGrain() > item1.getGrain() && item2.getGrain() > item3.getGrain() ){
                item = item2;
            }

            else if (item3.getGrain() > item1.getGrain() && item3.getGrain() > item2.getGrain() ){
                item = item1;
            }

            else{
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);
                
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addGrain >= grain && addVeg >=veg){
            Foods item1 = leastGrain(invent);
            Foods item2 = leastFV(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getPro() == 0 || item1.getOther() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getPro() == 0 || item2.getOther() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }
            makeHamp(grain, veg, protein, other);
        }

        if(addGrain >= grain && addPro >= protein){
            Foods item1 = leastGrain(invent);
            Foods item2 = leastPro(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getFV() == 0 || item1.getOther() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getFV() == 0 || item2.getOther() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;   
            }

            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }
            makeHamp(grain, veg, protein, other);
        }
        
        if(addGrain >= grain && addOth >= other){
            Foods item1 = leastGrain(invent);
            Foods item2 = leastOther(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getFV() == 0 || item1.getPro() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getFV() == 0 || item2.getPro() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);
                
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }
            info.updateInventory(item.getID());
            
            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addVeg >=veg && addPro >= protein){
            Foods item1 = leastFV(invent);
            Foods item2 = leastPro(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getGrain() == 0 || item1.getOther() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getGrain() == 0 || item2.getOther() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            info.updateInventory(item.getID());
            
            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addVeg >=veg && addOth >= other){
            Foods item1 = leastFV(invent);
            Foods item2 = leastOther(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getGrain() == 0 || item1.getPro() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getGrain() == 0 || item2.getPro() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth; 
            }
            info.updateInventory(item.getID());
            
            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if(addPro >= protein && addOth >= other){
            Foods item1 = leastOther(invent);
            Foods item2 = leastPro(invent);
            ArrayList <Foods> check = new ArrayList<Foods>();
            check.add(item1);
            check.add(item2);
            boolean i1 = false;
            boolean i2 = false;

            // if either contents needed are 0, use other item 
            if (item1.getFV() == 0 || item1.getGrain() == 0){
                item2 = item;
                // to check if it went through this if statement
                i1 = true;
            }

            else if (item2.getFV() == 0 || item2.getGrain() == 0){
                item1 = item;
                i2 = true;
            }

            // if both if statements true, make item least cals
            if(i1 == true && i2 == true){
                item = leastCals(check);
            }

            if (flag == false){
                this.hamper.add(item);

                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }

            info.updateInventory(item.getID());   

            if (flag == true){
                return;
            }
            makeHamp(grain, veg, protein, other);
        }

        if (addGrain >= grain){
        
            item = leastGrain(invent);

            if (flag == false){
                this.hamper.add(item);

                // get content values of item
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }
            
            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }
        
        if (addVeg >= veg){
         
            item = leastFV(invent);

            if (flag == false){
                this.hamper.add(item);
                
                // get content values of item
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }
            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }

            makeHamp(grain, veg, protein, other);
        }

        if (addPro >= protein){

            item = leastPro(invent); 
            
            if (flag == false){
                this.hamper.add(item);
                
                // get content values of item
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();
    
                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;            
            }

            info.updateInventory(item.getID());

            if (flag == true){
                return;
            }
           
            makeHamp(grain, veg, protein, other);
        }

        if (addOth >= other){
      
            item = leastOther(invent);

            if (flag == false){
                this.hamper.add(item);

                // get content values of item
                double WG = item.getGrain();
                double FV = item.getFV();
                double PRO = item.getPro();
                double Oth = item.getOther();

                // add new items contents values to hampers contents
                addGrain = addGrain + WG;
                addVeg = addVeg + FV;
                addPro = addPro + PRO;
                addOth = addOth + Oth;
            }
            
            info.updateInventory(item.getID());
            

            if (flag == true){
                return;
            }
           
            makeHamp(grain, veg, protein, other);
        }

        if (flag == false){
            this.hamper.add(item);
            
            // get content values of item
            double WG = item.getGrain();
            double FV = item.getFV();
            double PRO = item.getPro();
            double Oth = item.getOther();

            // add new items contents values to hampers contents
            addGrain = addGrain + WG;
            addVeg = addVeg + FV;
            addPro = addPro + PRO;
            addOth = addOth + Oth;
        }

        info.updateInventory(item.getID());

        if (flag == true){
            return;
        }
  
        makeHamp(grain, veg, protein, other);
        
    }

    public Foods leastGrain(ArrayList <Foods> list){
        // create foods object with no values
        Foods best = new Foods(0, null, 0, 0, 0, 0, 0);

        // list of grains that all have the same mnimum grain content
        ArrayList <Foods> grainList = new ArrayList<Foods>();
        double leastGrain = 0.1; 

        // find whats the lowest content of grain any item has
        for(int i = 0; i < list.size(); i ++){
            Foods prev = list.get(i);
            double grain = prev.getGrain();

            // if leastGrain does not have an actual value yet, equate to grain
            if (leastGrain == 0.1){
                leastGrain = grain;
            }

            // replace value of least grain every time a grain value lower appears
            if (leastGrain > grain){
                leastGrain = grain;
            }
        }

        // make list of food that all have the same value of least amount of grain
        for(int i = 0; i < list.size(); i ++){
            if (list.get(i).getGrain() == leastGrain){
                grainList.add(list.get(i));
            }
        }

        best = leastCals(grainList);
        return best;
    }

    public Foods leastFV(ArrayList <Foods> list){
        Foods best = new Foods(0, "name", 0, 0, 0, 0, 0);
        ArrayList <Foods> FVList = new ArrayList<Foods>();
        double leastFV = 0.1; 

        // find whats the lowest content of grain any item has
        for(int i = 0; i < list.size(); i ++){
            Foods prev = list.get(i);
            double FV = prev.getFV();
            if (leastFV == 0.1){
                leastFV = FV;
            }

            if (leastFV > FV){
                leastFV = FV;
            }
            
        }
        // make list of food that all have the same value of least amount of fv
        for(int i = 0; i < list.size(); i ++){
            if (list.get(i).getFV() == leastFV){
                FVList.add(list.get(i));
            }
        }

        best = leastCals(FVList);
        return best;
    }

    public Foods leastPro(ArrayList <Foods> list){
        Foods best = new Foods(0, null, 0, 0, 0, 0, 0);
        ArrayList <Foods> ProList = new ArrayList<Foods>();
        double leastPro = 0.1; 

        // find whats the lowest content of grain any item has
        for(int i = 0; i < list.size(); i ++){
            Foods prev = list.get(i);
            double Pro = prev.getPro();
            if (leastPro == 0.1){
                leastPro = Pro;
            }

            if (leastPro > Pro){
                leastPro = Pro;
            }
            
        }
        // make list of food that all have the same value of least amount of protein
        for(int i = 0; i < list.size(); i ++){
            if (list.get(i).getPro() == leastPro){
                ProList.add(list.get(i));
            }
        }

        best = leastCals(ProList);
        return best;
    }

    public Foods leastOther(ArrayList <Foods> list){
        Foods best = new Foods(0, "name", 0, 0, 0, 0, 0);
        ArrayList <Foods> OtherList = new ArrayList<Foods>();
        double leastOther = 0.1; 

        // find whats the lowest content of grain any item has
        for(int i = 0; i < list.size(); i ++){
            Foods prev = list.get(i);
            double Oth = prev.getPro();
            if (leastOther == 0.1){
                leastOther = Oth;
            }

            if (leastOther > Oth){
                leastOther = Oth;
            }
            
        }
        // make list of food that all have the same value of least amount of other
        for(int i = 0; i < list.size(); i ++){
            if (list.get(i).getPro() == leastOther){
                OtherList.add(list.get(i));
            }
        }

        best = leastCals(OtherList);
        return best;
    }
    
    // find food in list that has the least amount of calories
    public Foods leastCals(ArrayList<Foods> list){
        Foods best = new Foods(0, null, 0, 0, 0, 0, 0);
        double prevCal = 0;

        for(int i = 0; i < list.size(); i ++){
            Foods prev = list.get(i);
            double cal = prev.getCalories();

            if (prevCal == 0){
                prevCal = cal;
                best = prev;
            }

            if (prevCal > cal){
                best = prev;
                prevCal = cal;
            }
        }
        
        return best;
    }


    public void setHamp(ArrayList <Foods> hamper){
        this.hamper = hamper;
    }
    
    public ArrayList <Foods> getHamp(){
        return this.hamper;
    }
}
