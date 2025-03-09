package gr.myaigoprov.model;

import java.util.Date;

public class Milk {
    private Integer id;
    private double quantity;
    private String date;

    public Milk(double quantity, String date){
       setDate(date);
       setQuantity(quantity);
    }

    public Milk(Integer id, double quantity, String date){
        setId(id);
        this.quantity = quantity;
        this.date = date;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        if (id < 0) {
            throw new IllegalArgumentException("Το id δεν μπορεί να είναι αρνητικό");
        }
        else {
            this.id = id;
        }
    }

    public double getQuantity(){
        return quantity;
    }
    public void setQuantity(double newQuantity){
        if(newQuantity < 0){
            throw new IllegalArgumentException("Δεν μπορεί η ποσότητα του γάλακτος να είναι λιγότερη από 0 λίτρα");
        }
        else{
            this.quantity = newQuantity;
        }
    }

    public String getDate(){
        return date;
    }

    public void setDate(String newDate){
        if(newDate.isEmpty() || newDate == null){
            throw new IllegalArgumentException("Δεν μπορεί η ημερομηνία να είναι κενή");
        }
        else{
            this.date = newDate;
        }
    }


    @Override
    public String toString(){
        return "Γάλα{Λίτρα: " + quantity + " Ημ/μηνία: " + date + "}";
    }

}
