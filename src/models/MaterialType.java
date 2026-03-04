package models;

public class MaterialType {

    private int id;
    private String name;

    public MaterialType(){ }

    public MaterialType(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return this.name; }

}
