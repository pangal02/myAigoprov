package gr.myaigoprov.model;

public class Milk {
    private double quantity;
    private final String date;
//    private int image;

    public Milk(double quantity, String date){
        this.quantity = quantity;
        this.date = date;
//        this.image = image;
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

//    public void setDate(String newDate){
//        if(newDate.isEmpty() || newDate == null){
//            throw new IllegalArgumentException("Δεν μπορεί η ημερομηνία να είναι κενή");
//        }
//        else{
//            this.date = date;
//        }
//    }

//    public int getImage() {
//        return image;
//    }

    @Override
    public String toString(){
        return "Λίτρα: " + quantity + " Ημ/μηνία: " + date;
    }

}
