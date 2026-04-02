package com.example.laborganiser.backend.collections;

import com.example.laborganiser.backend.shelf.Shelf;

import java.util.List;

public class Collection {
    private int id;
    private String name;
    private List<Shelf> shelves;

    public Collection(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Collection(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(List<Shelf> shelves) {
        this.shelves = shelves;
    }
}
