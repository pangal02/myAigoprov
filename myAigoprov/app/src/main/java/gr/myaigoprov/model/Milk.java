package gr.myaigoprov.model;

public class Milk {
    private double quantity;
    private String date;

    public Milk(double quantity, String date){
        setQuantity(quantity);
        setDate(date);
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
        newDate = newDate.trim();
        if(newDate.isEmpty() || newDate == null){
            throw new IllegalArgumentException("Δεν μπορεί η ημερομηνία να είναι κενή");
        }
        else{
            this.date = date;
        }
    }

    @Override
    public String toString(){
        return "Λίτρα: " + quantity + " Ημ/μηνία: " + date;
    }

}
