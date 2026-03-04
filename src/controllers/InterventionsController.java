package controllers;

import dialogs.InterventionCreatingDialog;
import handlers.BreakDownHandler;
import handlers.InterventionHandler;
import handlers.MaterialHandler;
import handlers.TechnicianHandler;
import models.Intervention;
import views.InterventionsPage;
import views.MainWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class InterventionsController {

    private final MainWindow mainWindow;
    private final InterventionHandler interventionHandler;
    private final BreakDownHandler breakDownHandler;
    private final MaterialHandler materialHandler;
    private final TechnicianHandler technicianHandler;
    private final DocumentListener filterListener = new DocumentListener() {

        public void insertUpdate(DocumentEvent e) { filter(); }
        public void removeUpdate(DocumentEvent e) { filter(); }
        public void changedUpdate(DocumentEvent e) { filter(); }

        public void filter() {

            InterventionsPage interventionsPage = mainWindow.getHomePage().getInterventionsPage();

            String query = interventionsPage.getSearchField().getText().toLowerCase().trim();

            List<Intervention> interventions = interventionHandler.getAllInterventions();

            List<Intervention> filtered = interventions.stream()
                    .filter(intervention ->
                            intervention.getInterventionDate().toLowerCase().contains(query) ||
                            breakDownHandler.getBreakDownByID(intervention.getBreakdownID()).getDescription().toLowerCase().contains(query) ||
                            technicianHandler.getTechnicianByID(intervention.getTechnicianID()).getFullName().toLowerCase().contains(query) ||
                            materialHandler.getMaterialByID(intervention.getMaterialID()).getDesignation().toLowerCase().contains(query))
                    .toList();

            interventionsPage.updateData(filtered);
        }
    };

    public InterventionsController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        interventionHandler = new InterventionHandler();
        breakDownHandler = new BreakDownHandler();
        technicianHandler = new TechnicianHandler();
        materialHandler = new MaterialHandler();
        initializeActions();
    }

    public void refreshUI(){
        refreshMainData();
    }

    private void initializeActions(){
        mainWindow.getHomePage().getInterventionsPage().getAddButton().addActionListener(this::createIntervention);
        mainWindow.getHomePage().getInterventionsPage().getSearchField().getDocument().addDocumentListener(filterListener);
    }

    private void refreshMainData(){
        List<Intervention> interventionsInBase = interventionHandler.getAllInterventions();
        mainWindow.getHomePage().getInterventionsPage().updateData(interventionsInBase);
    }

    private void createIntervention(ActionEvent event){
        InterventionsPage interventionsPage = mainWindow.getHomePage().getInterventionsPage();
        InterventionCreatingDialog dialog = new InterventionCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(interventionsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {

            Intervention intervention = dialog.createIntervention();

            boolean inserted = interventionHandler.saveIntervention(intervention);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(interventionsPage, "Intervention enregistrée avec succès !" , "Nouvelle intervention" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(interventionsPage, "Une erreur est subvenue lors de l'enregistrement de cette intervention...", "Erreur d'enregistrement d'intervention", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.dispose();
    }

}
