package gr.myaigoprov.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


public abstract class Animal{
    private String farmerCode;
    private int tagNumber;
    private Gender gender;
    private Boolean youngMom;
    private String  birthdate;

    public Animal(String farmerCode, int tagNumber, Gender gender, String birthdate) {
        this.farmerCode = farmerCode;
        this.tagNumber = tagNumber;
        this.gender = gender;
        this.birthdate = birthdate;
        this.youngMom = false;
    }

    public String getFarmerCode() {
        return farmerCode;
    }

    public void setFarmerCode(String farmerCode) {
        this.farmerCode = farmerCode;
    }

    public int getTagNumber() {
        return tagNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isYoungMom() {
        return youngMom;
    }

    public void setYoungMom(Boolean hasBeYoungMom) {
        if (gender == Gender.FEMALE) {
            this.youngMom = hasBeYoungMom;
        } else {
            throw new IllegalArgumentException(
                    "[ΣΦΑΛΜΑ]> Μόνο θηλυκά ζώα μπορούν να είναι κάτω του έτους που έχουν γεννήσει.");
        }
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

}
