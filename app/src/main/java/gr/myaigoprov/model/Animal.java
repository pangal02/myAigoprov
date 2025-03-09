package gr.myaigoprov.model;

public abstract class Animal{
    private int id;
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

    public Animal(int id, String farmerCode, int tagNumber, Gender gender, String birthdate) {
        this.id = id;
        this.farmerCode = farmerCode;
        this.tagNumber = tagNumber;
        this.gender = gender;
        this.birthdate = birthdate;
        this.youngMom = false;
    }

    public Animal(int id, String farmerCode, int tagNumber, Gender gender, String birthdate, boolean youngMom){
        this.id = id;
        this.farmerCode = farmerCode;
        this.tagNumber = tagNumber;
        this.gender = gender;
        this.birthdate = birthdate;
        this.youngMom = youngMom;
    }

    public int getId(){
        return id;
    }

    public void setId(int newId){
        if(newId > -1){
            this.id = newId;
        }
        else {
            throw new IllegalArgumentException("Μη αποδεκτή τιμή ID για το ζώω: " + tagNumber + "! Μόνο ακέραιες θετικές τιμές!");
        }

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

    @Override
    public String toString(){
        String result = "{ID: " + id + ", TAG: " + tagNumber + ", fCode: " + farmerCode + ", Gender: " + gender
                + ", birthdate: " + birthdate  + "}";
        return result;
    }
}
