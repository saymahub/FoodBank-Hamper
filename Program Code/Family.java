/**
Samira Khan <a href="mailto: samira.khan@ucalgary.ca">
Ammar Elzeftawy <a href="mailto: ammar.elzeftawy1@ucalgary.ca">
Michele Pham <a href="mailto: michele.pham@ucalgary.ca">
Sayma Haque <a href="mailto: sayma.haque@ucalgary.ca">
@version 1.2
@since 1.0
*/

package edu.ucalgary.ensf409;

public class Family {
    private final int MALES;
    private final int FEMALES;
    private final int UNDER8;
    private final int OVER8;

    private double totalwholeGrain;
    private double totalfruitsVeg;
    private double totalprotein;
    private double totalother;
    private double totalcalories;

    public Family(int m, int f, int under8, int over8){
        this.MALES = m;
        this.FEMALES = f;
        this.UNDER8 = under8;
        this.OVER8 = over8;
    }

    public int getMales(){
        return this.MALES;
    }

    public int getFemales(){
        return this.FEMALES;
    }

    public int getUnder8(){
        return this.UNDER8;
    }

    public int getOver8(){
        return this.OVER8;
    }

    // total methods = how much of said content entire family needs
    // week methodws = multiply by 7 to get content for entire week
    public double FVtotal(double fv){
        return this.totalfruitsVeg += fv;
    }

    public double FVweek (double fv){
        return fv * 7; 
    }

    public double WGtotal(double wg){
        return this.totalwholeGrain += wg;
    }

    public double Wgweek (double wg){
        return wg * 7; 
    }

    public double Prototal(double pro){
        return this.totalprotein += pro;
    }

    public double Proweek (double pro){
        return pro * 7; 
    }

    public double Otherweek (double other){
        return other * 7; 
    }

    public double Othertotal(double other){
        return this.totalother += other;
    }

    public double calories(double cal){
        return this.totalcalories += cal;
    }

    public double getFVtotal(){
        return this.totalfruitsVeg;
    }

    public double getWGtotal(){
        return this.totalwholeGrain;
    }

    public double getPrototal(){
        return this.totalprotein;
    }

    public double getOthertotal(){
        return this.totalother;
    }

    public double getcalories(){
        return this.totalcalories;
    }
}
