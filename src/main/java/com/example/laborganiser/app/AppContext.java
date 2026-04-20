package com.example.laborganiser.app;

import com.example.laborganiser.backend.collections.CollectionRepo;
import com.example.laborganiser.backend.collections.CollectionService;
import com.example.laborganiser.backend.shelf.ShelfRepo;
import com.example.laborganiser.backend.shelf.ShelfService;
import com.example.laborganiser.backend.users.User;
import com.example.laborganiser.backend.users.UserRepo;
import com.example.laborganiser.backend.users.UserService;
import com.example.laborganiser.backend.vials.VialRepo;
import com.example.laborganiser.backend.vials.VialService;

public class AppContext {
    private final UserService userService;
    private final VialService vialService;
    private final ShelfService shelfService;
    private final CollectionService collectionService;

    private  int height;
    private  int width;

    private User currentUser;

    public AppContext() {
        UserRepo userRepo = new UserRepo();
        VialRepo vialRepo = new VialRepo();
        ShelfRepo shelfRepo = new ShelfRepo();
        CollectionRepo collectionRepo = new CollectionRepo();



        this.userService = new UserService(userRepo);
        this.vialService = new VialService(vialRepo);
        this.shelfService = new ShelfService(shelfRepo);
        this.collectionService = new CollectionService(collectionRepo);
    }

    public UserService getUserService() { return userService; }

    public VialService getVialService() {
        return vialService;
    }

    public ShelfService getShelfService() {
        return shelfService;
    }

    public CollectionService getCollectionService() {return  collectionService;}

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }

    public int getWidth() { return width; }

    public void setWidth(int width) { this.width = width; }



}