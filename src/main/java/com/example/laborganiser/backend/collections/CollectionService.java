package com.example.laborganiser.backend.collections;



import com.example.laborganiser.backend.Observer;

import java.util.ArrayList;
import java.util.List;

public class CollectionService {
    private CollectionRepo collectionRepo;

    private List<Observer> observers = new ArrayList<>();

    public CollectionService(CollectionRepo collectionRepo) {
        this.collectionRepo = collectionRepo;
    }

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    private void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }

    public boolean addCollection(String name) {
        Collection collection = new Collection(collectionRepo.getNextId(), name);
        boolean result = collectionRepo.addCollection(collection);

        if(result) {
            notifyObservers();
        }

        return result;
    }

    public List<Collection> getAllShelves(){
        return collectionRepo.getCollections();
    }

    public List<Collection> getCollection(){
        return this.collectionRepo.getCollections();
    }
}
