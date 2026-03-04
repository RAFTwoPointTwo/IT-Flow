package views;

import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {
    private final JLabel formLabel;
    private final JButton submitButton;
    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginPage() {
        setPreferredSize(GlobalUtilities.getCommonPageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(80 , 10 , 80 , 10));
        setBackground(AppColors.WHITE_COLOR);

        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setBorder(GlobalUtilities.getCommonLightBorderRadius(AppColors.LIGHT_BLUE_COLOR));
        mainContent.setBackground(AppColors.STRICT_WHITE_COLOR);
        Dimension size = new Dimension(600, 520);
        mainContent.setPreferredSize(size);
        mainContent.setMinimumSize(size);

        formLabel = new JLabel("Connexion");
        submitButton = new JButton("Se connecter");
        JPanel wrappedTitle = createTitlePanel();
        JPanel loginForm = createLoginForm();
        JPanel wrappedButton = createButtonPanel();

        wrappedTitle.setOpaque(false);
        loginForm.setOpaque(false);
        wrappedButton.setOpaque(false);

        mainContent.add(wrappedTitle , BorderLayout.NORTH);
        mainContent.add(loginForm, BorderLayout.CENTER);
        mainContent.add(wrappedButton , BorderLayout.SOUTH);

        GridBagConstraints formConstraints = new GridBagConstraints();
        formConstraints.gridx = 0;
        formConstraints.gridy = 0;
        formConstraints.weightx = 1.0;
        formConstraints.weighty = 1.0;
        formConstraints.fill = GridBagConstraints.NONE;
        formConstraints.anchor = GridBagConstraints.CENTER;

        add(mainContent, formConstraints);
    }

    public JButton getSubmitButton() { return submitButton; }

    public JTextField getLoginField() { return loginField; }

    public JPasswordField getPasswordField() { return passwordField; }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new FlowLayout());
        formLabel.setFont(GlobalUtilities.getBoldLargeAppFont());
        formLabel.setForeground(AppColors.BASE_BLUE_COLOR);
        titlePanel.add(formLabel);
        return titlePanel;
    }

    private JPanel createLoginForm(){

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 20, 30, 20);

        Font labelFont = GlobalUtilities.getBoldAppFont(20f);
        Color labelColor = AppColors.MEDIUM_BASE_BLUE_COLOR;
        JLabel loginLabel = new JLabel("Login");
        JLabel passwordLabel = new JLabel("Mot de passe");
        loginLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        loginLabel.setForeground(labelColor);
        passwordLabel.setForeground(labelColor);
        loginField = new JTextField();
        passwordField = new JPasswordField();
        loginField.setColumns(18);
        passwordField.setColumns(18);
        passwordField.setEchoChar('•');

        setUpFieldsDimension();

        String loginPlaceholder = "Entrez votre login ...";
        String passwordPlaceholder = "••••••••••";
        loginField.putClientProperty("JTextField.placeholderText", loginPlaceholder);
        passwordField.putClientProperty("JTextField.placeholderText", passwordPlaceholder);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(loginLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(passwordField, gbc);

        return formPanel;

    }

    private void setUpFieldsDimension(){
        Dimension fieldDimension = new Dimension(300 , 40);
        loginField.setPreferredSize(fieldDimension);
        loginField.setMinimumSize(fieldDimension);
        passwordField.setPreferredSize(fieldDimension);
        passwordField.setMinimumSize(fieldDimension);
    }
    private JPanel createButtonPanel(){
        JPanel buttonPanel = new JPanel(new FlowLayout());
        submitButton.setFont(GlobalUtilities.getBoldAppFont(18));
        submitButton.setPreferredSize(new Dimension(250 , 45));
        submitButton.setForeground(AppColors.WHITE_COLOR);
        submitButton.setBackground(AppColors.BASE_BLUE_COLOR);
        submitButton.setCursor(GlobalUtilities.getHandCursor());
        buttonPanel.add(submitButton);
        return buttonPanel;
    }

    public void clearAllFields(){
        loginField.setText("");
        passwordField.setText("");
    }

    public void showErrorMessage(String message , String windowTitle){
        JOptionPane.showMessageDialog(null, message , windowTitle , JOptionPane.ERROR_MESSAGE);
    }

    public void makeButtonFocused(){ submitButton.requestFocusInWindow(); }

}
