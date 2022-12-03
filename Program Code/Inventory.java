/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.2
@since 1.0
*/


//adds/contains food and removes food
package edu.ucalgary.ensf409;

import java.util.ArrayList;

public class Inventory{

    private ArrayList<Foods> totalInventory = new ArrayList<Foods>();
    
    // empty ctor
    public Inventory(){
    }

    // pass an ArrayList of Foods to set the inventory
    public void setInven(ArrayList<Foods> list){
        this.totalInventory = list;
    }

    // recieve inventory
    public ArrayList<Foods> getInven(){
        return this.totalInventory;
    }

    // get a specific item from inventory
    public Foods getItem(int i){
        return totalInventory.get(i);
    }
}