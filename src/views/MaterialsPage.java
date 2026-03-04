package views;

import com.formdev.flatlaf.FlatClientProperties;
import handlers.ComputerPackHandler;
import handlers.MaterialTypeHandler;
import models.Material;
import utilities.AppColors;
import utilities.GlobalUtilities;
import utilities.MaterialStates;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MaterialsPage extends JPanel {

    private final JPanel materialsPanel;
    private final JPanel bottomPanel;
    private final JTextField searchField;
    private final JButton addButton;
    private final MaterialTypeHandler materialTypeHandler;
    private final ComputerPackHandler computerPackHandler;
    private final List<JButton> editButtons;


    public MaterialsPage(){
        setPreferredSize(GlobalUtilities.getCommonHomePageDimension());
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        setBackground(AppColors.WHITE_COLOR);

        editButtons = new ArrayList<>();
        materialTypeHandler = new MaterialTypeHandler();
        computerPackHandler = new ComputerPackHandler();

        JPanel mainPanel = new JPanel(new BorderLayout(0 , 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        mainPanel.setOpaque(false);

        materialsPanel = new JPanel();
        materialsPanel.setLayout(new BorderLayout());
        materialsPanel.setOpaque(true);
        materialsPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
        materialsPanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.STRICT_WHITE_COLOR , 1 , 20));


        addButton = new JButton("+ Ajouter un matériel");
        addButton.setFont(GlobalUtilities.getBoldAppFont(14f));
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
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher un materiel ...");
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

        materialsPanel.add(headerPanel , BorderLayout.NORTH);

        JPanel topPanel = createTitlePanel();
        bottomPanel = createBottomPanel();

        mainPanel.add(topPanel , BorderLayout.NORTH);
        mainPanel.add(materialsPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel , BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(mainPanel, gbc);
    }

    public void updateData(List<Material> materials) {
        JPanel materialsContainer = new JPanel();
        materialsContainer.setLayout(new BoxLayout(materialsContainer, BoxLayout.Y_AXIS));
        materialsContainer.setOpaque(false);
        materialsContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

        editButtons.clear();

        Component centerComponent = ((BorderLayout) materialsPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (centerComponent != null) materialsPanel.remove(centerComponent);

        if (materials.isEmpty()){
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            JLabel emptyLabel = new JLabel("Aucun matériel trouvé...");
            emptyLabel.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);
            emptyLabel.setFont(GlobalUtilities.getBoldAppFont(20f));
            emptyPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
            emptyPanel.setBackground(AppColors.STRICT_WHITE_COLOR);
            emptyPanel.add(emptyLabel);

            materialsPanel.add(emptyPanel, BorderLayout.CENTER);
        }else {
            for (Material material : materials) {
                materialsContainer.add(createMaterialCard(material));
                materialsContainer.add(Box.createVerticalStrut(12));
            }

            JScrollPane scrollPane = new JScrollPane(materialsContainer);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            materialsPanel.add(scrollPane, BorderLayout.CENTER);
        }

        materialsPanel.revalidate();
        materialsPanel.repaint();
    }

    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel(new BorderLayout(10 , 0));
        titlePanel.setOpaque(true);
        titlePanel.setBorder(GlobalUtilities.getLightBorderRadius(AppColors.SEMI_DARK_BLUE_COLOR , 1 , 20));
        titlePanel.setBackground(AppColors.DARK_BLUE_COLOR);

        JLabel title = new JLabel("Matériaux enregistrés");
        title.setFont(GlobalUtilities.getBoldAppFont(28f));
        title.setForeground(AppColors.STRICT_WHITE_COLOR);

        JLabel subTitle = new JLabel("Gérez vos assets informatiques");
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

        if (text.equalsIgnoreCase(MaterialStates.FUNCTIONAL) || text.equalsIgnoreCase("Disponible")) {
            badge.setBackground(AppColors.LIGHT_GREEN_COLOR);
            label.setForeground(AppColors.GREEN_COLOR);
        }
        else if (text.equalsIgnoreCase(MaterialStates.DEFECTIVE)){
            badge.setBackground(AppColors.LIGHT_YELLOW_COLOR);
            label.setForeground(AppColors.YELLOW_COLOR);
        }
        else if (text.equalsIgnoreCase(MaterialStates.BROKEN_DOWN) || text.equalsIgnoreCase("Indisponible")){
            badge.setBackground(AppColors.LIGHT_RED_COLOR);
            label.setForeground(AppColors.RED_COLOR);
        }
        else{
            badge.setBackground(AppColors.SEMI_LIGHT_GRAY_COLOR);
            label.setForeground(AppColors.GRAY_COLOR);
        }

        badge.add(label);

        return badge;
    }

    private JPanel createMaterialCard(Material material) {
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

        JLabel titleLabel = new JLabel("MAT - " + material.getId() + " • " + material.getDesignation());
        titleLabel.setFont(GlobalUtilities.getBoldMediumAppFont());
        titleLabel.setForeground(AppColors.DARK_BLUE_COLOR);

        JLabel subTitle = new JLabel(material.getBrand() + " | S/N: " + material.getSerialNumber());
        subTitle.setFont(GlobalUtilities.getBoldSmallAppFont());
        subTitle.setForeground(AppColors.GRAY_COLOR);

        JLabel details = new JLabel("Détails : " + material.getSpecification());
        details.setFont(GlobalUtilities.getBoldSmallAppFont());
        details.setForeground(AppColors.SEMI_DARK_BLUE_COLOR);

        JLabel typeLabel = new JLabel("Type : ");
        typeLabel.setFont(GlobalUtilities.getBoldSmallAppFont());
        JPanel type = createBadge(materialTypeHandler.getMaterialTypeByID(material.getTypeID()).getName().toUpperCase());
        JPanel typePanel = new JPanel(new GridBagLayout());
        typePanel.setOpaque(false);
        typePanel.add(type);

        JLabel packLabel = new JLabel("Pack : ");
        packLabel.setFont(GlobalUtilities.getBoldSmallAppFont());
        JPanel pack = createBadge(computerPackHandler.getComputerPackByID(material.getPackID()).getName().toUpperCase());
        JPanel packPanel = new JPanel(new GridBagLayout());
        packPanel.setOpaque(false);
        packPanel.add(pack);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT , 7 , 0);

        JPanel typeWrapper = new JPanel(flowLayout);
        typeWrapper.add(typeLabel);
        typeWrapper.add(typePanel);

        JPanel packWrapper = new JPanel(flowLayout);
        packWrapper.add(packLabel);
        packWrapper.add(packPanel);

        typeWrapper.setOpaque(false);
        packWrapper.setOpaque(false);

        JLabel date = new JLabel("Acquis le " + material.getAcquisitionDate());
        date.setFont(GlobalUtilities.getBoldSmallAppFont());
        date.setForeground(AppColors.GRAY_COLOR);

        infosPanel.add(wrapInPanel(titleLabel , flowLayout));
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(wrapInPanel(subTitle , flowLayout));
        infosPanel.add(Box.createVerticalStrut(10));
        infosPanel.add(wrapInPanel(details , flowLayout));
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(typeWrapper);
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(packWrapper);
        infosPanel.add(Box.createVerticalStrut(5));
        infosPanel.add(wrapInPanel(date , flowLayout));

        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        badgePanel.setOpaque(false);
        badgePanel.add(createBadge(material.getState()));
        badgePanel.add(createBadge(material.getAvailabilityToString()));
        JPanel badgesWrapper = new JPanel(new GridBagLayout());
        badgesWrapper.setOpaque(false);
        badgesWrapper.add(badgePanel);

        JPanel editionLayout = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        editionLayout.setOpaque(false);
        JButton editButton = new JButton("Modifier");
        editButton.setCursor(GlobalUtilities.getHandCursor());
        editButton.setMargin(new Insets(7 , 15 , 7 , 15));
        editButton.setFont(GlobalUtilities.getBoldAppFont(14f));
        editButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12");
        editButton.setBackground(AppColors.SEMI_LIGHT_BLUE_COLOR);
        editButton.setForeground(AppColors.STRICT_WHITE_COLOR);
        editButton.putClientProperty("material" , material);
        editButtons.add(editButton);
        editionLayout.add(editButton);
        JPanel editWrapper = new JPanel(new GridBagLayout());
        editWrapper.setOpaque(false);
        editWrapper.add(editionLayout);

        card.add(infosPanel, BorderLayout.WEST);
        card.add(badgesWrapper, BorderLayout.CENTER);
        card.add(editWrapper, BorderLayout.EAST);

        return card;
    }

    private JPanel wrapInPanel(JLabel label , FlowLayout layout){
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.add(label);
        return panel;
    }

    public JTextField getSearchField() { return searchField; }

    public JButton getAddButton() { return addButton; }

    public JPanel getBottomPanel() { return bottomPanel; }

    public List<JButton> getEditButtons() { return editButtons; }

}