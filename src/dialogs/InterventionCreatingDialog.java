package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.BreakDownHandler;
import handlers.MaterialHandler;
import handlers.TechnicianHandler;
import models.BreakDown;
import models.Intervention;
import models.Material;
import models.Technician;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class InterventionCreatingDialog extends JDialog {

    private final JTextField technicianLastNameField;
    private final JTextField technicianFirstNamesField;
    private final JTextField dateField;
    private final JComboBox<Material> materialsComboBox;
    private final JComboBox<BreakDown> breakDownComboBox;
    private boolean succeeded = false;


    public InterventionCreatingDialog(JFrame owner){
        super(owner, "Nouvelle intervention", true);
        setLayout(new BorderLayout());
        setSize(520, 600);
        setLocationRelativeTo(owner);

        technicianLastNameField = new JTextField();
        technicianFirstNamesField = new JTextField();
        dateField = new JTextField();

        technicianLastNameField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        technicianLastNameField.putClientProperty("JTextField.placeholderText", "Entrez le nom du technicien...");
        technicianFirstNamesField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        technicianFirstNamesField.putClientProperty("JTextField.placeholderText", "Entrez les prénoms du technicien...");
        dateField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        dateField.putClientProperty("JTextField.placeholderText", "ex : 10-11-2021");
        KeyAdapter dateParser = new KeyAdapter() {
            public void keyTyped(KeyEvent event) {
                char c = event.getKeyChar();
                if (!((c >= '0' && c <= '9') || c == '-' || c == KeyEvent.VK_BACK_SPACE)) event.consume();
            }
        };
        dateField.addKeyListener(dateParser);

        MaterialHandler materialHandler = new MaterialHandler();
        List<Material> materials = materialHandler.getAllMaterials();
        materialsComboBox = new JComboBox<>();
        materialsComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        Material materialPlaceholder = new Material();
        materialPlaceholder.setId(-1);
        materialPlaceholder.setDesignation("Sélectionnez le matériel réparé ...");
        materialsComboBox.addItem(materialPlaceholder);
        for (Material material : materials) materialsComboBox.addItem(material);
        materialsComboBox.setSelectedIndex(0);

        BreakDownHandler breakDownHandler = new BreakDownHandler();
        List<BreakDown> breakDowns = breakDownHandler.getAllBreakDowns();
        breakDownComboBox = new JComboBox<>();
        breakDownComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        BreakDown breakDownPlaceholder = new BreakDown();
        breakDownPlaceholder.setId(-1);
        breakDownPlaceholder.setDescription("Sélectionnez la panne subvenue ...");
        breakDownComboBox.addItem(breakDownPlaceholder);
        for (BreakDown breakDown : breakDowns) breakDownComboBox.addItem(breakDown);
        breakDownComboBox.setSelectedIndex(0);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        JLabel title = new JLabel("Nouvelle intervention");
        title.setForeground(AppColors.WHITE_COLOR);
        title.setFont(GlobalUtilities.getBoldAppFont(18));
        header.add(title);

        JPanel body = new JPanel(new GridLayout(0, 1, 10, 15));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        body.setBackground(AppColors.WHITE_COLOR);

        JLabel lastName = new JLabel("Nom du technicien");
        lastName.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel firstNames = new JLabel("Prénoms du technicien");
        firstNames.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel material = new JLabel("Materiel pris en charge");
        material.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel breakDown = new JLabel("Panne subie par le matériel");
        breakDown.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel date = new JLabel("Date d'intervention");
        date.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        technicianLastNameField.setPreferredSize(fieldDimension);
        technicianFirstNamesField.setPreferredSize(fieldDimension);
        dateField.setPreferredSize(fieldDimension);
        materialsComboBox.setPreferredSize(fieldDimension);
        breakDownComboBox.setPreferredSize(fieldDimension);

        body.add(lastName);
        body.add(technicianLastNameField);
        body.add(firstNames);
        body.add(technicianFirstNamesField);
        body.add(material);
        body.add(materialsComboBox);
        body.add(breakDown);
        body.add(breakDownComboBox);
        body.add(date);
        body.add(dateField);

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

    public Intervention createIntervention(){

        String lastName = technicianLastNameField.getText().trim();
        String firstNames = technicianFirstNamesField.getText().trim();
        String interventionDate = dateField.getText().trim();
        Material materialRepaired = (Material) materialsComboBox.getSelectedItem();
        int materialID = (materialRepaired == null) ? 1 : materialRepaired.getId();
        BreakDown breakDownHappened = (BreakDown) breakDownComboBox.getSelectedItem();
        int breakDownID = (breakDownHappened == null) ? 1 : breakDownHappened.getId();

        Technician technician = new Technician(lastName , firstNames);
        TechnicianHandler technicianHandler = new TechnicianHandler();
        if (!technicianHandler.saveTechnician(technician)) showError("Une erreur est subvenue lors de l'enregistrement du technicien...");

        return new Intervention(interventionDate , materialID , breakDownID , technician.getId());

    }

    private void saveAction(){
        if (isFieldsValidated()) {
            succeeded = true;
            setVisible(false);
        }
    }

    private boolean isFieldsValidated() {

        if (technicianLastNameField.getText().trim().isBlank()) {
            showError("Veuillez renseigner le nom du technicien !");
            return false;
        }

        if (technicianFirstNamesField.getText().trim().isBlank()) {
            showError("Veuillez renseigner les prénoms du technicien !");
            return false;
        }

        if (!isMaterialValidated()) {
            return false;
        }

        if (!isBreakDownValidated()) {
            return false;
        }

        String enteredDate = dateField.getText().trim();
        if (enteredDate.isBlank() || !isDateValidated(enteredDate)) {
            if (enteredDate.isBlank()) showError("La date d'intervention est obligatoire !");
            return false;
        }

        return true;

    }

    private boolean isMaterialValidated(){
        if (materialsComboBox.getSelectedIndex() == 0) {
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez renseigner le matériel réparé !");
            return false;
        }
        Material materialSelected = (Material) materialsComboBox.getSelectedItem();
        if (materialSelected == null){
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Le matériel sélectionné n'existe pas...");
            return false;
        }
        if (materialSelected.isFunctional()){
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Le matériel sélectionné n'était pas endommagé...");
            return false;
        }
        materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isBreakDownValidated(){
        if (breakDownComboBox.getSelectedIndex() == 0) {
            breakDownComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez renseigner la panne subvenue !");
            return false;
        }
        BreakDown breakDownSelected = (BreakDown) breakDownComboBox.getSelectedItem();
        if (breakDownSelected == null){
            breakDownComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("La panne sélectionnée n'existe pas...");
            return false;
        }
        breakDownComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isDateValidated(String enteredDate) {
        if (!GlobalUtilities.isValidDateFormat(enteredDate)) {
            dateField.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez renseigner une date correcte au format jj-MM-AAAA !");
            dateField.requestFocusInWindow();
            return false;
        }
        dateField.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Donnée de champ incorrecte", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSucceeded() { return succeeded; }

}