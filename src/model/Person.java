package model;

public abstract class Person {
    protected int id;
    protected String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "] id=" + id + ", name='" + name + "'";
    }
}
