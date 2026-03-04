package models;

public class Technician {

    private int id;
    private String lastName;
    private String firstNames;

    public Technician(){ }

    public Technician(String lastName, String firstNames) {
        this.lastName = lastName;
        this.firstNames = firstNames;
    }

    public int getId() { return id; }

    public String getFirstNames() { return firstNames; }

    public String getLastName() { return lastName; }

    public String getFullName(){ return lastName + " " + firstNames; }

    public void setId(int id) { this.id = id; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setFirstNames(String firstNames) { this.firstNames = firstNames; }

}
