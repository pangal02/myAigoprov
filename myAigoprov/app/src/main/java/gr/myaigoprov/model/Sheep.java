package gr.myaigoprov.model;

import androidx.room.Entity;

@Entity(tableName = "sheeps")
public class Sheep extends Animal{
    
    public Sheep(String farmerCode, int skoulariki, Gender gender, String birthdate){
        super(farmerCode, skoulariki, gender, birthdate);
    }

    @Override
    public String toString(){
        String result = "Ship{" + super.toString() + "}";
        return result;
    }

}
