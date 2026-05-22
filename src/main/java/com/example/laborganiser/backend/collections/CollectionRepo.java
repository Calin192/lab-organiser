package com.example.laborganiser.backend.collections;

import com.example.laborganiser.backend.shelf.Shelf;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CollectionRepo {
    private List<Collection> collections;
    private static final String JSON_FILE_PATH = "src/main/resources/collections.json";
    private int currentId;

    public CollectionRepo() {
        this.collections = loadCollectionsFromJson();
        try{
            currentId = collections.stream()
                    .mapToInt(Collection::getId)
                    .max()
                    .orElse(0);
        }catch(Exception e){
            currentId = 0;
        }
    }

    private List<Collection> loadCollectionsFromJson() {
        try {
            Path path = Paths.get(JSON_FILE_PATH);

            if (!Files.exists(path)) {
                return new ArrayList<>();
            }

            String jsonContent = Files.readString(path);

            if (jsonContent.isBlank()) {
                return new ArrayList<>();
            }

            Gson gson = new Gson();
            List<Collection> loaded = gson.fromJson(jsonContent, new TypeToken<ArrayList<Collection>>(){}.getType());

            if (loaded != null) {
                for (Collection collection : loaded) {
                    if (collection.getShelves() == null) {
                        collection.setShelfIds(new ArrayList<>());
                    }
                }
            }

            return (loaded != null) ? loaded : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Error reading collections.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveCollectionsToJson() {
        try {
            Path path = Paths.get(JSON_FILE_PATH);
            Gson gson = new Gson();
            String jsonContent = gson.toJson(collections);
            Files.writeString(path, jsonContent);
        } catch (IOException e) {
            System.err.println("Error writing collections.json: " + e.getMessage());
        }
    }

    public boolean addCollection(Collection collection) {
        if (collections.size() == 0||(collection != null && !collections.contains(collection))) {
            collections.add(collection);
            saveCollectionsToJson();
            return true;
        }
        return false;
    }

    public boolean removeCollection(Collection collection) {
        if(collections.contains(collection)){
            collections.remove(collection);
            saveCollectionsToJson();
            return true;
        }
        return false;
    }

    public int getNextId(){
        return ++currentId;
    }

    public List<Collection>  getCollections(){
        return collections;
    }

    public boolean addShelf(Collection collection, int shelfId) {
        if (collection != null) {
            Collection foundCollection = collections.stream()
                    .filter(c -> c.getId() == collection.getId())
                    .findFirst()
                    .orElse(null);

            if (foundCollection != null) {
                foundCollection.addShelfId(shelfId);
                saveCollectionsToJson();
                return true;
            }
        }
        return false;
    }

    public void removeShelf(Shelf shelf, Collection collection) {
        collection.removeShelf(shelf.getId());
        saveCollectionsToJson();
    }
}
