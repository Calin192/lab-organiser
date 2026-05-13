package com.example.laborganiser.backend.shelf;

import com.example.laborganiser.backend.Observer;
import com.example.laborganiser.backend.vials.Vial;

import java.util.ArrayList;
import java.util.List;

public class ShelfService {

    private ShelfRepo shelfRepo;
    private List<Observer> observers = new ArrayList<>();

    public ShelfService(ShelfRepo shelfRepo) {
        this.shelfRepo = shelfRepo;
    }

    public Integer addShelf(String name) {
        int id = shelfRepo.getNextId();
        Shelf shelf = new Shelf(id, name);

        shelfRepo.addShelf(shelf);

        notifyObservers();
        return id;

    }

    public List<Shelf> getAllShelves(){
        return shelfRepo.getShelves();
    }

    public Shelf getShelf(String name){
        return shelfRepo.getShelf(name);
    }

    public Shelf getShelfById(Integer id){
        return shelfRepo.getShelfById(id);
    }

    public int getShelfIdByName(String name) {
        return shelfRepo.getShelfId(name);
    }

    public boolean addVial(Shelf shelf, Integer vialId) {
        if(shelf != null && vialId != 0)
        {
            shelfRepo.addVial(shelf, vialId);
            return true;
        }
        return false;

    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
