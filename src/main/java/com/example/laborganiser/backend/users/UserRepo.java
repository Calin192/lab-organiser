package com.example.laborganiser.backend.users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private static final String JSON_FILE_PATH = "src/main/resources/users.json";
    private List<User> users;

    public UserRepo() {
        this.users = loadUsersFromJson();
    }

    private List<User> loadUsersFromJson() {
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
            List<User> loaded = gson.fromJson(jsonContent, new TypeToken<ArrayList<User>>(){}.getType());

            return (loaded != null) ? loaded : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Error reading users.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public boolean addUser(User user) {
        if (user != null && !users.contains(user)) {
            users.add(user);
            saveUsersToJson();
            return true;
        }
        return false;
    }
    
    public boolean deleteUser(User user) {
        if (users.remove(user)) {
            saveUsersToJson();
            return true;
        }
        return false;
    }
    
    public boolean updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                saveUsersToJson();
                return true;
            }
        }
        return false;
    }
    
    public boolean loginUser(User user) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(user.getUsername())
                           && u.getPassword().equals(user.getPassword()));
    }

    private void saveUsersToJson() {
        try {
            Path path = Paths.get(JSON_FILE_PATH);
            Gson gson = new Gson();
            String jsonContent = gson.toJson(users);
            Files.writeString(path, jsonContent);
        } catch (IOException e) {
            System.err.println("Error writing users.json: " + e.getMessage());
        }
    }

    public User getUser(String username) {
        try{
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }}
        catch (NullPointerException e){
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}
