package views;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.MaterialHandler;
import handlers.PersonHandler;
import models.Assignment;
import models.Material;
import utilities.AppColors;
import utilities.GlobalUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AssignmentsPage extends JPanel {

    private final JPanel bottomPanel;
    private final JTextField searchField;
    private final JPanel assignmentsPanel;
    private final JButton addButton;
    private final PersonHandler personHandler;
    private final MaterialHandler materialHandler;
    private final List<JButton> updateButtons;

    public AssignmentsPage(){
        setPreferredSize(GlobalUtilities.getCommonHomePageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        setBackground(AppColors.WHITE_COLOR);

        personHandler = new PersonHandler();
        materialHandler = new MaterialHandler();

        updateButtons = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout(0 , 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        mainPanel.setOpaque(false);

        assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BorderLayout());
        assignmentsPanel.setOpaque(true);
        assignmentsPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
        assignmentsPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.STRICT_WHITE_COLOR , 1 , 20));


        addButton = new JButton("+ Nouvelle affectation");
        addButton.setFont(GlobalUtilities.getBoldAppFont(13f));
        addButton.setForeground(AppColors.WHITE_COLOR);
        addButton.setBackground(AppColors.BASE_BLUE_COLOR);
        Dimension buttonDimension = new Dimension(190 , 40);
        addButton.setPreferredSize(buttonDimension);
        addButton.setMinimumSize(buttonDimension);
        addButton.setCursor(GlobalUtilities.getHandCursor());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher une affectaion ...");
        Dimension fieldDimension = new Dimension(500 , 5);
        searchField.setPreferredSize(fieldDimension);
        searchField.setMargin(new Insets(1 , 10 , 1 , 0));
        searchField.setFont(GlobalUtilities.getMediumAppFont());
        headerPanel.add(searchField, BorderLayout.WEST);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionsPanel.setOpaque(false);
        actionsPanel.add(addButton);
        headerPanel.add(actionsPanel, BorderLayout.EAST);
        headerPanel.add(Box.createVerticalStrut(12) , BorderLayout.SOUTH);

        assignmentsPanel.add(headerPanel , BorderLayout.NORTH);

        JPanel topPanel = createTitlePanel();
        bottomPanel = createBottomPanel();

        mainPanel.add(topPanel , BorderLayout.NORTH);
        mainPanel.add(assignmentsPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel , BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(mainPanel, gbc);
    }

    public void updateData(List<Assignment> assignments) {
        JPanel assignmentsContainer = new JPanel();
        assignmentsContainer.setLayout(new BoxLayout(assignmentsContainer, BoxLayout.Y_AXIS));
        assignmentsContainer.setOpaque(false);
        assignmentsContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        updateButtons.clear();

        Component centerComponent = ((BorderLayout) assignmentsPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) assignmentsPanel.remove(centerComponent);

        if (assignments.isEmpty()){
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            JLabel emptyLabel = new JLabel("Aucune affectation enregistrée...");
            emptyLabel.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
            emptyLabel.setFont(GlobalUtilities.getBoldAppFont(20f));
            emptyPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
            emptyPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
            emptyPanel.add(emptyLabel);

            assignmentsPanel.add(emptyPanel, BorderLayout.CENTER);

        }else {

            for (Assignment assignment : assignments) {
                assignmentsContainer.add(createAssignmentCard(assignment));
                assignmentsContainer.add(Box.createVerticalStrut(12));
            }

            JScrollPane scrollPane = new JScrollPane(assignmentsContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            assignmentsPanel.add(scrollPane, BorderLayout.CENTER);
        }

        assignmentsPanel.revalidate();
        assignmentsPanel.repaint();
    }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout(10 , 0));
        titlePanel.setOpaque(true);
        titlePanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));
        titlePanel.setBackground(AppColors.DARK_BLUE_COLOR);

        JLabel title = new JLabel("Affectations");
        title.setFont(GlobalUtilities.getBoldAppFont(28f));
        title.setForeground(AppColors.STRICT_WHITE_COLOR);

        JLabel subTitle = new JLabel("Gérez les affectations de vos matériels");
        subTitle.setFont(GlobalUtilities.getBoldSmallAppFont());
        subTitle.setForeground(AppColors.WHITE_COLOR);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.add(title , BorderLayout.NORTH);
        titleWrapper.add(subTitle , BorderLayout.SOUTH);

        titlePanel.add(titleWrapper , BorderLayout.WEST);

        return titlePanel;
    }

    private JPanel createBottomPanel(){

        JPanel bottomPagePanel = new JPanel(new GridLayout(1 , 3 , 20 , 0));
        bottomPagePanel.setOpaque(false);
        bottomPagePanel.setBorder(BorderFactory.createEmptyBorder(5 , 20 , 5 , 20));

        return bottomPagePanel;

    }

    public JPanel createBottomCard(String title, int value, double percent , Color footerColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.STRICT_WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE , "arc: 20");

        JLabel titleCardLabel = new JLabel(title);
        titleCardLabel.setForeground(AppColors.GRAY_COLOR);
        titleCardLabel.setFont(GlobalUtilities.getBoldSmallAppFont());

        JLabel valueCardLabel = new JLabel(String.valueOf(value));
        valueCardLabel.setFont(GlobalUtilities.getBoldLargeAppFont());

        JLabel footerCardLabel = new JLabel(String.format("%.2f" , percent) + "%");
        footerCardLabel.setFont(GlobalUtilities.getBoldSmallAppFont());
        footerCardLabel.setForeground(footerColor);

        card.add(titleCardLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueCardLabel);
        card.add(Box.createVerticalGlue());
        card.add(footerCardLabel);

        return card;
    }

    private JPanel createBadge(String text) {
        JLabel label = new JLabel(text);
        label.setFont(GlobalUtilities.getBoldSmallAppFont());

        JPanel badge = new JPanel(new GridBagLayout());
        badge.setBorder(new EmptyBorder(5, 8, 5, 8));
        badge.putClientProperty(FlatClientProperties.STYLE, "arc: 12");

        if (text.equalsIgnoreCase("Terminée")) {
            badge.setBackground(AppColors.LIGHT_GREEN_COLOR);
            label.setForeground(AppColors.GREEN_COLOR);
        }
        else if (text.equalsIgnoreCase("En cours")){
            badge.setBackground(AppColors.LIGHT_YELLOW_COLOR);
            label.setForeground(AppColors.YELLOW_COLOR);
        }
        else{
            badge.setBackground(AppColors.SEMI_LIGHT_GRAY_COLOR);
            label.setForeground(AppColors.GRAY_COLOR);
        }

        badge.add(label);

        return badge;
    }

    private JPanel createAssignmentCard(Assignment assignment) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setBackground(AppColors.WHITE_COLOR);
        card.putClientProperty(FlatClientProperties.STYLE, "arc: 20");
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { card.setBackground(AppColors.LIGHT_GRAY_COLOR); }

            @Override
            public void mouseExited(MouseEvent e) { card.setBackground(AppColors.WHITE_COLOR); }
        });

        JPanel infosPanel = new JPanel();
        infosPanel.setLayout(new BoxLayout(infosPanel, BoxLayout.Y_AXIS));
        infosPanel.setOpaque(false);

        JLabel personLabel = new JLabel("Personne : ");
        personLabel.setFont(GlobalUtilities.getBoldAppFont(14f));
        JPanel person = createBadge(personHandler.getPersonByID(assignment.getPersonID()).getFullName());
        JPanel personPanel = new JPanel(new GridBagLayout());
        personPanel.setOpaque(false);
        personPanel.add(person);

        JLabel materialLabel = new JLabel("Matériel affecté : ");
        materialLabel.setFont(GlobalUtilities.getBoldAppFont(14f));
        Material assignmentMaterial = materialHandler.getMaterialByID(assignment.getMaterialID());
        JPanel material = createBadge(assignmentMaterial.getDesignation() + " [ ID : " + assignmentMaterial.getId() + " ]");
        JPanel materialPanel = new JPanel(new GridBagLayout());
        materialPanel.setOpaque(false);
        materialPanel.add(material);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT , 7 , 0);
        JPanel personWrapper = new JPanel(flowLayout);
        personWrapper.add(personLabel);
        personWrapper.add(personPanel);

        JPanel materialWrapper = new JPanel(flowLayout);
        materialWrapper.add(materialLabel);
        materialWrapper.add(materialPanel);

        personWrapper.setOpaque(false);
        materialWrapper.setOpaque(false);

        JLabel startDateLabel = new JLabel("Matériel affecté le " + assignment.getStartDate());
        startDateLabel.setFont(GlobalUtilities.getBoldAppFont(13f));
        startDateLabel.setForeground(AppColors.GRAY_COLOR);

        JLabel endDateLabel = new JLabel("Date de retour : " + (assignment.getEndDate() == null ? " - " : assignment.getEndDate()) );
        endDateLabel.setFont(GlobalUtilities.getBoldAppFont(13f));
        endDateLabel.setForeground(AppColors.GRAY_COLOR);

        infosPanel.add(personWrapper);
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(materialWrapper);
        infosPanel.add(Box.createVerticalStrut(12));
        infosPanel.add(wrapInPanel(startDateLabel , flowLayout));
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(wrapInPanel(endDateLabel , flowLayout));

        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        badgePanel.setOpaque(false);
        badgePanel.add(createBadge(assignment.getEndDate() == null ? "En cours" : "Terminée"));
        JPanel badgeWrapper = new JPanel(new GridBagLayout());
        badgeWrapper.setOpaque(false);
        badgeWrapper.add(badgePanel);

        card.add(infosPanel, BorderLayout.WEST);
        if (assignment.getEndDate() == null) card.add(badgeWrapper, BorderLayout.CENTER);
        else card.add(badgeWrapper , BorderLayout.EAST);

        if (assignment.getEndDate() == null){
            JPanel updateLayout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            updateLayout.setOpaque(false);
            JButton updateButton = new JButton("Terminer");
            updateButton.setCursor(GlobalUtilities.getHandCursor());
            updateButton.setMargin(new Insets(5 , 12 , 5 , 12));
            updateButton.setFont(GlobalUtilities.getBoldAppFont(14f));
            updateButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
            updateButton.setBackground(AppColors.SEMI_LIGHT_BLUE_COLOR);
            updateButton.setForeground(AppColors.STRICT_WHITE_COLOR);
            updateButton.putClientProperty("assignment" , assignment);
            updateButtons.add(updateButton);
            updateLayout.add(updateButton);
            JPanel editWrapper = new JPanel(new GridBagLayout());
            editWrapper.setOpaque(false);
            editWrapper.add(updateLayout);
            card.add(editWrapper, BorderLayout.EAST);
        }

        return card;
    }

    private JPanel wrapInPanel(JLabel label , FlowLayout layout){
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.add(label);
        return panel;
    }

    public int askUpdateConfirmation(){
        return JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous finaliser cette affectation ?",
                "Confirmation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public JButton getAddButton() { return addButton; }

    public JTextField getSearchField() { return searchField; }

    public JPanel getBottomPanel() { return bottomPanel; }

    public List<JButton> getUpdateButtons() { return updateButtons; }
}