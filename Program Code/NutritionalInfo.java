/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.3
@since 1.0
*/

// nutritional info on food
package edu.ucalgary.ensf409;
import java.sql.*;
import java.io.*;
import java.util.*;

public class NutritionalInfo{
    
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;    
    
    private Connection dbConnect;
    private ResultSet results;
    private ResultSet foodResult;

    private int itemID;
    private double wholeGrains;
    private double fruitsVeg;
    private double protein;
    private double other;
    private double calories;
    private String name;

    public NutritionalInfo(String url, String user, String pw){

        // Database URL
        this.DBURL = url;

        //  Database credentials
        this.USERNAME = user;
        this.PASSWORD = pw;
    }

    //Must create a connection to the database, no arguments, no return value    
    public void initializeConnection(){
        try {
            dbConnect = DriverManager.getConnection(this.DBURL, "student", "ensf");
        } 
        
        catch (SQLException e){
            e.printStackTrace();
        }                    
    }
    
    public String getDburl(){
        return this.DBURL;
    }

    public String getUsername(){
        return this.USERNAME;
    }
    
    public String getPassword(){
        return this.PASSWORD;
    }

    public int getID(){
        return this.itemID;
    }

    public String getName(){
        return this.name;
    }

    public double getWG(){
        return this.wholeGrains;
    }
    
    public double getFV(){
        return this.fruitsVeg;
    }

    public double getPro(){
        return this.protein;
    }

    public double getOther(){
        return this.other;
    }

    public double getCal(){
        return this.calories;
    }

    // creates inventory into an ArrayList of class Foods
    public ArrayList<Foods> List(){     
        ArrayList<Foods> foodList = new ArrayList<Foods>();
        try{
            Statement myStmt = dbConnect.createStatement();
            foodResult = myStmt.executeQuery("SELECT * FROM " + "AVAILABLE_FOOD");
            
            // recieves content of each category and stores them in variable
            while (foodResult.next()){

                int a = foodResult.getInt("GrainContent");
                double grain = a;

                int b = foodResult.getInt("FVContent");
                double fv = b;

                int c = foodResult.getInt("ProContent");
                double pro = c;

                int d = foodResult.getInt("Other");
                double other = d;

                int e = foodResult.getInt("Calories");
                double cal = e;
                
                // calculates contents based on calories
                grain = grain/100 * cal;
                fv = fv/100 * cal;
                pro = pro/100 * cal;
                other = other/100 * cal;

                // puts values into type Foods
                Foods individualFood = new Foods(foodResult.getInt("ItemID"), foodResult.getString("Name"), grain, fv, pro, other, cal);

                // adds new Food into ArrayList of Foods
                foodList.add(individualFood);

            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return foodList;
    }

    // removes item from inventory based on its ID
    public void updateInventory(int ID){
        try {
            String query = "DELETE FROM AVAILABLE_FOOD WHERE ItemID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, ID);
            myStmt.executeUpdate();
            myStmt.close();

        } 
        
        catch (SQLException ex) {
            ex.printStackTrace();
        }             
    }

    public void addFood(String name, int wg, int fv, int pro, int other, int cal){
             
        try{
            String query = "INSERT INTO AVAILABLE_FOOD (Name, GrainContent, FVContent, ProContent, Other, Calories) VALUES (?,?,?,?,?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setString(1, name);
            myStmt.setInt(2, wg);
            myStmt.setInt(3, fv);
            myStmt.setInt(4, pro);
            myStmt.setInt(5, other);
            myStmt.setInt(6, cal);
            myStmt.executeUpdate();
            myStmt.close();
        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }                  


    }    
 
    public void close() {
        try {
            results.close();
            dbConnect.close();
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        }               

    }
}
