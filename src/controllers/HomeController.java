package controllers;

import utilities.AppColors;
import utilities.PageNames;
import views.MainWindow;
import views.SideBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeController {

    private final MainWindow mainWindow;
    private final MaterialsController materialsController;
    private final AssignmentsController assignmentsController;
    private final BreakDownsController breakDownsController;
    private final DashboardController dashboardController;
    private final InterventionsController interventionController;
    private final MaterialsSettingsController materialsSettingsController;
    private final ActionListener buttonsListener;
    private JButton activeButton = null;


    public HomeController(MainWindow mainWindow , MaterialsController materialsController, AssignmentsController assignmentsController , BreakDownsController breakDownsController, DashboardController dashboardController , InterventionsController interventionsController , MaterialsSettingsController materialsSettingsController){
        this.mainWindow = mainWindow;
        this.materialsController = materialsController;
        this.assignmentsController = assignmentsController;
        this.dashboardController = dashboardController;
        this.breakDownsController = breakDownsController;
        this.interventionController = interventionsController;
        this.materialsSettingsController = materialsSettingsController;
        onMenuButtonClicked(mainWindow.getHomePage().getSideBar().getDashboardButton());
        buttonsListener = (event -> onMenuButtonClicked((JButton) event.getSource()));
        initializeActions();
    }

    private void onMenuButtonClicked(JButton clickedButton) {
        if (activeButton != null) activeButton.setBackground(AppColors.DARK_BLUE_COLOR);
        activeButton = clickedButton;
        activeButton.setBackground(AppColors.BASE_BLUE_COLOR);
        switch (activeButton.getActionCommand()){
            case (PageNames.MATERIALS_PAGE) -> materialsController.refreshUI();
            case (PageNames.ASSIGNMENTS_PAGE) -> assignmentsController.refreshUI();
            case (PageNames.BREAKDOWNS_PAGE) -> breakDownsController.refreshUI();
            case (PageNames.DASHBOARD_PAGE) -> dashboardController.refreshUI();
            case (PageNames.INTERVENTIONS_PAGE) -> interventionController.refreshUI();
            case (PageNames.MATERIALS_SETTINGS_PAGE) -> materialsSettingsController.refreshUI();
        }
        mainWindow.getHomePage().showPage(activeButton.getActionCommand());
    }

    private void initializeActions(){
        SideBar sideBar = mainWindow.getHomePage().getSideBar();
        sideBar.getDashboardButton().addActionListener(buttonsListener);
        sideBar.getMaterialsButton().addActionListener(buttonsListener);
        sideBar.getAssignmentsButton().addActionListener(buttonsListener);
        sideBar.getBreakdownsButton().addActionListener(buttonsListener);
        sideBar.getInterventionsButton().addActionListener(buttonsListener);
        sideBar.getMaterialsSettingsButton().addActionListener(buttonsListener);
    }

}