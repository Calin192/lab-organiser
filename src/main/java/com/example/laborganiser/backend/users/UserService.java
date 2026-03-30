package com.example.laborganiser.backend.users;

import java.util.List;
import com.example.laborganiser.backend.security.PasswordUtil;

public class UserService {

    private UserRepo userRepo = new UserRepo();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean loginUser(String username, String password) {
        User user = new User(username, PasswordUtil.hashPassword(password));

        if(user==null){
            return false;
        }


        return userRepo.loginUser(user);
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void addUser(User user) {
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        userRepo.addUser(user);
    }

    public void updateUser(User user) {
        userRepo.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepo.deleteUser(user);
    }




}
