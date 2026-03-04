package handlers;

import DAO.IdentifiersDAO;

import java.util.ArrayList;
import java.util.List;

public class IdentifiersHandler {

    private final IdentifiersDAO identifiersDAO;

    public IdentifiersHandler() {
        identifiersDAO = new IdentifiersDAO();
    }

    public boolean saveIdentifiers(List<String> identifiers){
        if (identifiers == null ||
            identifiers.size() != 2 ||
            identifiers.get(0) == null ||
            identifiers.get(1) == null) return false;

        return identifiersDAO.updateIdentifiers(identifiers);
    }

    public List<String> getAdminIdentifiers(){

        List<String> identifiers = identifiersDAO.getIdentifiers();

        if (identifiers == null ||
            identifiers.size() != 2 ||
            identifiers.get(0) == null ||
            identifiers.get(1) == null) return new ArrayList<>();

        return identifiers;

    }

}
