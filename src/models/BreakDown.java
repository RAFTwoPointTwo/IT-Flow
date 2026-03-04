package models;

public class BreakDown {

    private int id;
    private String description;

    public BreakDown(){ }

    public BreakDown(String description) {
        this.description = description;
    }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() { return this.getDescription(); }

}
