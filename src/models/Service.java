package models;

public class Service {

    private int id;
    private String name;
    private String description;

    public Service(){ }

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

}