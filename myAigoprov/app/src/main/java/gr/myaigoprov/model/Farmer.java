package gr.myaigoprov.model;

public class Farmer {
    private String name;
    private String lastName;
    private final String username;
    private final String farmerCode;


    public Farmer(String name, String lastname, String farmerCode, String username) {
        this.name = name;
        this.lastName = lastname;
        this.farmerCode = farmerCode;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFarmerCode() {
        return farmerCode;
    }

    public String getUsername(){
        return username;
    }




    @Override
    public String toString(){
        return "Farmer{Όνοματεπώνυμο: " + name + " " + lastName + ", Κωδικός Κτηνοτρόφου: " + farmerCode + ", Username: " + username + "}";
    }

}
