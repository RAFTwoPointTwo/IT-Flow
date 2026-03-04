package models;

public class Person {

    private int id;
    private String lastName;
    private String firstNames;
    private String  function;
    private int serviceID;

    public Person(){ }

    public Person(String lastName , String firstNames, String function, int serviceID) {
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.function = function;
        this.serviceID = serviceID;
    }

    public Person(String lastName , String firstNames){
        this.lastName = lastName;
        this.firstNames = firstNames;
    }

    public int getId() { return id; }

    public String getFirstNames() { return firstNames; }

    public String getLastName() { return lastName; }

    public String getFullName(){ return lastName + " " + firstNames; }

    public String getFunction() { return function; }

    public int getServiceID() { return serviceID; }

    public void setId(int id) { this.id = id; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setFirstNames(String firstNames) { this.firstNames = firstNames; }

    public void setFunction(String function) { this.function = function; }

    public void setServiceID(int serviceID) { this.serviceID = serviceID; }

}

