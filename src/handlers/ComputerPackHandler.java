package handlers;

import DAO.ComputerPackDAO;
import models.ComputerPack;

import java.util.ArrayList;
import java.util.List;

public class ComputerPackHandler {

    private final ComputerPackDAO computerPackDAO;

    public ComputerPackHandler() {
        computerPackDAO = new ComputerPackDAO();
    }

    public boolean saveComputerPack(ComputerPack computerPack) {

        if (computerPack == null) return false;
        if (computerPack.getName() == null || computerPack.getName().isBlank()) return false;
        if (computerPack.getDescription() == null || computerPack.getDescription().isBlank()) return false;

        return computerPackDAO.insertComputerPack(computerPack);

    }

    public List<ComputerPack> getAllComputerPacks() {
        List<ComputerPack> computerPacks = computerPackDAO.getAll();
        return (computerPacks == null) ? new ArrayList<>() : computerPacks;
    }

    public ComputerPack getComputerPackByID(int computerPackID) {

        ComputerPack computerPackToGet = computerPackDAO.getByID(computerPackID);

        if (computerPackToGet == null) return new ComputerPack("Pack Inconnu" , "Pack Inconnu");

        return computerPackToGet;

    }

    public int getComputerPacksCountInDB(){ return computerPackDAO.getCountInDB(); }

}
