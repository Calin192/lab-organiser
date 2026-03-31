package com.example.laborganiser.backend.vials;

public class VialService {
    private VialRepo vialRepo = new VialRepo();

    public VialService() {}

    public VialService(VialRepo vialRepo) {
        this.vialRepo = vialRepo;
    }

    public void addVial(String name, String material, String shape, String size, String unit,String color, String cap, String capColor, String description, String owner) {
        int id = vialRepo.getNextId();

        Vial vial = new Vial(id, name, material, shape, size, unit, color, cap, capColor, description,owner);

        vialRepo.addVial(vial);
    }
}
