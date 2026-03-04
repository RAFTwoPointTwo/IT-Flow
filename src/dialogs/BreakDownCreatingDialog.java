package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.BreakDownHandler;
import handlers.MaterialHandler;
import models.BreakDown;
import models.BreakdownOccurrence;
import models.Material;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class BreakDownCreatingDialog  extends JDialog {
    private final JComboBox<Material> materialsComboBox;
    private final JTextField breakDownDescriptionField;
    private final JTextField dateField;
    private final BreakDownHandler breakDownHandler;
    private boolean succeeded = false;


    public BreakDownCreatingDialog(JFrame owner) {
        super(owner, "Nouvelle panne", true);
        setLayout(new BorderLayout());
        setSize(520, 530);
        setLocationRelativeTo(owner);

        breakDownHandler = new BreakDownHandler();

        MaterialHandler materialHandler = new MaterialHandler();
        List<Material> materials = materialHandler.getAllMaterials();

        materialsComboBox = new JComboBox<>();
        materialsComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        Material materialPlaceholder = new Material();
        materialPlaceholder.setId(-1);
        materialPlaceholder.setDesignation("Sélectionnez un matériel ...");
        materialsComboBox.addItem(materialPlaceholder);
        for (Material material : materials) materialsComboBox.addItem(material);
        materialsComboBox.setSelectedIndex(0);

        breakDownDescriptionField = new JTextField();
        breakDownDescriptionField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        breakDownDescriptionField.putClientProperty("JTextField.placeholderText", "ex : Ecran endommagé");

        dateField = new JTextField();
        dateField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        dateField.putClientProperty("JTextField.placeholderText", "ex : 10-05-2021");
        KeyAdapter dateParser = new KeyAdapter() {
            public void keyTyped(KeyEvent event) {
                char c = event.getKeyChar();
                if (!((c >= '0' && c <= '9') || c == '-' || c == KeyEvent.VK_BACK_SPACE)) event.consume();
            }
        };
        dateField.addKeyListener(dateParser);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        JLabel title = new JLabel("Déclaration de panne");
        title.setForeground(AppColors.WHITE_COLOR);
        title.setFont(GlobalUtilities.getBoldAppFont(18));
        header.add(title);

        JPanel body = new JPanel(new GridLayout(0, 1, 10, 15));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        body.setBackground(AppColors.WHITE_COLOR);

        JLabel mat = new JLabel("Matériel endommagé");
        mat.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel desc = new JLabel("Description de la panne");
        desc.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel date = new JLabel("Date de la panne");
        date.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        materialsComboBox.setPreferredSize(fieldDimension);
        breakDownDescriptionField.setPreferredSize(fieldDimension);
        dateField.setPreferredSize(fieldDimension);

        body.add(mat);
        body.add(materialsComboBox);
        body.add(desc);
        body.add(breakDownDescriptionField);
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

    private void saveAction(){
        if (isFieldsValidated()) {
            succeeded = true;
            setVisible(false);
        }
    }

    public BreakdownOccurrence createBreakDown() {

        BreakDown breakDown = new BreakDown();
        breakDown.setDescription(breakDownDescriptionField.getText().trim());
        if (!breakDownHandler.saveBreakDown(breakDown)) showError("Une erreur est subvenue lors de l'enregistrement de la panne...");
        int breakDownID = breakDown.getId();

        Material selectedMaterial = (Material) materialsComboBox.getSelectedItem();
        int materialID = (selectedMaterial != null) ? selectedMaterial.getId() : 1;

        String brokDate = dateField.getText().trim();

        return new BreakdownOccurrence(materialID , breakDownID , brokDate);
    }

    private boolean isFieldsValidated() {
        if (!isMaterialValidated()) {
            return false;
        }

        if (breakDownDescriptionField.getText().trim().isBlank()) {
            showError("La description de la panne est obligatoire !");
            return false;
        }

        String enteredDate = dateField.getText().trim();
        if (enteredDate.isBlank() || !isDateValidated(enteredDate)) {
            if (enteredDate.isBlank()) showError("La date de panne est obligatoire !");
            return false;
        }

        return true;
    }

    private boolean isMaterialValidated() {
        if (materialsComboBox.getSelectedIndex() == 0) {
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez choisir le matériel tombé en panne !");
            return false;
        }
        Material materialSelected = (Material) materialsComboBox.getSelectedItem();
        if (materialSelected == null){
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Le matériel sélectionné n'existe pas...");
            return false;
        }
        if (materialSelected.isBrokenDown()){
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Le matériel sélectionné est déjà en panne...");
            return false;
        }
        materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
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
