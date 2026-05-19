package com.example.laborganiser.backend.collections;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private int id;
    private String name;
    private List<Integer> shelfIds;

    public Collection(int id, String name) {
        this.id = id;
        this.name = name;
        this.shelfIds = new ArrayList<>();
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

    public List<Integer> getShelves() {
        return shelfIds;
    }

    public void setShelfIds(List<Integer> shelfIds) {
        this.shelfIds = shelfIds;
    }

    public void addShelfId(int shelfId) {
        if (!shelfIds.contains(shelfId)) {
            shelfIds.add(shelfId);
        }
    }

    public Integer getShelfCount(){
        if (shelfIds.size()>0){
            return shelfIds.size();
        }
        return 0;
    }
}
