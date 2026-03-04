package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.ComputerPackHandler;
import handlers.MaterialTypeHandler;
import models.ComputerPack;
import models.Material;
import models.MaterialType;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class MaterialCreatingDialog extends JDialog {

    private final JTextField designationField;
    private final JTextField specificationField;
    private final JTextField brandField;
    private final JTextField serialNumberField;
    private final JTextField dateField;
    private final JComboBox<MaterialType> typesComboBox;
    private final JComboBox<ComputerPack> packsComboBox;
    private boolean succeeded = false;

    public MaterialCreatingDialog(JFrame owner) {
        super(owner, "Nouveau Matériel", true);
        setLayout(new BorderLayout());
        setSize(520, 600);
        setLocationRelativeTo(owner);

        designationField = new JTextField();
        designationField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        designationField.putClientProperty("JTextField.placeholderText", "ex : ASUS Zenbook 14 OLED");

        specificationField = new JTextField();
        specificationField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        specificationField.putClientProperty("JTextField.placeholderText", "ex : 8000 DPI, Bluetooth, Silent Click");

        brandField = new JTextField();
        brandField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        brandField.putClientProperty("JTextField.placeholderText", "ex : HP");

        serialNumberField = new JTextField();
        serialNumberField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        serialNumberField.putClientProperty("JTextField.placeholderText", "ex : SN-HP-445566");

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

        MaterialTypeHandler materialTypeHandler = new MaterialTypeHandler();
        List<MaterialType> types = materialTypeHandler.getAllMaterialTypes();
        typesComboBox = new JComboBox<>();
        typesComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        MaterialType typePlaceholder = new MaterialType();
        typePlaceholder.setId(-1);
        typePlaceholder.setName("Sélectionnez un type ...");
        typesComboBox.addItem(typePlaceholder);
        for (MaterialType type : types) typesComboBox.addItem(type);
        typesComboBox.setSelectedIndex(0);

        ComputerPackHandler computerPackHandler = new ComputerPackHandler();
        List<ComputerPack> packs = computerPackHandler.getAllComputerPacks();
        packsComboBox = new JComboBox<>();
        packsComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        ComputerPack packPlaceholder = new ComputerPack();
        packPlaceholder.setId(-1);
        packPlaceholder.setName("Sélectionnez un pack ...");
        packsComboBox.addItem(packPlaceholder);
        for (ComputerPack pack : packs) packsComboBox.addItem(pack);
        packsComboBox.setSelectedIndex(0);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        JLabel title = new JLabel("Ajout de matériel");
        title.setForeground(AppColors.WHITE_COLOR);
        title.setFont(GlobalUtilities.getBoldAppFont(18));
        header.add(title);

        JPanel body = new JPanel(new GridLayout(0, 1, 10, 15));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        body.setBackground(AppColors.WHITE_COLOR);

        JLabel des = new JLabel("Désignation");
        des.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel spec = new JLabel("Spécification");
        spec.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel brand = new JLabel("Marque");
        brand.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel serial = new JLabel("Numéro de série");
        serial.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel date = new JLabel("Date d'acquisition");
        date.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel type = new JLabel("Type du matériel");
        type.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel pack = new JLabel("Pack du matériel");
        pack.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        designationField.setPreferredSize(fieldDimension);
        specificationField.setPreferredSize(fieldDimension);
        brandField.setPreferredSize(fieldDimension);
        serialNumberField.setPreferredSize(fieldDimension);
        dateField.setPreferredSize(fieldDimension);
        typesComboBox.setPreferredSize(fieldDimension);
        packsComboBox.setPreferredSize(fieldDimension);

        body.add(des);
        body.add(designationField);
        body.add(spec);
        body.add(specificationField);
        body.add(brand);
        body.add(brandField);
        body.add(serial);
        body.add(serialNumberField);
        body.add(date);
        body.add(dateField);
        body.add(type);
        body.add(typesComboBox);
        body.add(pack);
        body.add(packsComboBox);

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

    public Material createMaterial() {

        Material material = new Material();

        material.setDesignation(designationField.getText().trim());
        material.setSpecification(specificationField.getText().trim());
        material.setBrand(brandField.getText().trim());
        material.setSerialNumber(serialNumberField.getText().trim());
        material.setAcquisitionDate(dateField.getText().trim());

        MaterialType selectedType = (MaterialType) typesComboBox.getSelectedItem();
        ComputerPack selectedPack = (ComputerPack) packsComboBox.getSelectedItem();

        if (selectedType != null) { material.setTypeID(selectedType.getId()); }
        else { material.setTypeID(1); }

        if (selectedPack != null) { material.setPackID(selectedPack.getId()); }
        else { material.setPackID(1); }

        material.setToAvailableState();
        material.setToFunctionalState();

        return material;
    }

    private boolean isFieldsValidated() {

        if (designationField.getText().trim().isBlank()) {
            showError("La désignation est obligatoire !");
            return false;
        }

        if (specificationField.getText().trim().isBlank()) {
            showError("La spécification est obligatoire !");
            return false;
        }

        if (brandField.getText().trim().isBlank()) {
            showError("La marque est obligatoire !");
            return false;
        }

        if (serialNumberField.getText().trim().isBlank()) {
            showError("Le numéro de série est obligatoire !");
            return false;
        }

        String enteredDate = dateField.getText().trim();
        if (enteredDate.isBlank() || !isDateValidated(enteredDate)) {
            if (enteredDate.isBlank()) showError("La date d'acquisition est obligatoire !");
            return false;
        }

        if (!isTypeValidated()) {
            showError("Veuillez choisir un type pour ce matériel !");
            return false;
        }

        if (!isPackValidated()) {
            showError("Veuillez choisir un pack pour ce matériel !");
            return false;
        }

        return true;

    }

    private boolean isTypeValidated(){
        if (typesComboBox.getSelectedIndex() == 0) {
            typesComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            return false;
        }
        typesComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isPackValidated(){
        if (packsComboBox.getSelectedIndex() == 0) {
            packsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            return false;
        }
        packsComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isDateValidated(String enteredDate) {
        if (!isValidDateFormat(enteredDate)) {
            dateField.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez renseigner une date correcte au format jj-MM-AAAA !");
            dateField.requestFocusInWindow();
            return false;
        }
        dateField.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isValidDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) { return false; }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Donnée de champ incorrecte", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSucceeded() { return succeeded; }

}
