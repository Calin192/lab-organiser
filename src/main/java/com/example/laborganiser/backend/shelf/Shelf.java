package com.example.laborganiser.backend.shelf;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private int id;
    private String name;
    private List<Integer> vials;

    public Shelf(int id, String name) {
        this.id = id;
        this.name = name;
        this.vials = new ArrayList<>();
    }

    public Shelf() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getVials() {
        return vials;
    }

    public void setVials(List<Integer> vials) {
        this.vials = vials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addVialId(int vialId) {
        this.vials.add(vialId);
    }

    public int getVialCount() {
        return (vials == null) ? 0 : vials.size();
    }

    public void removeVialId(int vialId) {
        this.vials.remove(Integer.valueOf(vialId));
    }
}
