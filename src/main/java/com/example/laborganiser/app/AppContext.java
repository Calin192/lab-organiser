package com.example.laborganiser.app;

import com.example.laborganiser.backend.users.UserRepo;
import com.example.laborganiser.backend.users.UserService;
import com.example.laborganiser.backend.vials.VialRepo;
import com.example.laborganiser.backend.vials.VialService;

public class AppContext {
    private final UserService userService;
    private final VialService vialService;

    public AppContext() {
        UserRepo userRepo = new UserRepo();
        VialRepo vialRepo = new VialRepo();

        this.userService = new UserService(userRepo);
        this.vialService = new VialService(vialRepo);
    }

    public UserService getUserService() {
        return userService;
    }

    public VialService getVialService() {
        return vialService;
    }
}