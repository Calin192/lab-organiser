package com.example.laborganiser.backend.shelf;

import java.util.List;

public class ShelfService {

    private ShelfRepo shelfRepo;

    public ShelfService(ShelfRepo shelfRepo) {
        this.shelfRepo = shelfRepo;
    }

    public boolean addShelf(String name) {
        Shelf shelf = new Shelf(shelfRepo.getNextId(), name);
        return shelfRepo.addShelf(shelf);
    }

    public List<Shelf> getAllShelves(){
        return shelfRepo.getShelves();
    }
}
