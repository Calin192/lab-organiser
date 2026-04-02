package com.example.laborganiser.backend.shelf;


import com.example.laborganiser.backend.vials.Vial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShelfRepo {
    private List<Shelf> shelves;
    private static final String JSON_FILE_PATH = "src/main/resources/shelves.json";
    private int currentId;

    public ShelfRepo() {
        this.shelves = loadShelvesFromJson();
        try{
            currentId = shelves.stream()
                    .mapToInt(Shelf::getId)
                    .max()
                    .orElse(0);
        }catch(Exception e){
            currentId = 0;
        }

    }

    private List<Shelf> loadShelvesFromJson() {
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
            List<Shelf> loaded = gson.fromJson(jsonContent, new TypeToken<ArrayList<Shelf>>(){}.getType());

            return (loaded != null) ? loaded : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Error reading shelf.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveShelvesToJson() {
        try {
            Path path = Paths.get(JSON_FILE_PATH);
            Gson gson = new Gson();
            String jsonContent = gson.toJson(shelves);
            Files.writeString(path, jsonContent);
        } catch (IOException e) {
            System.err.println("Error writing shelves.json: " + e.getMessage());
        }
    }

    public boolean addShelf(Shelf shelf) {
        if (shelves.size() == 0||(shelf != null && !shelves.contains(shelf))) {
            shelves.add(shelf);
            saveShelvesToJson();
            return true;
        }
        return false;
    }

    public int getNextId(){
        return ++currentId;
    }

    public List<Shelf>  getShelves(){
        return shelves;
    }
}
