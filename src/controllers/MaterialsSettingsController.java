package controllers;

import dialogs.ComputerPackCreatingDialog;
import dialogs.MaterialTypeCreatingDialog;
import handlers.ComputerPackHandler;
import handlers.MaterialTypeHandler;
import models.ComputerPack;
import models.MaterialType;
import views.MainWindow;
import views.MaterialsSettingsPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MaterialsSettingsController {

    private final MainWindow mainWindow;
    private final ComputerPackHandler computerPackHandler;
    private final MaterialTypeHandler materialTypeHandler;

    public MaterialsSettingsController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        computerPackHandler = new ComputerPackHandler();
        materialTypeHandler = new MaterialTypeHandler();
        initializeActions();
    }

    public void refreshUI(){
        refreshMainDate();
    }

    private void initializeActions(){
        mainWindow.getHomePage().getMaterialsSettingsPage().getAddPackButton().addActionListener(this::createPack);
        mainWindow.getHomePage().getMaterialsSettingsPage().getAddTypeButton().addActionListener(this::createType);
    }

    private void refreshMainDate(){
        List<ComputerPack> computerPacks = computerPackHandler.getAllComputerPacks();
        List<MaterialType> materialTypes = materialTypeHandler.getAllMaterialTypes();
        mainWindow.getHomePage().getMaterialsSettingsPage().updateData(computerPacks , materialTypes);
    }

    private void createPack(ActionEvent event){
        MaterialsSettingsPage materialsSettingsPage = mainWindow.getHomePage().getMaterialsSettingsPage();
        ComputerPackCreatingDialog dialog = new ComputerPackCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(materialsSettingsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {

            ComputerPack pack = dialog.createComputerPack();

            boolean inserted = computerPackHandler.saveComputerPack(pack);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(materialsSettingsPage, "Nouveau pack enregistré avec succès !" , "Nouveau pack" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(materialsSettingsPage, "Une erreur est subvenue lors de l'enregistrement du pack...", "Erreur d'enregistrement de pack de matériaux", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.dispose();
    }

    private void createType(ActionEvent event){
        MaterialsSettingsPage materialsSettingsPage = mainWindow.getHomePage().getMaterialsSettingsPage();
        MaterialTypeCreatingDialog dialog = new MaterialTypeCreatingDialog((JFrame) SwingUtilities.getWindowAncestor(materialsSettingsPage));
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {

            MaterialType materialType = dialog.createMaterialtype();

            boolean inserted = materialTypeHandler.saveMaterialType(materialType);

            if (inserted) {
                refreshUI();
                JOptionPane.showMessageDialog(materialsSettingsPage, "Type de matériel enregistré avec succès !" , "Nouveau type" , JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(materialsSettingsPage, "Une erreur est subvenue lors de l'enregistrement de ce type de matériel...", "Erreur d'enregistrement de type de matériel", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.dispose();
    }

}