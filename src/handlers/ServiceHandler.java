package handlers;

import DAO.ServiceDAO;
import models.Service;

public class ServiceHandler {

    private final ServiceDAO serviceDAO;

    public ServiceHandler(){
        serviceDAO = new ServiceDAO();
    }

    public boolean saveService(Service service){

        if (service == null) return false;
        if (service.getName() == null || service.getName().isBlank()) return false;
        if (service.getDescription() == null || service.getDescription().isBlank()) return false;

        return serviceDAO.insertService(service);

    }

    public int getServiceCountInDB(){ return serviceDAO.getCountInDB(); }

}
