package gr.myaigoprov.model;

import androidx.room.Entity;

@Entity(tableName = "goats")
public class Goat extends Animal{
    
    public Goat(String farmerCode, int tagNumber, Gender gender, String birthdate){
        super(farmerCode, tagNumber, gender, birthdate);
    }

    @Override
    public String toString(){
        String result = "Goat{" + super.toString() + "}";
        return result;
    }

}
