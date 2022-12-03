/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.2
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Foods implements Cloneable{

    private final int ID;
    private final double GRAIN;
    private final double FV;
    private final double PRO;
    private final double OTHER;
    private final double CALORIES;
    private final String FOODNAME;

    // recieves info on food's contents
    public Foods(int ID, String name, double grain, double fv, double pro, double other, double cal) {
        this.ID = ID;
        this.GRAIN = grain;
        this.FV = fv;
        this.PRO = pro;
        this.OTHER = other;
        this.CALORIES = cal;
        this.FOODNAME = name;
    }

    public int getID() {return this.ID;}

    public double getGrain() {return this.GRAIN;}

    public double getFV() {return this.FV;}

    public double getPro() {return this.PRO;}

    public double getOther() {return this.OTHER;}

    public double getCalories() {return this.CALORIES;}
    
    public String getFoodName() {return this.FOODNAME;}

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
}
