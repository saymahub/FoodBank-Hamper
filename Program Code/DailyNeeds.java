/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.3
@since 1.0
*/

package edu.ucalgary.ensf409;

import java.sql.*;

public class DailyNeeds{
   
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;

    private Connection dbConnect;
    private ResultSet results;
    private double calories;
    
    public DailyNeeds(String url, String user, String pw){

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
        } catch (SQLException e){
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

    // select needs requirement based on client
    public String selectNeeds(String client){     
        StringBuffer needs = new StringBuffer();
        
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                needs.append(results.getString("WholeGrains") + ", " 
                    + results.getString("FruitVeggies") + ", " + results.getString("Protein") + ", " 
                    + results.getString("Other") + ", " + results.getString("Calories") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return needs.toString();
    }
    
    // obtains calories value from sql and multiplies how many individuals are in certain age group
    public double Calories(String client, int number){
        StringBuffer calories = new StringBuffer();
       
        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                calories.append(results.getString("Calories") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        // multiply calories by how many of client there are
        String cal = calories.toString();
        this.calories = Double.parseDouble(cal) * number;

        return this.calories;
    }

    // returns how much fruit and veggies age group needs including if there are multiple members in group
    public double WGcontent(String client){
        StringBuffer wG = new StringBuffer();
        double WGcontent;

        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                wG.append(results.getString("WholeGrains") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        // multiply by calories to recieve how much WG is needed
        String strWG = wG.toString();
        WGcontent = Double.parseDouble(strWG) / 100 * this.calories;

        return WGcontent;
    }

    // returns how much fruit and veggies age group needs including if there are multiple members in group
    public double FVcontent(String client){
        StringBuffer fV = new StringBuffer();
        double FVcontent;

        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                fV.append(results.getString("FruitVeggies") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        // multiply by calories to recieve how much FV is needed
        String strFV = fV.toString();
        FVcontent = Double.parseDouble(strFV) / 100 * this.calories;

        return FVcontent;
    }

    // returns how much protein age group needs including if there are multiple members in group
    public double Procontent(String client){
        StringBuffer pro = new StringBuffer();
        double Procontent;

        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                pro.append(results.getString("Protein") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        // multiply by calories to recieve how much protein is needed
        String strPro = pro.toString();
        Procontent = Double.parseDouble(strPro)  / 100 * this.calories;

        return Procontent;
    }

     // returns how much protein age group needs including if there are multiple members in group
     public double Othercontent(String client){
        StringBuffer other = new StringBuffer();
        double Othercontent;

        try{
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + "DAILY_CLIENT_NEEDS WHERE Client = "+ "'" + client + "'");
            while (results.next()){
                other.append(results.getString("Other") + "\n"); 
                                
            }
            myStmt.close();

        }
        
        catch(SQLException ex){
            ex.printStackTrace();
        }

        // multiply by calories to recieve how much other is needed
        String strOther = other.toString();
        Othercontent = Double.parseDouble(strOther)  / 100 * this.calories;

        return Othercontent;
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
