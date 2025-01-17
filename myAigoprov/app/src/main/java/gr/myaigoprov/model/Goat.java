package gr.myaigoprov.model;

public class Goat extends Animal{
    
    public Goat(String farmerCode, int tagNumber, Gender gender, String birthdate){
        super(farmerCode, tagNumber, gender, birthdate);
    }
    public Goat(int id, String farmerCode, int tagNumber, Gender gender, String birthdate){
        super(id, farmerCode, tagNumber, gender, birthdate);
    }

    public Goat(int id, String farmerCode, int tagNumber, Gender gender, String birthdate, boolean youngMom){
        super(id, farmerCode, tagNumber, gender, birthdate, youngMom);
    }

    @Override
    public String toString(){
        String result = "Goat" + super.toString();
        return result;
    }

}
