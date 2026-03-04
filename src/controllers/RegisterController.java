package controllers;

import database.DatabaseStatus;
import handlers.IdentifiersHandler;
import utilities.GlobalUtilities;
import utilities.PageNames;
import views.MainWindow;
import views.RegisterPage;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterController {

    private final MainWindow mainWindow;
    private final IdentifiersHandler identifiersHandler;

    public RegisterController(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        identifiersHandler = new IdentifiersHandler();
        initializeSubmitAction();
    }

    private void initializeSubmitAction(){
        mainWindow.getRegisterPage().getSendButton().addActionListener(this::adminRegister);
    }

    private void adminRegister(ActionEvent event){
        String login = mainWindow.getRegisterPage().getLoginField().getText();
        char[] password = mainWindow.getRegisterPage().getPasswordField().getPassword();
        if (!isIdentifiersValidated(login.trim() , String.valueOf(password))) return;
        List<String> identifiers = new ArrayList<>();
        identifiers.add(login);
        identifiers.add(GlobalUtilities.passwordHasher(String.valueOf(password)));
        if (identifiersHandler.saveIdentifiers(identifiers)){
            DatabaseStatus.makeAppAdministrated();
            mainWindow.getHomePage().getSideBar().insertAdminName(login);
            mainWindow.showPage(PageNames.HOME_PAGE);
        }else { System.err.println("!! Un problème est subvenu lors de l'insertion des identifiants !!"); }
        Arrays.fill(password, '\0');
    }

    private boolean isIdentifiersValidated(String login , String password){

        RegisterPage registerPage = mainWindow.getRegisterPage();

        if (login.isBlank()){
            registerPage.showErrorMessage("Veuillez renseigner votre login !" , "Champ du login invalide");
            return false;
        }

        if (password.isBlank()){
            registerPage.showErrorMessage("Veuillez renseigner votre mot de passe !" , "Champ du mot de passe invalide");
            return false;
        }

        if (password.length() < 8){
            registerPage.showErrorMessage("Votre mot de passe doit contenir au moins 8 caractères !" , "Mot de passe trop court");
            return false;
        }

        return true;
    }

}
