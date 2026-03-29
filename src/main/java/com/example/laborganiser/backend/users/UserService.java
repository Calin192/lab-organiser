package com.example.laborganiser.backend.users;

import java.util.List;

public class UserService {

    private UserRepo userRepo = new UserRepo();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean loginUser(String username, String password) {
        User user = new User(username, password);
        return userRepo.loginUser(user);
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void addUser(User user) {
        userRepo.addUser(user);
    }

    public void updateUser(User user) {
        userRepo.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepo.deleteUser(user);
    }




}
