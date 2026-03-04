package dialogs;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.MaterialHandler;
import handlers.PersonHandler;
import handlers.ServiceHandler;
import models.*;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AssignmentCreatingDialog extends JDialog {

    private final JTextField personLastNameField;
    private final JTextField personFirstNamesField;
    private final JTextField personFunctionField;
    private final JTextField serviceNameField;
    private final JTextField serviceDescriptionField;
    private final JComboBox<Material> materialsComboBox;
    private boolean succeeded = false;


    public AssignmentCreatingDialog(JFrame owner){
        super(owner, "Nouvelle affectation", true);
        setLayout(new BorderLayout());
        setSize(520, 600);
        setLocationRelativeTo(owner);

        personLastNameField = new JTextField();
        personFirstNamesField = new JTextField();
        personFunctionField = new JTextField();
        serviceNameField = new JTextField();
        serviceDescriptionField = new JTextField();

        personLastNameField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        personLastNameField.putClientProperty("JTextField.placeholderText", "Entrez le nom de l'attributaire...");
        personFirstNamesField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        personFirstNamesField.putClientProperty("JTextField.placeholderText", "Entrez les prénoms de l'attributaire...");
        personFunctionField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        personFunctionField.putClientProperty("JTextField.placeholderText", "Entrez la fonction de l'attributaire...");
        serviceNameField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        serviceNameField.putClientProperty("JTextField.placeholderText", "Entrez le nom du Service de l'attributaire...");
        serviceDescriptionField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        serviceDescriptionField.putClientProperty("JTextField.placeholderText", "Donnez une description du service...");

        MaterialHandler materialHandler = new MaterialHandler();
        List<Material> materials = materialHandler.getAvailableMaterials();
        materialsComboBox = new JComboBox<>();
        materialsComboBox.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        if (!materials.isEmpty()){
            Material materialPlaceholder = new Material();
            materialPlaceholder.setId(-1);
            materialPlaceholder.setDesignation("Sélectionnez un matériel ...");
            materialsComboBox.addItem(materialPlaceholder);
            for (Material material : materials) materialsComboBox.addItem(material);
            materialsComboBox.setSelectedIndex(0);
        }else {
            Material materialPlaceholder = new Material();
            materialPlaceholder.setId(-1);
            materialPlaceholder.setDesignation("Aucun matériel disponible pour une affectation...");
            materialsComboBox.addItem(materialPlaceholder);
            materialsComboBox.setSelectedIndex(0);
        }
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        header.setBackground(AppColors.SEMI_DARK_BLUE_COLOR);
        JLabel title = new JLabel("Nouvelle affectation");
        title.setForeground(AppColors.WHITE_COLOR);
        title.setFont(GlobalUtilities.getBoldAppFont(18));
        header.add(title);

        JPanel body = new JPanel(new GridLayout(0, 1, 10, 15));
        body.setBorder(new EmptyBorder(20, 20, 20, 20));
        body.setBackground(AppColors.WHITE_COLOR);

        JLabel lastName = new JLabel("Nom de l'attributaire");
        lastName.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel firstNames = new JLabel("Prénoms de l'attributaire");
        firstNames.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel function = new JLabel("Fonction de l'attributaire (Dans le service)");
        function.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel serviceName = new JLabel("Nom de son service");
        serviceName.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel serviceDescription = new JLabel("Description du service (Facultative)");
        serviceDescription.setFont(GlobalUtilities.getBoldAppFont(15f));

        JLabel materialAssign = new JLabel("Matériel à affecter");
        materialAssign.setFont(GlobalUtilities.getBoldAppFont(15f));

        Dimension fieldDimension = new Dimension(10 , 40);

        personLastNameField.setPreferredSize(fieldDimension);
        personFirstNamesField.setPreferredSize(fieldDimension);
        serviceNameField.setPreferredSize(fieldDimension);
        serviceDescriptionField.setPreferredSize(fieldDimension);
        materialsComboBox.setPreferredSize(fieldDimension);

        body.add(lastName);
        body.add(personLastNameField);
        body.add(firstNames);
        body.add(personFirstNamesField);
        body.add(serviceName);
        body.add(serviceNameField);
        body.add(function);
        body.add(personFunctionField);
        body.add(serviceDescription);
        body.add(serviceDescriptionField);
        body.add(materialAssign);
        body.add(materialsComboBox);

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

    public Assignment createAssignment(){

        String lastName = personLastNameField.getText().trim();
        String firstNames = personFirstNamesField.getText().trim();
        String function = personFunctionField.getText().trim();
        String serviceName = serviceNameField.getText().trim();
        String serviceDescription = (serviceDescriptionField.getText().trim().isBlank()) ? " - " : serviceDescriptionField.getText().trim();
        Material materialToAssign = (Material) materialsComboBox.getSelectedItem();
        int materialID = (materialToAssign == null) ? 1 : materialToAssign.getId();

        Service service = new Service(serviceName , serviceDescription);
        ServiceHandler serviceHandler = new ServiceHandler();
        if (!serviceHandler.saveService(service)) showError("Une erreur est subvenue lors de l'enregistrement du service de l'attributaire...");

        PersonHandler personHandler = new PersonHandler();
        Person person = new Person(lastName , firstNames , function , service.getId());
        if (!personHandler.savePerson(person)) showError("Une erreur est subvenue lors de l'enregistrement des données de l'attributaire...");

        return new Assignment(materialID , person.getId());

    }

    private void saveAction(){
        if (isFieldsValidated()) {
            succeeded = true;
            setVisible(false);
        }
    }

    private boolean isFieldsValidated() {

        if (personLastNameField.getText().trim().isBlank()) {
            showError("Veuillez renseigner le nom de l'attributaire !");
            return false;
        }

        if (personFirstNamesField.getText().trim().isBlank()) {
            showError("Veuillez renseigner les prénoms de l'attributaire !");
            return false;
        }

        if (serviceNameField.getText().trim().isBlank()) {
            showError("Veuillez renseigner le nom du service !");
            return false;
        }

        if (personFunctionField.getText().trim().isBlank()) {
            showError("Veuillez renseigner la fonction de l'attributaire !");
            return false;
        }

        return isMaterialValidated();

    }

    private boolean isMaterialValidated(){
        if (materialsComboBox.getSelectedIndex() == 0) {
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Veuillez choisir un matériel à affecter !");
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
            showError("Le matériel sélectionné est en panne... Impossible de l'affecter.");
            return false;
        }
        if (!materialSelected.isAvailable()){
            materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, "error");
            showError("Le matériel sélectionné n'est pas disponible...");
            return false;
        }
        if (materialSelected.isDefective()){
            int response = askDefectiveAssignmentConfirmation();
            if (response == JOptionPane.OK_OPTION){
                materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
                return true;
            }else { return false; }
        }
        materialsComboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
        return true;
    }

    public int askDefectiveAssignmentConfirmation(){
        return JOptionPane.showConfirmDialog(
                this,
                "Le matériel sélectionné est défectueux. Voulez-vous quand même l'affecter ?",
                "Confirmation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Donnée de champ incorrecte", JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSucceeded() { return succeeded; }

}