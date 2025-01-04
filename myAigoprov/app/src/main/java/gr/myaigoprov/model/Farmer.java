package gr.myaigoprov.model;

public class Farmer {
    private String fullName;
    private final String farmerCode;


    public Farmer(String fullName, String farmerCode) {
        this.fullName = fullName;
        this.farmerCode = farmerCode;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        if(fullName.isEmpty() || fullName == null){
            throw new IllegalArgumentException("Το Όνομα δεν μπορει να είναι κενό!");
        }
        else{
            this.fullName = fullName;
        }
    }


    public String getFarmerCode() {
        return farmerCode;
    }


    @Override
    public String toString(){
        return "Farmer{Όνοματεπώνυμο: " + fullName  + ", Κωδικός Κτηνοτρόφου: " + farmerCode + "}";
    }

}
