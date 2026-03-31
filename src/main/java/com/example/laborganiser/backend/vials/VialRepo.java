package com.example.laborganiser.backend.vials;

import com.example.laborganiser.backend.users.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VialRepo {
    private List<Vial> vials;
    private static final String JSON_FILE_PATH = "src/main/resources/vials.json";
    private int currentId;

    public VialRepo(List<Vial> vials) {
        this.vials = vials;
    }

    public VialRepo() {
        this.vials = loadVialsFromJson();

        try{
            currentId = vials.stream()
                    .mapToInt(Vial::getId)
                    .max()
                    .orElse(0);
        }catch(Exception e){
            currentId = 0;
        }

    }

    private List<Vial> loadVialsFromJson() {
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
            List<Vial> loaded = gson.fromJson(jsonContent, new TypeToken<ArrayList<Vial>>(){}.getType());

            return (loaded != null) ? loaded : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Error reading vials.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveVialsToJson() {
        try {
            Path path = Paths.get(JSON_FILE_PATH);
            Gson gson = new Gson();
            String jsonContent = gson.toJson(vials);
            Files.writeString(path, jsonContent);
        } catch (IOException e) {
            System.err.println("Error writing vials.json: " + e.getMessage());
        }
    }

    public boolean addVial(Vial vial) {
            if (vials.size() == 0||(vial != null && !vials.contains(vial))) {
                vials.add(vial);
                saveVialsToJson();
                return true;
            }
            return false;
    }

    private boolean removeVial(Vial vial) {
        if (vials.remove(vial)) {
            saveVialsToJson();
            return true;
        }
        return false;
    }

    public boolean updateVial(Vial vial) {
        for (int i = 0; i < vials.size(); i++) {
            if (vials.get(i).getId()==vial.getId()) {
                vials.set(i, vial);
                saveVialsToJson();
                return true;
            }
        }
        return false;
    }

    public int getVialCount() {
        return vials.size();
    }

    public int getNextId(){
        return ++currentId;
    }

}
