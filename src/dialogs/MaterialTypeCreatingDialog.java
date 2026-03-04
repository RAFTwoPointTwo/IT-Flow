package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import models.*;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MaterialTypeCreatingDialog extends JDialog {

    private final JTextField typeNameField;
    private boolean succeeded = false;

    public MaterialTypeCreatingDialog(JFrame owner){
        super(owner, "Nouveau type", true);
        setLayout(new BorderLayout());
        setSize(520, 300);
        setLocationRelativeTo(owner);

        typeNameField = new JTextField();

        typeNameField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        typeNameField.putClientProperty("JTextField.placeholderText", "Entrez le type de matériel...");

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        JLabel title = new JLabel("Nouveau type");
        title.setForeground(AppColors.WHITE_COLOR);
        title.setFont(GlobalUtilities.getBoldAppFont(18));
        header.add(title);

        JPanel body = new JPanel(new GridLayout(0, 1, 10, 5));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        body.setBackground(AppColors.WHITE_COLOR);

        JLabel label = new JLabel("Nom du type de matériel");
        label.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        typeNameField.setPreferredSize(fieldDimension);

        body.add(label);
        body.add(typeNameField);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        Dimension buttonDimension = new Dimension(120 , 35);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setPreferredSize(buttonDimension);
        cancelButton.setCursor(GlobalUtilities.getHandCursor());
        cancelButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setPreferredSize(buttonDimension);
        saveButton.setCursor(GlobalUtilities.getHandCursor());
        saveButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");

        saveButton.setBackground(AppColors.BASE_BLUE_COLOR);
        saveButton.setForeground(AppColors.STRICT_WHITE_COLOR);

        cancelButton.addActionListener(event -> this.dispose());

        saveButton.addActionListener(event -> saveAction());

        footer.add(cancelButton);
        footer.add(saveButton);

        JScrollPane bodyWrapper = new JScrollPane(body);
        bodyWrapper.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(bodyWrapper, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public MaterialType createMaterialtype(){
        String name = typeNameField.getText().trim();
        return new MaterialType(name);
    }

    private void saveAction(){
        if (isFieldsValidated()) {
            succeeded = true;
            setVisible(false);
        }
    }

    private boolean isFieldsValidated() {
        if (typeNameField.getText().trim().isBlank()){
            showErrorMessage();
            return false;
        }
        return true;
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(this, "Veuillez renseigner le type de matériel que vous souhaitez ajouter !", "Donnée de champ incorrecte", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSucceeded() { return succeeded; }

}
