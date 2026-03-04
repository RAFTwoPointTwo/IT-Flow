package controllers;

import handlers.*;
import views.MainWindow;

public class DashboardController {

    private final MainWindow mainWindow;
    private final MaterialHandler materialHandler;
    private final MaterialTypeHandler materialTypeHandler;
    private final ComputerPackHandler computerPackHandler;
    private final AssignmentHandler assignmentHandler;
    private final PersonHandler personHandler;
    private final ServiceHandler serviceHandler;
    private final BreakDownHandler breakDownHandler;
    private final InterventionHandler interventionHandler;

    public DashboardController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        materialHandler = new MaterialHandler();
        materialTypeHandler = new MaterialTypeHandler();
        computerPackHandler = new ComputerPackHandler();
        assignmentHandler = new AssignmentHandler();
        personHandler = new PersonHandler();
        serviceHandler = new ServiceHandler();
        breakDownHandler = new BreakDownHandler();
        interventionHandler = new InterventionHandler();
    }

    public void refreshUI(){
        refreshMainData();
    }

    private void refreshMainData(){
        int allMatCount = materialHandler.getMaterialCountInDB();
        int funcMatCount = materialHandler.getFunctionalMaterialsCountInDB();
        int defMatCount = materialHandler.getDefectiveMaterialsCountInDB();
        int brokMatCount = materialHandler.getBrokenDownMaterialsCountInDB();
        double funcMatPercent = makePercent(funcMatCount , allMatCount);
        double defMatPercent = makePercent(defMatCount , allMatCount);
        double brokMatPercent = makePercent(brokMatCount , allMatCount);
        int packsCount = computerPackHandler.getComputerPacksCountInDB();
        int typesCount = materialTypeHandler.getMaterialTypesCountInDB();

        int allAssignmentsCount = assignmentHandler.getAssignmentCountInDB();
        int activeAssignmentsCount = assignmentHandler.getActiveAssignmentsCountInDB();
        int terminatedAssignmentsCount = assignmentHandler.getTerminatedAssignmentsCountInDB();
        double activeAssignmentsPercent = makePercent(activeAssignmentsCount, allAssignmentsCount);
        double terminatedAssignmentsPercent = makePercent(terminatedAssignmentsCount, allAssignmentsCount);
        int personsCount = personHandler.getPersonCountInDB();
        int servicesCount = serviceHandler.getServiceCountInDB();

        int breakDownsCount = breakDownHandler.getBreakDownCountInDB();
        int interventionsCount = interventionHandler.getInterventionCountInDB();

        mainWindow.getHomePage().getDashboardPage().updateData(packsCount , typesCount , allMatCount , funcMatPercent , defMatPercent , brokMatPercent , allAssignmentsCount , activeAssignmentsPercent , terminatedAssignmentsPercent , personsCount , servicesCount , breakDownsCount , interventionsCount);
    }

    private double makePercent(int value , int totalValue){
        if (totalValue > 0) return (100.0 * value) / totalValue;
        return 0.0;
    }

}
