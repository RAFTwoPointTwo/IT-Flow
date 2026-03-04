package controllers;

import dialogs.AssignmentCreatingDialog;
import handlers.AssignmentHandler;
import handlers.MaterialHandler;
import handlers.PersonHandler;
import models.Assignment;
import utilities.AppColors;
import views.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class AssignmentsController {

    private final MainWindow mainWindow;
    private final AssignmentHandler assignmentHandler;
    private final PersonHandler personHandler;
    private final MaterialHandler materialHandler;
    private final DocumentListener filterListener = new DocumentListener() {

        public void insertUpdate(DocumentEvent e) { filter(); }
        public void removeUpdate(DocumentEvent e) { filter(); }
        public void changedUpdate(DocumentEvent e) { filter(); }

        public void filter() {

            AssignmentsPage assignmentsPage = mainWindow.getHomePage().getAssignmentsPage();

            String query = assignmentsPage.getSearchField().getText().toLowerCase().trim();

            List<Assignment> allAssignments = assignmentHandler.getAllAssignments();

            List<Assignment> filtered = allAssignments.stream()
                    .filter(assignment ->
                            assignment.getStartDate().toLowerCase().contains(query) ||
                            personHandler.getPersonByID(assignment.getPersonID()).getFullName().toLowerCase().contains(query) ||
                            materialHandler.getMaterialByID(assignment.getMaterialID()).getDesignation().toLowerCase().contains(query))
                    .toList();

            assignmentsPage.updateData(filtered);
            addUpdateListeners();
        }
    };

    public AssignmentsController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        assignmentHandler = new AssignmentHandler();
        personHandler = new PersonHandler();
        materialHandler = new MaterialHandler();
        initializeActions();
    }

    private void initializeActions(){
        mainWindow.getHomePage().getAssignmentsPage().getAddButton().addActionListener(this::createAssignment);
        mainWindow.getHomePage().getAssignmentsPage().getSearchField().getDocument().addDocumentListener(filterListener);
    }

    public void refreshUI(){
        refreshMainData();
        refreshBottomStats();
        addUpdateListeners();
    }

    private void refreshBottomStats(){
        JPanel bottomPanel = mainWindow.getHomePage().getAssignmentsPage().getBottomPanel();
        bottomPanel.removeAll();

        int allAssignmentsCount = assignmentHandler.getAssignmentCountInDB();
        int activeAssignmentsCount = assignmentHandler.getActiveAssignmentsCountInDB();
        int terminatedAssignmentsCount = assignmentHandler.getTerminatedAssignmentsCountInDB();

        double allAssignmentsPercent = makePercent(allAssignmentsCount, allAssignmentsCount);
        double activeAssignmentsPercent = makePercent(activeAssignmentsCount, allAssignmentsCount);
        double terminatedAssignmentsPercent = makePercent(terminatedAssignmentsCount, allAssignmentsCount);

        JPanel allAssignments = mainWindow.getHomePage().getAssignmentsPage().createBottomCard("Toutes les affectations", allAssignmentsCount, allAssignmentsPercent, AppColors.GRAY_COLOR);
        JPanel activeAssignments = mainWindow.getHomePage().getAssignmentsPage().createBottomCard("Affectations en cours", activeAssignmentsCount, activeAssignmentsPercent, AppColors.YELLOW_COLOR);
        JPanel terminatedAssignments = mainWindow.getHomePage().getAssignmentsPage().createBottomCard("Affectations terminées", terminatedAssignmentsCount, terminatedAssignmentsPercent, AppColors.GREEN_COLOR);

        bottomPanel.add(allAssignments);
        bottomPanel.add(activeAssignments);
        bottomPanel.add(terminatedAssignments);

        bottomPanel.revalidate();
        bottomPanel.repaint();

    }

    private void addUpdateListeners(){
        AssignmentsPage assignmentsPage = mainWindow.getHomePage().getAssignmentsPage();
        if (assignmentsPage.getUpdateButtons().isEmpty()) return;
        for (JButton button : assignmentsPage.getUpdateButtons()){
            button.addActionListener(event -> {
                Assignment assignment = (Assignment) button.getClientProperty("assignment");
                updateAssignment(assignment , assignmentsPage);
            });
        }
    }

    private void createAssignment(ActionEvent event){
        AssignmentsPage assignmentsPage = mainWindow.getHomePage().getAssignmentsPage();
        AssignmentCreatingDialog dialog = new AssignmentCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(assignmentsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {

            Assignment assignment = dialog.createAssignment();

            boolean inserted = assignmentHandler.saveAssignment(assignment);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(assignmentsPage, "Affectation enregistrée avec succès !" , "Nouvelle affectation" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(assignmentsPage, "Une erreur est subvenue lors de l'ajout de l'affectation...", "Erreur d'ajout de l'affectation", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.dispose();
    }

    private void updateAssignment(Assignment assignment , AssignmentsPage page){
        int response = page.askUpdateConfirmation();
        if (response == JOptionPane.OK_OPTION){
            boolean updated = assignmentHandler.endAssignment(assignment);
            if (updated) {
                refreshUI();
                JOptionPane.showMessageDialog(page, "L'affectation a été finalisée avec succès ! Le matériel assigné est à nouveau disponible." , "Finalisation d'affectation" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(page, "Une erreur est subvenue lors de la mise à jour d'une affectation...", "Erreur de mise à jour", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshMainData(){
        List<Assignment> assignmentsInBase = assignmentHandler.getAllAssignments();
        mainWindow.getHomePage().getAssignmentsPage().updateData(assignmentsInBase);
    }

    private double makePercent(int value , int totalValue){
        if (totalValue > 0) return (100.0 * value) / totalValue;
        return 0.0;
    }
}
