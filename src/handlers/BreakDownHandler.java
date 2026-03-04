package handlers;

import DAO.BreakDownDAO;
import models.BreakDown;

import java.util.ArrayList;
import java.util.List;

public class BreakDownHandler {

    private final BreakDownDAO breakDownDAO;

    public BreakDownHandler() {
        breakDownDAO = new BreakDownDAO();
    }

    public boolean saveBreakDown(BreakDown breakDown) {

        if (breakDown == null) return false;
        if (breakDown.getDescription() == null || breakDown.getDescription().isBlank()) return false;

        return breakDownDAO.insertBreakDown(breakDown);

    }

    public List<BreakDown> getAllBreakDowns() {
        List<BreakDown> breakDowns = breakDownDAO.getAll();
        return (breakDowns == null) ? new ArrayList<>() : breakDowns;
    }

    public BreakDown getBreakDownByID(int breakDownID) {

        BreakDown breakDownToGet = breakDownDAO.getByID(breakDownID);

        if (breakDownToGet == null) return new BreakDown("Panne Inconnue");

        return breakDownToGet;

    }

    public int getBreakDownCountInDB(){ return breakDownDAO.getCountInDB(); }

}
