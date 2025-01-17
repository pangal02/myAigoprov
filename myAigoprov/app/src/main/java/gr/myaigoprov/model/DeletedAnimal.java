package gr.myaigoprov.model;

public class DeletedAnimal {
    private Animal animal;
    private String animalType;
    private String exitType;
    public DeletedAnimal(Animal animal, String exitType) {
        this.animal = animal;
        if(animal instanceof Goat){
            animalType = "ΑΙΓΑ";
        }
        else if(animal instanceof Sheep){
            animalType = "ΠΡΟΒΑΤΟ";
        }
        this.exitType = exitType;
    }

    public DeletedAnimal(Animal animal, String animalType, String exitType){
        this.animal = animal;
        this.animalType = animalType;
        this.exitType = exitType;
    }

    public Animal getAnimal(){
        return animal;
    }

    public int getId(){
        return animal.getId();
    }
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getExitType() {
        return exitType;
    }

    public void setExitType(String exitType) {
        this.exitType = exitType;
    }

    public String toString(){
        return "(" +animalType + ")" + animal.toString();
    }
}
