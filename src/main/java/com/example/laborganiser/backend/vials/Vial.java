package com.example.laborganiser.backend.vials;

public class Vial {
    private int id;
    private String name;
    private String material;
    private String shape;
    private String size;
    private String unit;
    private String color;
    private String cap;
    private String capColor;
    private String description;
    private String ownder;

    public Vial(int id, String name, String material, String shape, String size,String unit, String color, String cap, String capColor, String description, String ownder) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.shape = shape;
        this.size = size;
        this.unit = unit;
        this.color = color;
        this.cap = cap;
        this.capColor = capColor;
        this.description = description;
    }

    public Vial() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCapColor() {
        return capColor;
    }

    public void setCapColor(String capColor) {
        this.capColor = capColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getOwner(){
        return ownder;
    }
    public void setOwner(String ownder){
        this.ownder = ownder;
    }
}
