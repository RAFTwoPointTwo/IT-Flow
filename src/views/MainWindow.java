package views;

import database.DatabaseStatus;
import utilities.PageNames;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{

    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    private final RegisterPage registerPage = new RegisterPage();
    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    public MainWindow(){

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        addPage(registerPage , PageNames.REGISTER_PAGE);
        addPage(loginPage, PageNames.LOGIN_PAGE);
        addPage(homePage, PageNames.HOME_PAGE);

        if (!DatabaseStatus.isAppAdministrated()){
            showPage(PageNames.REGISTER_PAGE);
            registerPage.setVisible(true);
            SwingUtilities.invokeLater(registerPage::makeButtonFocused);
        }else {
            showPage(PageNames.LOGIN_PAGE);
            loginPage.setVisible(true);
            SwingUtilities.invokeLater(loginPage::makeButtonFocused);
        }

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public HomePage getHomePage(){ return homePage; }

    public RegisterPage getRegisterPage(){ return registerPage; }

    public LoginPage getLoginPage() { return loginPage; }

    private void addPage(JPanel page , String pageName){ mainPanel.add(page , pageName); }

    public void showPage(String pageName){ cardLayout.show(mainPanel , pageName); }

    public void displayWindow(){ setVisible(true); }

}