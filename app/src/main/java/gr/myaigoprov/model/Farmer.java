package gr.myaigoprov.model;

import static androidx.core.content.ContentProviderCompat.requireContext;

import gr.myaigoprov.database.DatabaseHelper;

public class Farmer {
    private String fullName;
    private final String farmerCode;
    private String animalsType;


    public Farmer(String fullName, String farmerCode, String animalsType) {
        this.fullName = fullName;
        if(farmerCode.isEmpty() || farmerCode == null){
            this.farmerCode = "EL12345";
        }
        else{
            this.farmerCode = farmerCode;
        }
        setAnimalsType(animalsType);
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
        return;
    }

    public String getFarmerCode() {
        return farmerCode;
    }

    public String getAnimalsType(){
        return animalsType;
    }

    public void setAnimalsType(String newAnimalType){
        if(newAnimalType.isEmpty() || newAnimalType == null){
            throw new IllegalArgumentException("Δεν μπορεί ο τύπος ζώων κτηνοτρόφου να είναι κενός! Αποδεκτές τιμές: ΑΙΓΕΣ, ΠΡΟΒΑΤΑ, ΑΙΓΟΠΡΟΒΑΤΑ.");
        }
        else{
            this.animalsType = newAnimalType;
        }
    }




    @Override
    public String toString(){
        return "Farmer{Όνοματεπώνυμο: " + fullName  + ", Κωδικός Κτηνοτρόφου: " + farmerCode + "}";
    }

}
