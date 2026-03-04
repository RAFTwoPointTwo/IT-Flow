package controllers;

import dialogs.BreakDownCreatingDialog;
import handlers.BreakDownHandler;
import handlers.BreakDownOccurrenceHandler;
import handlers.MaterialHandler;
import models.BreakdownOccurrence;
import views.BreakDownsPage;
import views.MainWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class BreakDownsController {
    private final MainWindow mainWindow;
    private final BreakDownHandler breakDownHandler;
    private final BreakDownOccurrenceHandler breakDownOccurrenceHandler;
    private final MaterialHandler materialHandler;
    private final DocumentListener filterListener = new DocumentListener() {

        public void insertUpdate(DocumentEvent e) { filter(); }
        public void removeUpdate(DocumentEvent e) { filter(); }
        public void changedUpdate(DocumentEvent e) { filter(); }

        public void filter() {
            BreakDownsPage breakDownsPage = mainWindow.getHomePage().getBreakDownsPage();

            String query = breakDownsPage.getSearchField().getText().toLowerCase().trim();

            List<BreakdownOccurrence> allBreakDownOccurrences = breakDownOccurrenceHandler.getAllBreakDownOccurrences();

            List<BreakdownOccurrence> filtered = allBreakDownOccurrences.stream()
                    .filter(breakdownOccurrence ->
                            breakdownOccurrence.getBreakdownDate().toLowerCase().contains(query) ||
                            materialHandler.getMaterialByID(breakdownOccurrence.getMaterialID()).getDesignation().toLowerCase().contains(query) ||
                            breakDownHandler.getBreakDownByID(breakdownOccurrence.getBreakdownID()).getDescription().toLowerCase().contains(query))
                    .toList();

            breakDownsPage.updateData(filtered);
        }
    };

    public BreakDownsController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        breakDownHandler = new BreakDownHandler();
        breakDownOccurrenceHandler = new BreakDownOccurrenceHandler();
        materialHandler = new MaterialHandler();
        initializeActions();
    }

    private void initializeActions(){
        mainWindow.getHomePage().getBreakDownsPage().getAddButton().addActionListener(this::createBreakDown);
        mainWindow.getHomePage().getBreakDownsPage().getSearchField().getDocument().addDocumentListener(filterListener);
    }

    public void refreshUI(){
        refreshMainData();
    }

    private void refreshMainData(){
        List<BreakdownOccurrence> breakdownOccurrencesInBase = breakDownOccurrenceHandler.getAllBreakDownOccurrences();
        mainWindow.getHomePage().getBreakDownsPage().updateData(breakdownOccurrencesInBase);
    }

    private void createBreakDown(ActionEvent event) {

        BreakDownsPage breakDownsPage = mainWindow.getHomePage().getBreakDownsPage();
        BreakDownCreatingDialog dialog = new BreakDownCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(breakDownsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            BreakdownOccurrence breakdownOccurrence = dialog.createBreakDown();

            boolean inserted = breakDownOccurrenceHandler.saveBreakDownOccurrence(breakdownOccurrence);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(breakDownsPage, "Panne enregistrée avec succès !" , "Déclaration de panne" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(breakDownsPage, "Une erreur est subvenue lors de l'enregistrement de cette panne...", "Erreur d'enregistrement de panne", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.dispose();
    }

}