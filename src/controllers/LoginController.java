package controllers;

import handlers.IdentifiersHandler;
import utilities.GlobalUtilities;
import utilities.PageNames;
import views.LoginPage;
import views.MainWindow;

import java.awt.event.ActionEvent;
import java.util.List;

public class LoginController {

    private final MainWindow mainWindow;
    private final IdentifiersHandler identifiersHandler;

    public LoginController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        identifiersHandler = new IdentifiersHandler();
        initializeSubmitAction();
    }

    private void initializeSubmitAction(){
        mainWindow.getLoginPage().getSubmitButton().addActionListener(this::submitEnteredIdentifiers);
    }

    private void submitEnteredIdentifiers(ActionEvent event){
        String login = mainWindow.getLoginPage().getLoginField().getText();
        char[] password = mainWindow.getLoginPage().getPasswordField().getPassword();
        if (!isIdentifiersValidated(login.trim() , String.valueOf(password))) return;
        mainWindow.getHomePage().getSideBar().insertAdminName(login);
        mainWindow.showPage(PageNames.HOME_PAGE);
    }

    private boolean isIdentifiersValidated(String login , String password){

        LoginPage loginPage = mainWindow.getLoginPage();

        if (login.isBlank()){
            loginPage.showErrorMessage("Veuillez saisir le login de connexion !" , "Champ du login invalide");
            loginPage.clearAllFields();
            return false;
        }

        if (password.isBlank()){
            loginPage.showErrorMessage("Veuillez saisir le mot de passe !" , "Champ du mot de passe invalide");
            loginPage.clearAllFields();
            return false;
        }

        List<String> identifiersInBase = identifiersHandler.getAdminIdentifiers();
        String loginInBase = identifiersInBase.get(0);
        String passwordInBase = identifiersInBase.get(1);

        if (!login.equals(loginInBase) || !GlobalUtilities.checkPasswordWithHashed(password , passwordInBase)){
            loginPage.showErrorMessage("Identifiants invalides ! Veuillez réessayer !" , "Identifiants invalides");
            loginPage.clearAllFields();
            return false;
        }

        return true;

    }

}