package com.example.laborganiser.backend.collections;



import java.util.List;

public class CollectionService {
    private CollectionRepo collectionRepo;

    public CollectionService(CollectionRepo collectionRepo) {
        this.collectionRepo = collectionRepo;
    }

    public boolean addCollection(String name) {
        Collection collection = new Collection(collectionRepo.getNextId(), name);
        return collectionRepo.addCollection(collection);
    }

    public List<Collection> getAllShelves(){
        return collectionRepo.getCollections();
    }
}
