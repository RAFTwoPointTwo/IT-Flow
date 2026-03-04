package app;

import controllers.*;
import views.AppUI;
import views.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppUI.initUI();

            MainWindow mainWindow = new MainWindow();

            new RegisterController(mainWindow);
            new LoginController(mainWindow);

            MaterialsController materialsController = new MaterialsController(mainWindow);
            AssignmentsController assignmentsController = new AssignmentsController(mainWindow);
            BreakDownsController breakDownsController = new BreakDownsController(mainWindow);
            DashboardController dashboardController = new DashboardController(mainWindow);
            InterventionsController interventionsController = new InterventionsController(mainWindow);
            MaterialsSettingsController materialsSettingsController = new MaterialsSettingsController(mainWindow);

            new HomeController(
                    mainWindow,
                    materialsController,
                    assignmentsController,
                    breakDownsController,
                    dashboardController,
                    interventionsController,
                    materialsSettingsController
            );

            mainWindow.displayWindow();

        });
    }
}
