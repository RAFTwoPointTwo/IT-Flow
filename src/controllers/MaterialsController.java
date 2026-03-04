package controllers;

import dialogs.MaterialCreatingDialog;
import dialogs.MaterialEditingDialog;
import handlers.ComputerPackHandler;
import handlers.MaterialHandler;
import handlers.MaterialTypeHandler;
import models.Material;
import utilities.AppColors;
import views.MainWindow;
import views.MaterialsPage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class MaterialsController {

    private final MainWindow mainWindow;
    private final MaterialHandler materialHandler;
    private final MaterialTypeHandler materialTypeHandler;
    private final ComputerPackHandler computerPackHandler;
    private final DocumentListener filterListener = new DocumentListener() {

        public void insertUpdate(DocumentEvent e) { filter(); }
        public void removeUpdate(DocumentEvent e) { filter(); }
        public void changedUpdate(DocumentEvent e) { filter(); }

        public void filter() {

            MaterialsPage materialsPage = mainWindow.getHomePage().getMaterialsPage();

            String query = materialsPage.getSearchField().getText().toLowerCase().trim();

            List<Material> allMaterials = materialHandler.getAllMaterials();

            List<Material> filtered = allMaterials.stream()
                    .filter(material ->
                            material.getDesignation().toLowerCase().contains(query) ||
                            material.getSpecification().toLowerCase().contains(query) ||
                            material.getBrand().toLowerCase().contains(query) ||
                            material.getAcquisitionDate().toLowerCase().contains(query) ||
                            material.getState().toLowerCase().contains(query) ||
                            material.getAvailabilityToString().toLowerCase().startsWith(query) ||
                            materialTypeHandler.getMaterialTypeByID(material.getTypeID()).getName().toLowerCase().contains(query) ||
                            computerPackHandler.getComputerPackByID(material.getPackID()).getName().toLowerCase().contains(query) ||
                            material.getSerialNumber().toLowerCase().contains(query))
                    .toList();

            materialsPage.updateData(filtered);
            addEditListeners();
        }
    };

    public MaterialsController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        materialHandler = new MaterialHandler();
        materialTypeHandler = new MaterialTypeHandler();
        computerPackHandler = new ComputerPackHandler();
        initializeActions();
    }

    private void initializeActions(){
        mainWindow.getHomePage().getMaterialsPage().getAddButton().addActionListener(this::createMaterial);
        mainWindow.getHomePage().getMaterialsPage().getSearchField().getDocument().addDocumentListener(filterListener);

    }

    public void refreshUI(){
        refreshMainData();
        refreshBottomStats();
        addEditListeners();
    }

    private void refreshBottomStats(){

        JPanel bottomPanel = mainWindow.getHomePage().getMaterialsPage().getBottomPanel();

        bottomPanel.removeAll();

        int allMatCount = materialHandler.getMaterialCountInDB();
        int funcMatCount = materialHandler.getFunctionalMaterialsCountInDB();
        int defMatCount = materialHandler.getDefectiveMaterialsCountInDB();
        int brokMatCount = materialHandler.getBrokenDownMaterialsCountInDB();

        double allMatPercent = makePercent(allMatCount , allMatCount);
        double funcMatPercent = makePercent(funcMatCount , allMatCount);
        double defMatPercent = makePercent(defMatCount , allMatCount);
        double brokMatPercent = makePercent(brokMatCount , allMatCount);

        JPanel allMat = mainWindow.getHomePage().getMaterialsPage().createBottomCard("Tous les matériaux" , allMatCount , allMatPercent , AppColors.GRAY_COLOR);
        JPanel funcMat = mainWindow.getHomePage().getMaterialsPage().createBottomCard("Matériaux fonctionnels" , funcMatCount , funcMatPercent , AppColors.GREEN_COLOR);
        JPanel defMat = mainWindow.getHomePage().getMaterialsPage().createBottomCard("Matériaux défectueux" , defMatCount , defMatPercent , AppColors.YELLOW_COLOR);
        JPanel brokMat = mainWindow.getHomePage().getMaterialsPage().createBottomCard("Matériaux en panne" , brokMatCount , brokMatPercent , AppColors.RED_COLOR);

        bottomPanel.add(allMat);
        bottomPanel.add(funcMat);
        bottomPanel.add(defMat);
        bottomPanel.add(brokMat);

        bottomPanel.revalidate();
        bottomPanel.repaint();

    }

    private void refreshMainData() {
        List<Material> materialsInBase = materialHandler.getAllMaterials();
        mainWindow.getHomePage().getMaterialsPage().updateData(materialsInBase);
    }

    private void addEditListeners(){
        MaterialsPage materialsPage = mainWindow.getHomePage().getMaterialsPage();
        for (JButton button : materialsPage.getEditButtons()){
            button.addActionListener(event -> {
                Material material = (Material) button.getClientProperty("material");
                editMaterial(material);
            });
        }
    }

    private void createMaterial(ActionEvent event){

        MaterialsPage materialsPage = mainWindow.getHomePage().getMaterialsPage();
        MaterialCreatingDialog dialog = new MaterialCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(materialsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Material newMaterial = dialog.createMaterial();

            boolean inserted = materialHandler.saveMaterial(newMaterial);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(materialsPage, "Matériel ajouté avec succès !" , "Matériel enregistré" , JOptionPane.INFORMATION_MESSAGE);
            } else { JOptionPane.showMessageDialog(materialsPage, "Une erreur est subvenue lors de l'ajout du nouveau matériel...", "Erreur d'ajout du matériel", JOptionPane.ERROR_MESSAGE); }
        }

        dialog.dispose();

    }

    private void editMaterial(Material material){

        MaterialsPage materialsPage = mainWindow.getHomePage().getMaterialsPage();
        MaterialEditingDialog dialog = new MaterialEditingDialog((JFrame) SwingUtilities.getWindowAncestor(materialsPage) , material);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Material editedMaterial = dialog.updateMaterial();

            boolean edited = materialHandler.updateMaterial(editedMaterial);

            if (edited) {
                refreshUI();
                JOptionPane.showMessageDialog(materialsPage, "Les informations du matériel ont été mises à jour avec succès !" , "Matériel mis à jour" , JOptionPane.INFORMATION_MESSAGE);
            } else { JOptionPane.showMessageDialog(materialsPage, "Une erreur est subvenue lors de la mise à jour du matériel...", "Erreur de mise à jour", JOptionPane.ERROR_MESSAGE); }
        }

        dialog.dispose();

    }

    private double makePercent(int value , int totalValue){
        if (totalValue > 0) return (100.0 * value) / totalValue;
        return 0.0;
    }

}