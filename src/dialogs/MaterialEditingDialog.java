package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.AssignmentHandler;
import handlers.ComputerPackHandler;
import handlers.MaterialTypeHandler;
import models.Assignment;
import models.ComputerPack;
import models.Material;
import models.MaterialType;
import utilities.AppColors;
import utilities.GlobalUtilities;
import utilities.MaterialStates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

public class MaterialEditingDialog extends JDialog {

    Material materialToEdit;
    private final JTextField designationField;
    private final JTextField specificationField;
    private final JTextField brandField;
    private final JTextField serialNumberField;
    private final JTextField dateField;
    private final JComboBox<MaterialType> typesComboBox;
    private final JComboBox<ComputerPack> packsComboBox;
    private final JComboBox<String> statesComboBox;
    private final JComboBox<String> availabilityComboBox;
    private final AssignmentHandler assignmentHandler;
    private boolean succeeded = false;

    public MaterialEditingDialog(JFrame owner , Material material) {
        super(owner, "Modification de Matériel", true);
        setLayout(new BorderLayout());
        setSize(520, 600);
        setLocationRelativeTo(owner);
        this.materialToEdit = material;
        assignmentHandler = new AssignmentHandler();

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
        for (MaterialType type : types) typesComboBox.addItem(type);

        ComputerPackHandler computerPackHandler = new ComputerPackHandler();
        List<ComputerPack> packs = computerPackHandler.getAllComputerPacks();
        packsComboBox = new JComboBox<>();
        packsComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        for (ComputerPack pack : packs) packsComboBox.addItem(pack);

        String[] stateOptions = {MaterialStates.FUNCTIONAL , MaterialStates.DEFECTIVE , MaterialStates.BROKEN_DOWN};
        statesComboBox = new JComboBox<>(stateOptions);
        statesComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");

        String[] availabilityOptions = {"Disponible" , "Indisponible"};
        availabilityComboBox = new JComboBox<>(availabilityOptions);
        availabilityComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");

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

        JLabel state = new JLabel("État du matériel");
        state.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel availability = new JLabel("Disponibilité du matériel");
        availability.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        designationField.setPreferredSize(fieldDimension);
        specificationField.setPreferredSize(fieldDimension);
        brandField.setPreferredSize(fieldDimension);
        serialNumberField.setPreferredSize(fieldDimension);
        dateField.setPreferredSize(fieldDimension);
        typesComboBox.setPreferredSize(fieldDimension);
        packsComboBox.setPreferredSize(fieldDimension);

        designationField.setText(materialToEdit.getDesignation());
        specificationField.setText(materialToEdit.getSpecification());
        brandField.setText(materialToEdit.getBrand());
        serialNumberField.setText(materialToEdit.getSerialNumber());
        dateField.setText(materialToEdit.getAcquisitionDate());
        MaterialType materialType = materialTypeHandler.getMaterialTypeByID(materialToEdit.getTypeID());
        ComputerPack materialPack = computerPackHandler.getComputerPackByID(materialToEdit.getPackID());

        for (int i = 0 ; i < typesComboBox.getItemCount() ; i++){
            if (typesComboBox.getItemAt(i).getId() == materialType.getId()){
                typesComboBox.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0 ; i < packsComboBox.getItemCount() ; i++){
            if (packsComboBox.getItemAt(i).getId() == materialPack.getId()){
                packsComboBox.setSelectedIndex(i);
                break;
            }
        }

        statesComboBox.setSelectedItem(materialToEdit.getState());
        availabilityComboBox.setSelectedItem(materialToEdit.getAvailabilityToString());

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
        body.add(state);
        body.add(statesComboBox);
        body.add(availability);
        body.add(availabilityComboBox);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        Dimension buttonDimension = new Dimension(120 , 35);
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setPreferredSize(buttonDimension);
        cancelButton.setCursor(GlobalUtilities.getHandCursor());
        cancelButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        JButton saveButton = new JButton("Modifier");
        saveButton.setPreferredSize(buttonDimension);
        saveButton.setCursor(GlobalUtilities.getHandCursor());
        saveButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");

        saveButton.setBackground(AppColors.BASE_BLUE_COLOR);
        saveButton.setForeground(AppColors.STRICT_WHITE_COLOR);

        cancelButton.addActionListener(event -> this.dispose());

        saveButton.addActionListener(event -> editAction());

        footer.add(cancelButton);
        footer.add(saveButton);

        JScrollPane bodyWrapper = new JScrollPane(body);
        bodyWrapper.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(bodyWrapper, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void editAction(){
        if (isFieldsValidated()) {
            succeeded = true;
            setVisible(false);
        }
    }

    public Material updateMaterial() {

        materialToEdit.setDesignation(designationField.getText().trim());
        materialToEdit.setSpecification(specificationField.getText().trim());
        materialToEdit.setBrand(brandField.getText().trim());
        materialToEdit.setSerialNumber(serialNumberField.getText().trim());
        materialToEdit.setAcquisitionDate(dateField.getText().trim());

        String selectedState = (String) statesComboBox.getSelectedItem();
        String selectedAvailability = (String) availabilityComboBox.getSelectedItem();
        MaterialType selectedType = (MaterialType) typesComboBox.getSelectedItem();
        ComputerPack selectedPack = (ComputerPack) packsComboBox.getSelectedItem();

        if (selectedType != null) { materialToEdit.setTypeID(selectedType.getId()); }
        else { materialToEdit.setTypeID(1); }

        if (selectedPack != null) { materialToEdit.setPackID(selectedPack.getId()); }
        else { materialToEdit.setPackID(1); }

        if (selectedState != null) materialToEdit.setState(selectedState);

        boolean available = Objects.equals(selectedAvailability , "Disponible");

        materialToEdit.setAvailable(available);

        return materialToEdit;
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

        if (!isStateValidated()){
            return false;
        }

        return isAvailabilityValidated();

    }

    private boolean isStateValidated(){
        String selectedState = (String) statesComboBox.getSelectedItem();
        if (materialToEdit.isBrokenDown() && !Objects.equals(MaterialStates.BROKEN_DOWN , selectedState)) {
            statesComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Ce matériel est en panne ! Vous ne pouvez pas modifier son état. Une intervention est requise.");
            statesComboBox.requestFocusInWindow();
            return false;
        }
        if (!materialToEdit.isBrokenDown() && Objects.equals(MaterialStates.BROKEN_DOWN , selectedState)) {
            statesComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Vous ne pouvez pas rendre ce matériel en panne ici. Une déclaration de panne est requise.");
            statesComboBox.requestFocusInWindow();
            return false;
        }
        statesComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    private boolean isAvailabilityValidated() {
        String selectedAvailability = (String) availabilityComboBox.getSelectedItem();
        List<Assignment> matchingAssignments = assignmentHandler.getAssignmentsByMaterialID(materialToEdit.getId());
        boolean terminated = true;
        for (Assignment assignment : matchingAssignments){
            if (assignment.getEndDate() == null){
                terminated = false;
                break;
            }
        }
        if (!terminated && Objects.equals(selectedAvailability , "Disponible")) {
            availabilityComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Ce matériel est en cours d'affectation ! Il ne peut pas être disponible.");
            availabilityComboBox.requestFocusInWindow();
            return false;
        }
        if (materialToEdit.isBrokenDown() && Objects.equals(selectedAvailability , "Disponible")) {
            availabilityComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Ce matériel est en panne ! Il ne peut pas être disponible.");
            availabilityComboBox.requestFocusInWindow();
            return false;
        }
        availabilityComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
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
