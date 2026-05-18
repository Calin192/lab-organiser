package com.example.laborganiser.backend.vials;

import com.example.laborganiser.backend.Observer;
import com.example.laborganiser.backend.shelf.Shelf;

import java.util.ArrayList;
import java.util.List;

public class VialService {
    private VialRepo vialRepo = new VialRepo();
    private List<Observer> observers = new ArrayList<>();

    public VialService() {}

    public VialService(VialRepo vialRepo) {
        this.vialRepo = vialRepo;
    }

    public Integer addVial(String name, String material, String shape, String size, String unit,String color, String cap, String capColor, String description, String owner) {
        int id = vialRepo.getNextId();

        Vial vial = new Vial(id, name, material, shape, size, unit, color, cap, capColor, description,owner);

        vialRepo.addVial(vial);

        notifyObservers();
        return id;
    }

    public void removeVial(int id) {
        Vial vial = vialRepo.getVial(id);
        vialRepo.removeVial(vial);
    }

    public List<Vial> getVials() {
        return vialRepo.getVials();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }


    public Vial getVialById(Integer vialId) {
        return vialRepo.getVial(vialId);
    }
}
