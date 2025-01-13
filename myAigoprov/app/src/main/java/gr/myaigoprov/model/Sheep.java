package gr.myaigoprov.model;

public class Sheep extends Animal{
    
    public Sheep(String farmerCode, int skoulariki, Gender gender, String birthdate){
        super(farmerCode, skoulariki, gender, birthdate);
    }

    public Sheep(int id, String farmerCode, int skoulariki, Gender gender, String birthdate){
        super(id, farmerCode, skoulariki, gender, birthdate);
    }

    @Override
    public String toString(){
        String result = "Ship{" + super.toString() + "}";
        return result;
    }

}
